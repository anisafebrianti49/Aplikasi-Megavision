package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DetailSolusiUpgradeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menginflate ke layout yang baru kita buat khusus upgrade
        return inflater.inflate(R.layout.fragment_detail_solusi_upgrade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Logika tombol kembali ke daftar upgrade paket
        view.findViewById<View>(R.id.btn_back_detail_upgrade).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}