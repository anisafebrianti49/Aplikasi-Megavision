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

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol back
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Aktifkan tombol Submit hanya jika field terisi
        binding.etNomorPelanggan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().trim()
                val isEnabled = input.isNotEmpty()

                binding.btnSubmit.isEnabled = isEnabled
                binding.btnSubmit.alpha = if (isEnabled) 1.0f else 0.5f
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Tombol Submit
        binding.btnSubmit.setOnClickListener {
            val nomorPelanggan = binding.etNomorPelanggan.text.toString().trim()
            Toast.makeText(requireContext(), "Instruksi reset dikirim ke: $nomorPelanggan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}