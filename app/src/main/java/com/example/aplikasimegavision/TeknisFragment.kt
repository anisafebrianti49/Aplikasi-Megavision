package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class TeknisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teknis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBackTeknis = view.findViewById<ImageView>(R.id.btn_back_teknis)
        btnBackTeknis?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val btnIdPelanggan = view.findViewById<View>(R.id.card_faq_id_pelanggan)
        btnIdPelanggan?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailSolusiTeknisFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}