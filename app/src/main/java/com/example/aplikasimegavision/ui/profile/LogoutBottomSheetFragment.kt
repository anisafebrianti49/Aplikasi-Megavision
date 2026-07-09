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

    // Callback listener agar ProfileFragment bisa bereaksi setelah logout dikonfirmasi
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

        // 1. Langsung tarik Nama User dari SharedPreferences biar dinamis
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
            // Panggil callback kalau-kalau ProfileFragment butuh bereaksi
            onLogoutConfirmed?.invoke()

            // 2. Bersihkan sesi login dari ingatan HP
            prefs.edit().clear().apply()
            Toast.makeText(requireContext(), "Berhasil logout", Toast.LENGTH_SHORT).show()

            // 3. Arahkan user kembali ke halaman Login/Awal
            try {
                // Sesuaikan R.id.action_... dengan ID panah di nav_graph kamu (jika ada)
                // findNavController().navigate(R.id.action_profileFragment_to_loginFragment)

                // Fallback super aman: Restart activity ke halaman awal
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