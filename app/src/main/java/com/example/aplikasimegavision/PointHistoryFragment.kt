package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PointHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_points_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageView>(R.id.btnBackHistory)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val rvRiwayat = view.findViewById<RecyclerView>(R.id.rvRiwayatPoint)

        if (rvRiwayat != null) {
            rvRiwayat.layoutManager = LinearLayoutManager(requireContext())

            rvRiwayat.adapter = RiwayatPoinAdapter(RiwayatManager.dataRiwayat)
        }
    }
}