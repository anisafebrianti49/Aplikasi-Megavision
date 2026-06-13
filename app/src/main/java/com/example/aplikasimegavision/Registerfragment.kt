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
import com.example.aplikasimegavision.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    // Binding ini yang menghubungkan kode Kotlin dengan layout fragment_register.xml
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Tombol Back: Kembali ke halaman sebelumnya
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // 2. Logika Tombol Submit (Hanya aktif jika input tidak kosong)
        binding.etNomorPelanggan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().trim()
                val isEnabled = input.isNotEmpty()

                binding.btnSubmit.isEnabled = isEnabled
                // Memberikan efek visual: agak transparan jika mati, pekat jika aktif
                binding.btnSubmit.alpha = if (isEnabled) 1.0f else 0.5f
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 3. Aksi saat tombol Submit diklik
        binding.btnSubmit.setOnClickListener {
            val nomorPelanggan = binding.etNomorPelanggan.text.toString().trim()
            Toast.makeText(requireContext(), "Mendaftarkan nomor: $nomorPelanggan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}