package com.example.aplikasimegavision.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController // Ditambahkan untuk navigasi yang konsisten
import com.example.aplikasimegavision.databinding.FragmentChangePasswordBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    // Inisialisasi Database
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // FIX: Tanda tanya (?) setelah savedInstanceState dihapus agar tidak eror compiler
        super.onViewCreated(view, savedInstanceState)

        // Arahkan ke tabel "pelanggan"
        database = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("pelanggan")

        // Tombol Kembali
        binding.btnBack.setOnClickListener {
            // FIX: Diubah menggunakan findNavController agar selaras dengan Navigation Component
            findNavController().popBackStack()
        }

        // Tombol Simpan
        binding.btnSimpanPassword.setOnClickListener {
            prosesUbahPassword()
        }
    }

    private fun prosesUbahPassword() {
        val passwordLama = binding.etPasswordLama.text.toString().trim()
        val passwordBaru = binding.etPasswordBaru.text.toString().trim()
        val konfirmasiPassword = binding.etKonfirmasiPassword.text.toString().trim()

        // 1. Validasi Input tidak boleh kosong
        if (passwordLama.isEmpty()) {
            binding.tilPasswordLama.error = "Masukkan password lama"
            return
        }
        if (passwordBaru.isEmpty()) {
            binding.tilPasswordLama.isErrorEnabled = false
            binding.tilPasswordBaru.error = "Masukkan password baru"
            return
        }
        if (konfirmasiPassword.isEmpty()) {
            binding.tilPasswordBaru.isErrorEnabled = false
            binding.tilKonfirmasiPassword.error = "Masukkan konfirmasi password"
            return
        }

        // 2. Validasi Password Baru harus sama dengan Konfirmasi
        if (passwordBaru != konfirmasiPassword) {
            binding.tilKonfirmasiPassword.error = "Password baru tidak cocok"
            return
        }

        // Hapus error jika aman
        binding.tilKonfirmasiPassword.isErrorEnabled = false

        // FIX: Mengambil ID User secara dinamis dari SharedPreferences, mirip dengan ProfileFragment
        val prefs = requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        val userIdLogin = prefs.getString("USER_ID", "") ?: ""

        if (userIdLogin.isEmpty()) {
            Toast.makeText(requireContext(), "Sesi login tidak valid!", Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Cek password lama ke Firebase sebelum diubah
        database.child(userIdLogin).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val passwordDiDatabase = snapshot.child("password").value.toString()

                if (passwordLama == passwordDiDatabase) {
                    // Jika password lama benar, simpan password baru!
                    database.child(userIdLogin).child("password").setValue(passwordBaru)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Password berhasil diubah!", Toast.LENGTH_SHORT).show()
                            // Kembali ke halaman Profil menggunakan NavController
                            findNavController().popBackStack()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Gagal menyimpan: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    binding.tilPasswordLama.error = "Password lama salah"
                }
            } else {
                Toast.makeText(requireContext(), "Data user tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Gagal terhubung ke database", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}