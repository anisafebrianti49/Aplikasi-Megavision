package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class AdministrasiTagihanFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_administrasi_tagihan, container, false)

        view.findViewById<ImageView>(R.id.btnBackAdministrasi).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<CardView>(R.id.itemPertanyaan1).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailSolusiFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}