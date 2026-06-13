package com.example.aplikasimegavision.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // Nama pengguna pada teks konfirmasi (bisa dilewatkan via argument)
        val namaUser = arguments?.getString("nama_user", "Rubby Ferdiansyah") ?: "Rubby Ferdiansyah"
        binding.tvKonfirmasiPesan.text =
            "Anda akan keluar dari akun $namaUser, lanjutkan?"

        binding.btnTutup.setOnClickListener {
            dismiss()
        }

        binding.btnBatal.setOnClickListener {
            dismiss()
        }

        binding.btnKonfirmasiKeluar.setOnClickListener {
            onLogoutConfirmed?.invoke()
            // TODO: Clear session/token and navigate to Auth screen
            // Contoh: findNavController().navigate(R.id.action_global_to_profileAuthActivity)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}