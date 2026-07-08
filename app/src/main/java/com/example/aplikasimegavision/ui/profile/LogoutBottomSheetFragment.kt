package com.example.aplikasimegavision.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentLogoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LogoutBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentLogoutBottomSheetBinding? = null
    private val binding get() = _binding!!

    var onLogoutConfirmed: (() -> Unit)? = null

    companion object {
        const val TAG = "LogoutBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogoutBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        val namaUser = prefs.getString("NAMA", "Pengguna") ?: "Pengguna"

        binding.tvKonfirmasiPesan.text = "Anda akan keluar dari akun $namaUser, lanjutkan?"

        binding.btnTutup.setOnClickListener {
            dismiss()
        }

        binding.btnBatal.setOnClickListener {
            dismiss()
        }

        binding.btnKonfirmasiKeluar.setOnClickListener {
            onLogoutConfirmed?.invoke()

            prefs.edit().clear().apply()
            Toast.makeText(requireContext(), "Berhasil logout", Toast.LENGTH_SHORT).show()

            try {
                val intent = requireActivity().intent
                requireActivity().finish()
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}