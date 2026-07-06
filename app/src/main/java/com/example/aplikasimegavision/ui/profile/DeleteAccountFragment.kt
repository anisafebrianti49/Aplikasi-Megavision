package com.example.aplikasimegavision.ui.profile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentDeleteAccountBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteAccountFragment : Fragment() {

    private var _binding: FragmentDeleteAccountBinding? = null
    private val binding get() = _binding!!

    // Variabel untuk Firebase
    private lateinit var database: DatabaseReference
    private var userId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Pengaman Inisialisasi Firebase
        try {
            FirebaseApp.initializeApp(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 2. Ambil ID User dari SharedPreferences
        val prefs = requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        userId = prefs.getString("USER_ID", "") ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(requireContext(), "Sesi login tidak valid", Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Hubungkan ke Database Server Singapura
        database = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("pelanggan").child(userId)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Jalankan pengecekan di awal untuk antisipasi auto-fill
        checkInputAndUpdateButton()

        // Aktifkan tombol Hapus Akun hanya jika kedua field terisi
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkInputAndUpdateButton()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etNomorTelepon.addTextChangedListener(watcher)
        binding.etAlasanHapus.addTextChangedListener(watcher)

        binding.btnHapusAkun.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun checkInputAndUpdateButton() {
        val nomor = binding.etNomorTelepon.text?.toString()?.trim() ?: ""
        val alasan = binding.etAlasanHapus.text?.toString()?.trim() ?: ""
        val isEnabled = nomor.isNotEmpty() && alasan.isNotEmpty()

        binding.btnHapusAkun.isEnabled = isEnabled
        binding.btnHapusAkun.alpha = if (isEnabled) 1.0f else 0.5f
    }

    private fun showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi Hapus Akun")
            .setMessage("Apakah Anda yakin ingin menghapus akun My Megavision Anda? Tindakan ini tidak dapat dibatalkan.")
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Hapus") { _, _ ->
                hapusAkunDariFirebase()
            }
            .show()
    }

    private fun hapusAkunDariFirebase() {
        // Ambil inputan dari EditText
        val inputNomor = binding.etNomorTelepon.text?.toString()?.trim() ?: ""

        Toast.makeText(requireContext(), "Memvalidasi data...", Toast.LENGTH_SHORT).show()
        binding.btnHapusAkun.isEnabled = false

        // 4. Validasi ke Firebase Database sebelum menghapus
        database.get().addOnSuccessListener { snapshot ->
            if (!isAdded || _binding == null) return@addOnSuccessListener

            if (snapshot.exists()) {
                // Ambil data nomor_telepon dari database
                val nomorDiDatabase = snapshot.child("nomor_telepon").value?.toString() ?: ""

                // 5. Cek apakah inputan sama dengan data di database
                if (inputNomor == nomorDiDatabase) {
                    // Jika cocok, lanjutkan ke proses hapus
                    eksekusiHapusData()
                } else {
                    // Jika tidak cocok, batalkan dan tampilkan pesan
                    binding.btnHapusAkun.isEnabled = true
                    Toast.makeText(requireContext(), "Validasi Gagal: Nomor telepon tidak sesuai dengan akun ini.", Toast.LENGTH_LONG).show()
                }
            } else {
                binding.btnHapusAkun.isEnabled = true
                Toast.makeText(requireContext(), "Akun tidak ditemukan di database.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            if (!isAdded || _binding == null) return@addOnFailureListener
            binding.btnHapusAkun.isEnabled = true
            Toast.makeText(requireContext(), "Gagal memvalidasi: ${exception.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun eksekusiHapusData() {
        // 6. Perintah sakti untuk menghapus data di Firebase
        database.removeValue().addOnCompleteListener { task ->
            if (!isAdded || _binding == null) return@addOnCompleteListener

            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Akun berhasil dihapus", Toast.LENGTH_LONG).show()

                // 7. Bersihkan data login dari memori HP
                requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply()

                // 8. Arahkan pengguna kembali ke halaman Login/Awal
                try {
                    findNavController().navigate(R.id.loginFragment)
                } catch (e: Exception) {
                    val intent = requireActivity().intent
                    requireActivity().finish()
                    startActivity(intent)
                }
            } else {
                binding.btnHapusAkun.isEnabled = true
                Toast.makeText(requireContext(), "Gagal menghapus akun: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}