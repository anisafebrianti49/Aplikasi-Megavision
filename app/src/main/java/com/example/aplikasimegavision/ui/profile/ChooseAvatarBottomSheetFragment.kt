package com.example.aplikasimegavision.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aplikasimegavision.databinding.DialogChooseAvatarBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseAvatarBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: DialogChooseAvatarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogChooseAvatarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getString("USER_ID", "") ?: ""

        val avatarMap = mapOf(
            binding.ivAvatar1 to "avatar_1",
            binding.ivAvatar2 to "avatar_2",
            binding.ivAvatar3 to "avatar_3",
            binding.ivAvatar4 to "avatar_4",
            binding.ivAvatar5 to "avatar_5",
            binding.ivAvatar6 to "avatar_6",
            binding.ivAvatar7 to "avatar_7",
            binding.ivAvatar8 to "avatar_8",
            binding.ivAvatar9 to "avatar_9",
            binding.ivAvatar10 to "avatar_10"
        )

        avatarMap.forEach { (imageView, avatarString) ->
            imageView.setOnClickListener {
                saveAvatarSelection(userId, avatarString)
            }
        }

        binding.btnResetAvatar.setOnClickListener {
            saveAvatarSelection(userId, "default")
        }
    }

    private fun saveAvatarSelection(userId: String, avatarName: String) {
        val prefs = requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("SAVED_AVATAR_$userId", avatarName).apply()

        parentFragmentManager.setFragmentResult("avatar_changed_request", Bundle())

        Toast.makeText(requireContext(), "Foto profil diperbarui!", Toast.LENGTH_SHORT).show()
        dismiss() // Tutup Bottom Sheet otomatis
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}