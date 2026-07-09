package com.example.aplikasimegavision

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.databinding.FragmentRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            FirebaseApp.initializeApp(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        database = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("pelanggan")

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        fun checkInputAndUpdateButton() {
            val input = binding.etNomorPelanggan.text?.toString()?.trim() ?: ""
            val isEnabled = input.isNotEmpty()

            binding.btnSubmit.isEnabled = isEnabled
            binding.btnSubmit.alpha = if (isEnabled) 1.0f else 0.5f
        }

        checkInputAndUpdateButton()

        binding.etNomorPelanggan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkInputAndUpdateButton()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSubmit.setOnClickListener {
            val nomorPelanggan = binding.etNomorPelanggan.text.toString().trim()
            prosesRegistrasi(nomorPelanggan)
        }

        binding.btnBackIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun prosesRegistrasi(nomorInput: String) {
        binding.btnSubmit.isEnabled = false
        binding.btnSubmit.alpha = 0.5f
        Toast.makeText(requireContext(), "Mengecek data...", Toast.LENGTH_SHORT).show()

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded || _binding == null) return

                var nomorSudahAda = false

                for (userSnapshot in snapshot.children) {
                    val dbNomor = userSnapshot.child("nomor_pelanggan").value?.toString() ?: ""
                    if (dbNomor == nomorInput) {
                        nomorSudahAda = true
                        break
                    }
                }

                if (nomorSudahAda) {
                    Toast.makeText(requireContext(), "Nomor pelanggan sudah terdaftar!", Toast.LENGTH_LONG).show()
                    binding.btnSubmit.isEnabled = true
                    binding.btnSubmit.alpha = 1.0f
                } else {
                    val jumlahUserSekarang = snapshot.childrenCount
                    val newUserId = String.format("user_%03d", jumlahUserSekarang + 1)

                    val newUserMap = mapOf<String, Any>(
                        "nomor_pelanggan" to nomorInput,
                        "nama" to "Pengguna Baru",
                        "password" to "123456",
                        "email" to "",
                        "nomor_telepon" to "",
                        "tanggal_lahir" to "",
                        "kode_referral" to newUserId.replace("user_", "10000")
                    )

                    database.child(newUserId).setValue(newUserMap).addOnCompleteListener { task ->
                        if (!isAdded || _binding == null) return@addOnCompleteListener

                        binding.btnSubmit.isEnabled = true
                        binding.btnSubmit.alpha = 1.0f

                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Pendaftaran berhasil! Silakan Login.", Toast.LENGTH_LONG).show()
                            findNavController().navigateUp()
                        } else {
                            Toast.makeText(requireContext(), "Gagal mendaftar: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if (isAdded) {
                    binding.btnSubmit.isEnabled = true
                    binding.btnSubmit.alpha = 1.0f
                    Toast.makeText(requireContext(), "Terjadi kesalahan: ${error.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}