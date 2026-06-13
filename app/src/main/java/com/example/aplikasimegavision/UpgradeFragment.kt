package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class UpgradeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 1. Menghubungkan ke file XML daftar pertanyaan
        val view = inflater.inflate(R.layout.fragment_upgrade_paket, container, false)

        // 2. Aksi klik langsung menembak ID CardView yang asli (card_faq_speedtest)
        val btnPertanyaan1 = view.findViewById<View>(R.id.card_faq_speedtest)
        btnPertanyaan1.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailSolusiUpgradeFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}