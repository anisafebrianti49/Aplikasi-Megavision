package com.example.aplikasimegavision

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class RiwayatPoin(
    val judul: String,
    val tanggal: String,
    val poin: Int,
    val isPoinMasuk: Boolean
)

class RiwayatPoinAdapter(private val listRiwayat: List<RiwayatPoin>) : RecyclerView.Adapter<RiwayatPoinAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = view.findViewById(R.id.tvJudulRiwayat)
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggalRiwayat)
        val tvPoin: TextView = view.findViewById(R.id.tvJumlahPoin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat_point, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val riwayat = listRiwayat[position]

        holder.tvJudul.text = riwayat.judul
        holder.tvTanggal.text = riwayat.tanggal

        if (riwayat.isPoinMasuk) {
            holder.tvPoin.text = "+${riwayat.poin} Poin"
            holder.tvPoin.setTextColor(Color.parseColor("#00A050"))
        } else {
            holder.tvPoin.text = "-${riwayat.poin} Poin"
            holder.tvPoin.setTextColor(Color.parseColor("#E53935"))
        }
    }

    override fun getItemCount(): Int = listRiwayat.size
}