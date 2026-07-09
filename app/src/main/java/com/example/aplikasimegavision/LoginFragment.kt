package com.example.aplikasimegavision.ui.auth

import android.content.Context
import android.content.Intent
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
import com.example.aplikasimegavision.MainActivity_dashboard
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference

    companion object {
        private const val TAG = "LOGIN_DEBUG"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            FirebaseApp.initializeApp(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        database = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("pelanggan")

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        fun checkInputAndUpdateButton() {
            val nomor = binding.etNomorPelanggan.text?.toString()?.trim() ?: ""
            val password = binding.etPassword.text?.toString()?.trim() ?: ""

            val aktif = nomor.isNotEmpty() && password.isNotEmpty()

            binding.btnSubmit.isEnabled = aktif
            binding.btnSubmit.alpha = if (aktif) 1f else 0.5f
        }

        checkInputAndUpdateButton()

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkInputAndUpdateButton()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etNomorPelanggan.addTextChangedListener(textWatcher)
        binding.etPassword.addTextChangedListener(textWatcher)

        binding.btnSubmit.setOnClickListener {
            val nomor = binding.etNomorPelanggan.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            Toast.makeText(requireContext(), "Sedang mengecek data...", Toast.LENGTH_SHORT).show()

            binding.btnSubmit.isEnabled = false
            binding.btnSubmit.alpha = 0.5f

            loginFirebase(nomor, password)
        }

        binding.tvLupaPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.tvDaftarAkun.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun loginFirebase(nomorPelanggan: String, password: String) {
        Log.d(TAG, "Login dicoba - Nomor: $nomorPelanggan | Pass: $password")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded || _binding == null) return

                var loginBerhasil = false

                for (userSnapshot in snapshot.children) {
                    val dbNomor = userSnapshot.child("nomor_pelanggan").value?.toString() ?: ""
                    val dbPassword = userSnapshot.child("password").value?.toString() ?: ""

                    if (nomorPelanggan == dbNomor && password == dbPassword) {
                        loginBerhasil = true

                        val userId = userSnapshot.key ?: ""
                        val nama = userSnapshot.child("nama").value?.toString() ?: ""

                        requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
                            .edit()
                            .putString("USER_ID", userId)
                            .putString("NAMA", nama)
                            .apply()

                        Toast.makeText(requireContext(), "Login Berhasil!", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(requireContext(), MainActivity_dashboard::class.java))
                        requireActivity().finish()
                        break
                    }
                }

                if (!loginBerhasil) {
                    Toast.makeText(requireContext(), "Nomor pelanggan atau password salah", Toast.LENGTH_LONG).show()
                    binding.btnSubmit.isEnabled = true
                    binding.btnSubmit.alpha = 1f
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if (isAdded) {
                    Log.e(TAG, "Firebase Error", error.toException())
                    Toast.makeText(requireContext(), "Firebase Error: ${error.message}", Toast.LENGTH_LONG).show()
                    binding.btnSubmit.isEnabled = true
                    binding.btnSubmit.alpha = 1f
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}