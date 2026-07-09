package com.example.aplikasimegavision.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentChangePasswordBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase
            .getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("pelanggan")

        // Tombol kembali
        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .commit()
        }

        binding.btnSimpanPassword.setOnClickListener {
            prosesUbahPassword()
        }
    }

    private fun prosesUbahPassword() {

        val passwordLama = binding.etPasswordLama.text.toString().trim()
        val passwordBaru = binding.etPasswordBaru.text.toString().trim()
        val konfirmasiPassword = binding.etKonfirmasiPassword.text.toString().trim()

        if (passwordLama.isEmpty()) {
            binding.tilPasswordLama.error = "Masukkan password lama"
            return
        }

        binding.tilPasswordLama.isErrorEnabled = false

        if (passwordBaru.isEmpty()) {
            binding.tilPasswordBaru.error = "Masukkan password baru"
            return
        }

        binding.tilPasswordBaru.isErrorEnabled = false

        if (konfirmasiPassword.isEmpty()) {
            binding.tilKonfirmasiPassword.error = "Masukkan konfirmasi password"
            return
        }

        binding.tilKonfirmasiPassword.isErrorEnabled = false

        if (passwordBaru != konfirmasiPassword) {
            binding.tilKonfirmasiPassword.error = "Password baru tidak cocok"
            return
        }

        binding.tilKonfirmasiPassword.isErrorEnabled = false

        val prefs = requireActivity()
            .getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)

        val userId = prefs.getString("USER_ID", "") ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Sesi login tidak valid!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        database.child(userId).get()
            .addOnSuccessListener { snapshot ->

                if (!snapshot.exists()) {
                    Toast.makeText(
                        requireContext(),
                        "Data user tidak ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@addOnSuccessListener
                }

                val passwordDatabase =
                    snapshot.child("password").value?.toString() ?: ""

                if (passwordLama != passwordDatabase) {
                    binding.tilPasswordLama.error = "Password lama salah"
                    return@addOnSuccessListener
                }

                binding.tilPasswordLama.isErrorEnabled = false

                database.child(userId)
                    .child("password")
                    .setValue(passwordBaru)
                    .addOnSuccessListener {

                        Toast.makeText(
                            requireContext(),
                            "Password berhasil diubah!",
                            Toast.LENGTH_SHORT
                        ).show()

                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                ProfileFragment()
                            )
                            .commit()
                    }
                    .addOnFailureListener {

                        Toast.makeText(
                            requireContext(),
                            "Gagal menyimpan: ${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            .addOnFailureListener {

                Toast.makeText(
                    requireContext(),
                    "Gagal terhubung ke database",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}