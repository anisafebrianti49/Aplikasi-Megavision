package com.example.aplikasimegavision.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentProfileBinding
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

        // 1. Hubungkan ke Firebase Database
        // (Sesuaikan "user_rubby" dengan ID user login milikmu jika menggunakan Auth)
        val userId = "user_rubby"
        val databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId)

        // Setup label awal untuk baris tanggal lahir yang menggunakan include layout
        try {
            binding.rowTanggalLahir.tvRowLabel.text = "Tanggal Lahir"
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 2. Ambil Data Real-time dari Firebase
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded || _binding == null) return

                if (snapshot.exists()) {
                    // Ambil string dari Firebase, jika null beri string kosong ""
                    val namaUser = snapshot.child("nama").value?.toString() ?: ""
                    val nomorPelanggan = snapshot.child("nomor_pelanggan").value?.toString() ?: ""
                    val tanggalLahir = snapshot.child("tanggal_lahir").value?.toString() ?: ""
                    val kodeReferral = snapshot.child("kode_referral").value?.toString() ?: ""
                    val email = snapshot.child("email").value?.toString() ?: ""
                    val nomorTelepon = snapshot.child("nomor_telepon").value?.toString() ?: ""

                    // Tampilkan ke komponen UI sesuai dengan ID di XML kamu
                    binding.tvNamaPengguna.text = namaUser.ifEmpty { "Rubby Ferdiansyah" }
                    binding.tvNomorPelanggan.text = "Nomor Pelanggan : ${nomorPelanggan.ifEmpty { "01120482" }}"

                    // Set Nilai Tanggal Lahir (Sistem Include)
                    try {
                        binding.rowTanggalLahir.tvRowValue.text = tanggalLahir.ifEmpty { "29 May 2026" }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    // Set Nilai Baris Lainnya (Langsung sesuai ID di XML kamu)
                    binding.tvKodeReferralValue.text = kodeReferral.ifEmpty { "01120482" }
                    binding.tvEmailValue.text = email.ifEmpty { "rubbyferdiansyaah@gmail.com" }

                    if (nomorTelepon.isNotEmpty()) {
                        binding.tvNomorTeleponValue.text = nomorTelepon
                        binding.tvNomorTeleponValue.setTextColor(resources.getColor(R.color.text_primary, null))
                        binding.tvNomorTeleponValue.setTypeface(null, android.graphics.Typeface.NORMAL)
                    } else {
                        binding.tvNomorTeleponValue.text = "Tidak Ada Nomor Telepon"
                        // Tetap miring/abu-abu jika kosong sesuai desain awal XML-mu
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Gagal memuat data: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // 3. Aksi Klik Tombol Ubah Data (Header)
        binding.tvUbahData.setOnClickListener {
            // Menggunakan fungsi bantu mencari ID dari nama String agar tidak bikin eror compile
            val actionId = resources.getIdentifier("action_profileFragment_to_editProfileFragment", "id", requireContext().packageName)
            safeNavigate(actionId, "Edit Profile")
        }

        // 4. Aksi Klik Salin Kode Referral
        binding.btnCopyReferral.setOnClickListener {
            val kode = binding.tvKodeReferralValue.text.toString()
            if (kode.isNotEmpty()) {
                val clipboard = requireContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("Kode Referral", kode)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), "Kode referral disalin!", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. Aksi Klik Tambah Nomor Telepon
        binding.btnTambahTelepon.setOnClickListener {
            val actionId = resources.getIdentifier("action_profileFragment_to_addPhoneFragment", "id", requireContext().packageName)
            safeNavigate(actionId, "Tambah Telepon")
        }

        // 6. Aksi Klik Ubah Password
        binding.btnEditPassword.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Ubah Password", Toast.LENGTH_SHORT).show()
        }

        // 7. Aksi Navigasi Menu Akun (Ganti Akun, Kebijakan, Hapus Akun)
        binding.rowGantiAkun.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Ganti Akun", Toast.LENGTH_SHORT).show()
        }

        binding.rowKebijakanPrivasi.setOnClickListener {
            val actionId = resources.getIdentifier("action_profileFragment_to_privacyPolicyFragment", "id", requireContext().packageName)
            safeNavigate(actionId, "Kebijakan Privasi")
        }

        binding.rowHapusAkun.setOnClickListener {
            val actionId = resources.getIdentifier("action_profileFragment_to_deleteAccountFragment", "id", requireContext().packageName)
            safeNavigate(actionId, "Hapus Akun")
        }

        // 8. Tombol Keluar — Menampilkan BottomSheet Keluar
        binding.btnKeluar.setOnClickListener {
            try {
                val sheet = LogoutBottomSheetFragment()
                sheet.show(parentFragmentManager, LogoutBottomSheetFragment.TAG)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Membuka konfirmasi keluar...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi Pengaman Navigasi Fragment
    private fun safeNavigate(actionId: Int, destinationName: String) {
        try {
            findNavController().navigate(actionId)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Menuju $destinationName (Rute panah action belum ditarik di nav_graph.xml)",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}