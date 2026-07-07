package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class UpgradePaketFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengarah ke layout fragment_upgrade.xml kamu
        return inflater.inflate(R.layout.fragment_upgrade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FIX: ID disesuaikan dengan XML halaman upgrade, yaitu btn_back_upgrade
        val btnBack = view.findViewById<ImageView>(R.id.btn_back_upgrade)
        val cardFaqSpeedtest = view.findViewById<CardView>(R.id.card_faq_speedtest)

        // Logika ketika tombol "Back" diklik
        btnBack?.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                // Kembalikan ke halaman sebelumnya jika ada riwayat transaksi fragment
                parentFragmentManager.popBackStack()
            } else {
                // PERTAHANAN TERAKHIR: Jika popBackStack kosong, paksa replace balik ke Bantuan utama
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, BantuanFragment()) // <-- Sesuaikan nama class fragment utama bantuan Anda jika berbeda
                    .commit()
            }
        }

        // Ketika pertanyaan diklik, langsung pindah ke halaman jawaban asli
        cardFaqSpeedtest?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailSolusiUpgradeFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}