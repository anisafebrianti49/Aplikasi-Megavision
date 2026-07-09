package com.example.aplikasimegavision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RiwayatAdapter(private val listRiwayat: List<RiwayatModel>) :
    RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggal)
        val tvMetode: TextView = view.findViewById(R.id.tvMetode)
        val tvNominal: TextView = view.findViewById(R.id.tvNominal)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val btnDownloadItem: TextView = view.findViewById(R.id.btnDownloadItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listRiwayat[position]

        holder.tvTanggal.text = data.tanggal
        holder.tvMetode.text = data.metode
        holder.tvNominal.text = data.nominal
        holder.tvStatus.text = data.status

        holder.btnDownloadItem.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Mengunduh nota tanggal ${data.tanggal}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = listRiwayat.size
}