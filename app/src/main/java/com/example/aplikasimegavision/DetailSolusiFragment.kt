package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class DetailSolusiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_solusi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MENGGUNAKAN ID BARU SESUAI XML KAMU
        val btnBack = view.findViewById<ImageView>(R.id.btnBackDetail)
        val btnYa = view.findViewById<Button>(R.id.btn_solusi_terpecahkan)

        // (Opsional) Jika ingin mengubah teks lewat Kotlin, pastikan ID-nya cocok
        // val tvSolusi = view.findViewById<TextView>(R.id.tv_detail_solusi)

        // 1. Fungsi Tombol Back (Kembali ke halaman sebelumnya)
        btnBack?.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // 2. Fungsi Tombol YA (Kembali langsung ke Dashboard Bantuan Utama)
        btnYa?.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BantuanFragment())
                .commit()
        }
    }
}