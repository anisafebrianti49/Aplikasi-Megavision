package com.example.aplikasimegavision.ui.profile

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

class AddPhoneFragment : Fragment() {

    private var _binding: FragmentAddPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Aktifkan tombol Simpan hanya jika field terisi
        binding.etNomorTelepon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isEnabled = s.toString().trim().isNotEmpty()
                binding.btnSimpan.isEnabled = isEnabled
                binding.btnSimpan.alpha = if (isEnabled) 1.0f else 0.5f
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSimpan.setOnClickListener {
            val nomor = binding.etNomorTelepon.text.toString().trim()
            if (nomor.length < 9) {
                binding.tilNomorTelepon.error = "Nomor telepon tidak valid"
                return@setOnClickListener
            }
            binding.tilNomorTelepon.error = null
            // TODO: Call ViewModel to send OTP / save phone
            Toast.makeText(requireContext(), "Kode OTP dikirim ke $nomor", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}