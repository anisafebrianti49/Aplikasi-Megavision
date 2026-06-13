package com.example.aplikasimegavision.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol back
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Aktifkan tombol Submit hanya jika kedua field sudah terisi
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nomorPelanggan = binding.etNomorPelanggan.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                val isEnabled = nomorPelanggan.isNotEmpty() && password.isNotEmpty()
                binding.btnSubmit.isEnabled = isEnabled
                binding.btnSubmit.alpha = if (isEnabled) 1.0f else 0.5f
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etNomorPelanggan.addTextChangedListener(textWatcher)
        binding.etPassword.addTextChangedListener(textWatcher)

        // Tombol Submit (contoh aksi login)
        binding.btnSubmit.setOnClickListener {
            val nomorPelanggan = binding.etNomorPelanggan.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            // TODO: Implementasi logika login (panggil ViewModel/API)
            Toast.makeText(requireContext(), "Login: $nomorPelanggan", Toast.LENGTH_SHORT).show()
        }

        // Link Lupa Password
        binding.tvLupaPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        // Link Daftar Akun
        binding.tvDaftarAkun.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}