package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat // TAMPILAN TANGGAL
import java.util.Date
import java.util.Locale
import android.widget.ImageView

class VoucherCatalogFragment : Fragment() {

    private lateinit var rvKatalogVoucher: RecyclerView
    private lateinit var tvSaldoPoin: TextView
    private lateinit var voucherAdapter: VoucherAdapter

    private var saldoPoinSaatIni: Int = 1500

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_voucher_catalog, container, false)

        rvKatalogVoucher = view.findViewById(R.id.rvKatalogVoucher)
        tvSaldoPoin = view.findViewById(R.id.tvSaldoPoin)

        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            requireActivity().finish()
        }

        updateTampilanSaldo()

        val btnRiwayat = view.findViewById<MaterialButton>(R.id.btnRiwayat)
        btnRiwayat.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, PointHistoryFragment())
                .addToBackStack(null)
                .commit()
        }

        val btnVoucherSaya = view.findViewById<MaterialButton>(R.id.btnVoucherSaya)
        btnVoucherSaya.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, MyVouchersFragment())
                .addToBackStack(null)
                .commit()
        }

        setupRecyclerView()
        loadDataVoucher()

        return view
    }

    private fun updateTampilanSaldo() {
        tvSaldoPoin.text = "$saldoPoinSaatIni Poin"
    }

    private fun setupRecyclerView() {
        rvKatalogVoucher.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadDataVoucher() {
        val dummyData = listOf(
            Voucher("V001", "Diskon Tagihan Rp 10.000", "Potongan tagihan ringan untuk bulan berikutnya.\n1. Berlaku 30 hari sejak ditukar.\n2. Hanya untuk pelanggan aktif.", 100, 10000.0, "tersedia"),
            Voucher("V002", "Diskon Tagihan Rp 20.000", "Potongan tagihan untuk bulan berikutnya.\n1. Berlaku 30 hari sejak ditukar.\n2. Hanya untuk pelanggan aktif.", 200, 20000.0, "tersedia"),
            Voucher("V003", "Diskon Tagihan Rp 30.000", "Potongan tagihan untuk bulan berikutnya.\n1. Berlaku 30 hari sejak ditukar.\n2. Tidak dapat digabung dengan promo lain.", 300, 30000.0, "tersedia"),
            Voucher("V004", "Diskon Tagihan Rp 50.000", "Potongan tagihan menengah.\n1. Berlaku 14 hari sejak ditukar.\n2. Tidak dapat digabung dengan promo lain.", 450, 50000.0, "tersedia"),
            Voucher("V005", "Diskon Tagihan Rp 75.000", "Potongan tagihan besar.\n1. Berlaku 14 hari sejak ditukar.\n2. Hanya dapat ditukarkan 1x dalam sebulan.", 700, 75000.0, "tersedia"),
            Voucher("V006", "Diskon Tagihan Rp 100.000", "Potongan super besar untuk tagihanmu.\n1. Masa berlaku 7 hari.\n2. Hanya dapat ditukarkan 1x dalam setahun.", 900, 100000.0, "tersedia"),
            Voucher("V007", "Diskon Tagihan Rp 150.000", "Potongan maksimal khusus pelanggan setia.\n1. Masa berlaku 7 hari.\n2. Hanya dapat ditukarkan 1x dalam setahun.", 1300, 150000.0, "tersedia")
        )

        voucherAdapter = VoucherAdapter(dummyData) { selectedVoucher ->
            showVoucherDetailDialog(selectedVoucher)
        }
        rvKatalogVoucher.adapter = voucherAdapter
    }

    private fun showVoucherDetailDialog(voucher: Voucher) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_voucher_detail, null)
        bottomSheetDialog.setContentView(view)

        val tvNama = view.findViewById<TextView>(R.id.tvDialogNamaVoucher)
        val tvSK = view.findViewById<TextView>(R.id.tvDialogSyaratKetentuan)
        val btnBatal = view.findViewById<Button>(R.id.btnBatalTukar)
        val btnTukar = view.findViewById<Button>(R.id.btnLanjutTukar)

        tvNama.text = voucher.namaVoucher
        tvSK.text = voucher.deskripsi

        btnBatal.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        btnTukar.setOnClickListener {
            bottomSheetDialog.dismiss()
            prosesPenukaranVoucher(voucher)
        }
        bottomSheetDialog.show()
    }

    private fun prosesPenukaranVoucher(voucher: Voucher) {
        if (saldoPoinSaatIni >= voucher.poinDibutuhkan) {
            saldoPoinSaatIni -= voucher.poinDibutuhkan
            updateTampilanSaldo()

            val database = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("VoucherSaya")
            val idFirebaseBaru = database.push().key ?: ""

            val voucherBaru = VoucherAktif(
                idFirebase = idFirebaseBaru,
                namaVoucher = voucher.namaVoucher
            )

            database.child(idFirebaseBaru).setValue(voucherBaru)

            val sdf = SimpleDateFormat("dd MMMM yyyy • HH:mm 'WIB'", Locale("id", "ID"))
            val tanggalSekarang = sdf.format(Date())

            val riwayatBaru = RiwayatPoin(
                judul = "Tukar ${voucher.namaVoucher}",
                tanggal = tanggalSekarang,
                poin = voucher.poinDibutuhkan,
                isPoinMasuk = false
            )

            RiwayatManager.dataRiwayat.add(0, riwayatBaru)

            tampilkanPopUpSukses(voucher)
        } else {
            Toast.makeText(requireContext(), "Maaf, poin kamu tidak cukup untuk menukar voucher ini!", Toast.LENGTH_LONG).show()
        }
    }

    private fun tampilkanPopUpSukses(voucher: Voucher) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_success_tukar, null)
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val tvPesan = dialogView.findViewById<TextView>(R.id.tvSuccessMessage)
        val btnTutup = dialogView.findViewById<Button>(R.id.btnTutupSuccess)

        tvPesan.text = "Berhasil menukar ${voucher.poinDibutuhkan} Poin dengan ${voucher.namaVoucher}.\n\nSilakan cek halaman Voucher Saya untuk menggunakannya."

        btnTutup.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}