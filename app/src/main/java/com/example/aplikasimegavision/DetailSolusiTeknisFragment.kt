package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DetailSolusiTeknisFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Arahkan ke file XML detail solusi teknis yang sudah kamu punya
        return inflater.inflate(R.layout.fragment_detail_solusi, container, false)
    }
}