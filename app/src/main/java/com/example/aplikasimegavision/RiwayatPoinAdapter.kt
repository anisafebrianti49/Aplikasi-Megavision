package com.example.aplikasimegavision

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 1. Cetakan Datanya
data class RiwayatPoin(
    val judul: String,
    val tanggal: String,
    val poin: Int,
    val isPoinMasuk: Boolean // true untuk poin nambah (+), false untuk poin kurang (-)
)

// 2. Adapternya
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

        // Logika untuk mengubah warna teks poin (+ hijau, - merah)
        if (riwayat.isPoinMasuk) {
            holder.tvPoin.text = "+${riwayat.poin} Poin"
            holder.tvPoin.setTextColor(Color.parseColor("#00A050")) // Hijau
        } else {
            holder.tvPoin.text = "-${riwayat.poin} Poin"
            holder.tvPoin.setTextColor(Color.parseColor("#E53935")) // Merah
        }
    }

    override fun getItemCount(): Int = listRiwayat.size
}