package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class DetailSolusiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menghubungkan ke layout xml detail solusi
        val view = inflater.inflate(R.layout.fragment_detail_solusi, container, false)

        // Aksi tombol back agar bisa kembali ke daftar pertanyaan
        val btnBack = view.findViewById<ImageView>(R.id.btnBackDetail)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}