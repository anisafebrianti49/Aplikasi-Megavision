package com.example.aplikasimegavision

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.databinding.FragmentForgotPasswordBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private var isVerified = false
    private var verifiedUserId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try { FirebaseApp.initializeApp(requireContext()) } catch (e: Exception) { e.printStackTrace() }

        database = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("pelanggan")

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }

        binding.btnSubmit.setOnClickListener {
            if (!isVerified) {
                prosesCekNomor(binding.etNomorPelanggan.text.toString().trim())
            } else {
                prosesSimpanPasswordBaru(binding.etPasswordBaru.text.toString().trim())
            }
        }
    }

    private fun prosesCekNomor(nomor: String) {
        binding.btnSubmit.isEnabled = false
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    if (userSnapshot.child("nomor_pelanggan").value == nomor) {
                        isVerified = true
                        verifiedUserId = userSnapshot.key ?: ""

                        binding.etNomorPelanggan.isEnabled = false
                        binding.etPasswordBaru.visibility = View.VISIBLE
                        binding.btnSubmit.text = "Simpan Password Baru"
                        Toast.makeText(requireContext(), "Nomor ditemukan! Masukkan password baru.", Toast.LENGTH_SHORT).show()
                        binding.btnSubmit.isEnabled = true
                        return
                    }
                }
                Toast.makeText(requireContext(), "Nomor tidak ditemukan", Toast.LENGTH_SHORT).show()
                binding.btnSubmit.isEnabled = true
            }
            override fun onCancelled(error: DatabaseError) { binding.btnSubmit.isEnabled = true }
        })
    }

    private fun prosesSimpanPasswordBaru(passBaru: String) {
        if (passBaru.length < 6) {
            Toast.makeText(requireContext(), "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
            return
        }

        database.child(verifiedUserId).child("password").setValue(passBaru).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Password berhasil diubah!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Gagal mengubah password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}