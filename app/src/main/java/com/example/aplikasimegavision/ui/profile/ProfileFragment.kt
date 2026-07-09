package com.example.aplikasimegavision.ui.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentProfileBinding
import com.example.aplikasimegavision.ui.auth.LoginFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            FirebaseApp.initializeApp(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            binding.rowTanggalLahir.tvRowLabel.text = "Tanggal Lahir"

            // KODE PERBAIKAN: Menggunakan ID yang benar dari XML kamu
            binding.rowTanggalLahir.ivActionIcon.visibility = android.view.View.GONE

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val prefs = requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getString("USER_ID", "") ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(requireContext(), "Sesi login tidak aktif (Gagal memuat data)", Toast.LENGTH_SHORT).show()
            return
        }

        // 1. TAMPILKAN FOTO PROFIL AWAL DARI SHAREDPREFERENCES LOKAL
        loadLocalProfileImage(userId)

        // 2. DAFTARKAN LISTENER (PENERIMA SINYAL) JIKA AVATAR DIUBAH DI BOTTOM SHEET
        parentFragmentManager.setFragmentResultListener("avatar_changed_request", viewLifecycleOwner) { _, _ ->
            loadLocalProfileImage(userId) // Gambar langsung di-refresh otomatis pas bottom sheet ditutup!
        }

        // 3. AKSI KLIK FOTO PROFIL -> MUNCULKAN BOTTOM SHEET GRID 10 AVATAR
        binding.ivProfile.setOnClickListener {
            val avatarBottomSheet = ChooseAvatarBottomSheetFragment()
            avatarBottomSheet.show(parentFragmentManager, "ChooseAvatarBottomSheetTag")
        }

        // --- Aksi klik navigasi lainnya tetap sama ---
        binding.tvUbahData.setOnClickListener {
            safeNavigate(EditProfileFragment())
        }

        binding.btnCopyReferral.setOnClickListener {
            val kode = binding.tvKodeReferralValue.text.toString()
            if (kode.isNotEmpty() && kode != "-") {
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Kode Referral", kode)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), "Kode referral disalin!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnTambahTelepon.setOnClickListener {
            safeNavigate(AddPhoneFragment())
        }

        binding.btnEditPassword.setOnClickListener {
            safeNavigate(ChangePasswordFragment())
        }


        binding.rowGantiAkun.setOnClickListener {
            safeNavigate(LoginFragment())
        }

        binding.rowKebijakanPrivasi.setOnClickListener {
            safeNavigate(PrivacyPolicyFragment())
        }

        binding.rowHapusAkun.setOnClickListener {
            safeNavigate(DeleteAccountFragment())
        }

        binding.btnKeluar.setOnClickListener {
            val logoutBottomSheet = LogoutBottomSheetFragment()
            logoutBottomSheet.show(parentFragmentManager, "LogoutBottomSheetTag")
        }

        // ==========================================
        // PROSES PEMBACAAN DATA TEKS DARI DATABASE
        // ==========================================
        val databaseRef = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("pelanggan").child(userId)

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded || _binding == null) return

                if (snapshot.exists()) {
                    val namaUser = snapshot.child("nama").value?.toString() ?: ""
                    val nomorPelanggan = snapshot.child("nomor_pelanggan").value?.toString() ?: ""
                    val tanggalLahir = snapshot.child("tanggal_lahir").value?.toString() ?: ""
                    val kodeReferral = snapshot.child("kode_referral").value?.toString() ?: ""
                    val email = snapshot.child("email").value?.toString() ?: ""
                    val nomorTelepon = snapshot.child("nomor_telepon").value?.toString() ?: ""

                    binding.tvNamaPengguna.text = namaUser.ifEmpty { "Nama Tidak Tersedia" }
                    binding.tvNomorPelanggan.text = "Nomor Pelanggan : ${nomorPelanggan.ifEmpty { "-" }}"

                    try {
                        binding.rowTanggalLahir.tvRowValue.text = tanggalLahir.ifEmpty { "-" }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    binding.tvKodeReferralValue.text = kodeReferral.ifEmpty { "-" }
                    binding.tvEmailValue.text = email.ifEmpty { "-" }

                    if (nomorTelepon.isNotEmpty()) {
                        binding.tvNomorTeleponValue.text = nomorTelepon
                        binding.tvNomorTeleponValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary))
                        binding.tvNomorTeleponValue.setTypeface(null, Typeface.NORMAL)
                    } else {
                        binding.tvNomorTeleponValue.text = "Tidak Ada Nomor Telepon"
                    }

                } else {
                    Toast.makeText(requireContext(), "Data pelanggan belum terdaftar di database", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Gagal memuat data: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // ==========================================
    // SEKARANG SE-MANTAP INI FUNGSI LOAD FOTO LOKALNYA (MENDUKUNG 10 AVATAR)
    // ==========================================
    private fun loadLocalProfileImage(userId: String) {
        if (_binding == null) return

        val prefs = requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        val savedAvatar = prefs.getString("SAVED_AVATAR_$userId", "default")

        // Memetakan string SharedPreferences ke resource drawable asli secara dinamis
        val imageResource = when (savedAvatar) {
            "avatar_1" -> R.drawable.avatar_1
            "avatar_2" -> R.drawable.avatar_2
            "avatar_3" -> R.drawable.avatar_3
            "avatar_4" -> R.drawable.avatar_4
            "avatar_5" -> R.drawable.avatar_5
            "avatar_6" -> R.drawable.avatar_6
            "avatar_7" -> R.drawable.avatar_7
            "avatar_8" -> R.drawable.avatar_8
            "avatar_9" -> R.drawable.avatar_9
            "avatar_10" -> R.drawable.avatar_10
            else -> R.drawable.ic_profile_placeholder
        }

        binding.ivProfile.setImageResource(imageResource)
    }

    private fun safeNavigate(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}