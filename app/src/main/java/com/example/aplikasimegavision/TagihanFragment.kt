package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TagihanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tagihan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnDownload = view.findViewById<Button>(R.id.btnDownloadTagihan)
        val btnMetode = view.findViewById<Button>(R.id.btnMetodePembayaran)
        val rvRiwayat = view.findViewById<RecyclerView>(R.id.rvRiwayat)

        btnDownload.setOnClickListener {
            Toast.makeText(requireContext(), "Downloading...", Toast.LENGTH_SHORT).show()
        }

        btnMetode.setOnClickListener {
            val fragmentTujuan = MetodePembayaranFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentTujuan)
                .addToBackStack(null)
                .commit()
        }

        val dataRiwayat = listOf(
            RiwayatModel("26 Apr 26", "Transfer Mandiri (VA Mandiri)", "Rp. 850.000", "Success"),
            RiwayatModel("13 Apr 26", "Transfer Mandiri (VA Mandiri)", "Rp. 50.000", "Success"),
            RiwayatModel("13 Apr 26", "Transfer Mandiri (VA Mandiri)", "Rp. 54.000", "Success"),
            RiwayatModel("14 Mar 26", "Transfer VA BCA (VA BCA)", "Rp. 144.225", "Success"),
            RiwayatModel("11 Feb 26", "Transfer Mandiri (VA Mandiri)", "Rp. 110.000", "Success")
        )

        rvRiwayat.layoutManager = LinearLayoutManager(requireContext())
        rvRiwayat.adapter = RiwayatAdapter(dataRiwayat)
    }
}