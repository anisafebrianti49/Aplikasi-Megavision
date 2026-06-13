package com.example.aplikasimegavision.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.databinding.FragmentDeleteAccountBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteAccountFragment : Fragment() {

    private var _binding: FragmentDeleteAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Aktifkan tombol Hapus Akun hanya jika kedua field terisi
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nomor = binding.etNomorTelepon.text.toString().trim()
                val alasan = binding.etAlasanHapus.text.toString().trim()
                val isEnabled = nomor.isNotEmpty() && alasan.isNotEmpty()
                binding.btnHapusAkun.isEnabled = isEnabled
                binding.btnHapusAkun.alpha = if (isEnabled) 1.0f else 0.5f
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etNomorTelepon.addTextChangedListener(watcher)
        binding.etAlasanHapus.addTextChangedListener(watcher)

        binding.btnHapusAkun.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi Hapus Akun")
            .setMessage("Apakah Anda yakin ingin menghapus akun My Megavision Anda? Tindakan ini tidak dapat dibatalkan.")
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Hapus") { _, _ ->
                // TODO: Call ViewModel to delete account
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}