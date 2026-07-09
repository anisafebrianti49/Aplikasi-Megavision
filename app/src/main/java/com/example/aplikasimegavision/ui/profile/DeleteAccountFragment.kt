package com.example.aplikasimegavision.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.aplikasimegavision.ProfileAuthActivity
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentDeleteAccountBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteAccountFragment : Fragment() {

    private var _binding: FragmentDeleteAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private var userId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            FirebaseApp.initializeApp(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val prefs = requireActivity()
            .getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)

        userId = prefs.getString("USER_ID", "") ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(requireContext(), "Sesi login tidak valid", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase
            .getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("pelanggan")
            .child(userId)

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .commit()
        }

        checkInputAndUpdateButton()

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                checkInputAndUpdateButton()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etNomorTelepon.addTextChangedListener(watcher)
        binding.etAlasanHapus.addTextChangedListener(watcher)

        binding.btnHapusAkun.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun checkInputAndUpdateButton() {
        val nomor = binding.etNomorTelepon.text.toString().trim()
        val alasan = binding.etAlasanHapus.text.toString().trim()

        val enable = nomor.isNotEmpty() && alasan.isNotEmpty()

        binding.btnHapusAkun.isEnabled = enable
        binding.btnHapusAkun.alpha = if (enable) 1f else 0.5f
    }

    private fun showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi Hapus Akun")
            .setMessage("Apakah Anda yakin ingin menghapus akun My Megavision? Tindakan ini tidak dapat dibatalkan.")
            .setNegativeButton("Batal", null)
            .setPositiveButton("Hapus") { _, _ ->
                hapusAkunDariFirebase()
            }
            .show()
    }

    private fun hapusAkunDariFirebase() {

        val inputNomor =
            binding.etNomorTelepon.text.toString().trim()

        binding.btnHapusAkun.isEnabled = false

        database.get().addOnSuccessListener { snapshot ->

            if (!isAdded || _binding == null) return@addOnSuccessListener

            if (!snapshot.exists()) {
                binding.btnHapusAkun.isEnabled = true
                Toast.makeText(
                    requireContext(),
                    "Akun tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
                return@addOnSuccessListener
            }

            val nomorDatabase =
                snapshot.child("nomor_telepon").value?.toString() ?: ""

            if (inputNomor != nomorDatabase) {

                binding.btnHapusAkun.isEnabled = true

                Toast.makeText(
                    requireContext(),
                    "Nomor telepon tidak sesuai",
                    Toast.LENGTH_LONG
                ).show()

                return@addOnSuccessListener
            }

            eksekusiHapusData()

        }.addOnFailureListener {

            binding.btnHapusAkun.isEnabled = true

            Toast.makeText(
                requireContext(),
                "Gagal memvalidasi data",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun eksekusiHapusData() {

        database.removeValue().addOnCompleteListener { task ->

            if (!isAdded || _binding == null) return@addOnCompleteListener

            if (task.isSuccessful) {

                requireActivity()
                    .getSharedPreferences(
                        "MegavisionPrefs",
                        Context.MODE_PRIVATE
                    )
                    .edit()
                    .clear()
                    .apply()

                Toast.makeText(
                    requireContext(),
                    "Akun berhasil dihapus",
                    Toast.LENGTH_LONG
                ).show()

                val intent = Intent(
                    requireContext(),
                    ProfileAuthActivity::class.java
                )

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
                requireActivity().finish()

            } else {

                binding.btnHapusAkun.isEnabled = true

                Toast.makeText(
                    requireContext(),
                    "Gagal menghapus akun: ${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}