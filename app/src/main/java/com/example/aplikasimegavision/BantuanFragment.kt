package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment

class BantuanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengarah ke layout dashboard bantuan Anda
        return inflater.inflate(R.layout.fragment_bantuan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inisialisasi komponen menu Topik berdasarkan ID di XML
        val cardAdministrasi = view.findViewById<CardView>(R.id.card_administrasi)
        val cardUpgradePaket = view.findViewById<CardView>(R.id.card_upgrade_paket)
        val cardPermasalahanTeknis = view.findViewById<CardView>(R.id.card_permasalahan_teknis)

        // 2. BARU: Inisialisasi komponen Pencarian & Card Pertanyaan FAQ
        val etSearch = view.findViewById<EditText>(R.id.et_search)
        val cardPertanyaan1 = view.findViewById<CardView>(R.id.card_pertanyaan1)
        val cardPertanyaan2 = view.findViewById<CardView>(R.id.card_pertanyaan2)
        val cardPertanyaan3 = view.findViewById<CardView>(R.id.card_pertanyaan3)

        // ==========================================
        // LOGIKA REAL-TIME SEARCH (FILTER PERTANYAAN)
        // ==========================================
        etSearch?.doOnTextChanged { text, _, _, _ ->
            // Mengubah inputan user menjadi huruf kecil dan menghapus spasi tak berguna di ujung
            val query = text.toString().lowercase().trim()

            if (query.isEmpty()) {
                // Jika kolom pencarian kosong, tampilkan kembali semua Card FAQ
                cardPertanyaan1?.visibility = View.VISIBLE
                cardPertanyaan2?.visibility = View.VISIBLE
                cardPertanyaan3?.visibility = View.VISIBLE
            } else {
                // Filter FAQ 1: "Bagaimana Cara Mengajukan Upgrade?"
                cardPertanyaan1?.visibility = if ("bagaimana cara mengajukan upgrade".contains(query)) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                // Filter FAQ 2: "Kapan tagihan Megavision jatuh tempo?"
                cardPertanyaan2?.visibility = if ("kapan tagihan megavision jatuh tempo".contains(query)) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                // Filter FAQ 3: "Bagaimana cara mengatasi internet lambat?"
                cardPertanyaan3?.visibility = if ("bagaimana cara mengatasi internet lambat".contains(query)) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

        // ==========================================
        // LOGIKA PINDAH HALAMAN (AKSI KLIK MENU TOPIK)
        // ==========================================

        // Aksi klik untuk kartu Administrasi & Tagihan
        cardAdministrasi?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdministrasiTagihanFragment())
                .addToBackStack(null)
                .commit()
        }

        // Aksi klik untuk kartu Upgrade Paket
        cardUpgradePaket?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UpgradePaketFragment())
                .addToBackStack(null)
                .commit()
        }

        // Aksi klik untuk kartu Permasalahan Teknis
        cardPermasalahanTeknis?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TeknisFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}