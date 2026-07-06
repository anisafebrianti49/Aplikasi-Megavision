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
import com.example.aplikasimegavision.databinding.FragmentAddPhoneBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPhoneFragment : Fragment() {

    private var _binding: FragmentAddPhoneBinding? = null
    private val binding get() = _binding!!

    // Variabel untuk koneksi Firebase
    private lateinit var database: DatabaseReference
    private var userId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPhoneBinding.inflate(inflater, container, false)
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

        // 2. Ambil ID User yang sedang login
        val prefs = requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        userId = prefs.getString("USER_ID", "") ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(requireContext(), "Sesi login tidak valid", Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Hubungkan ke Firebase (menggunakan URL server Singapura)
        database = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("pelanggan").child(userId)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Panggil fungsi cek input di awal untuk jaga-jaga kalau ada auto-fill
        checkInputAndUpdateButton()

        // Aktifkan tombol Simpan hanya jika field terisi
        binding.etNomorTelepon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkInputAndUpdateButton()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSimpan.setOnClickListener {
            simpanNomorTelepon()
        }
    }

    // Fungsi terpisah agar kode lebih rapi
    private fun checkInputAndUpdateButton() {
        val nomor = binding.etNomorTelepon.text?.toString()?.trim() ?: ""
        val isEnabled = nomor.isNotEmpty()

        binding.btnSimpan.isEnabled = isEnabled
        binding.btnSimpan.alpha = if (isEnabled) 1.0f else 0.5f
    }

    private fun simpanNomorTelepon() {
        val nomor = binding.etNomorTelepon.text.toString().trim()

        if (nomor.length < 9) {
            binding.tilNomorTelepon.error = "Nomor telepon tidak valid (minimal 9 angka)"
            return
        }
        binding.tilNomorTelepon.error = null

        // Matikan tombol sementara dan beri notif loading
        Toast.makeText(requireContext(), "Menyimpan nomor telepon...", Toast.LENGTH_SHORT).show()
        binding.btnSimpan.isEnabled = false
        binding.btnSimpan.alpha = 0.5f

        // 4. Update field "nomor_telepon" di Firebase
        database.child("nomor_telepon").setValue(nomor).addOnCompleteListener { task ->
            // Pastikan fragment masih aktif
            if (!isAdded || _binding == null) return@addOnCompleteListener

            // Nyalakan kembali tombolnya
            binding.btnSimpan.isEnabled = true
            binding.btnSimpan.alpha = 1.0f

            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Nomor telepon berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp() // Kembali otomatis ke ProfileFragment
            } else {
                Toast.makeText(requireContext(), "Gagal menyimpan data: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}