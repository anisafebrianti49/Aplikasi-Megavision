package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class LiveChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Kita buat tampilan teks sederhana langsung lewat kode (tanpa XML) untuk testing sementara
        val view = View(context)
        view.setBackgroundColor(android.graphics.Color.parseColor("#F1F5F9")) // Background abu-abu terang
        return view
    }
}