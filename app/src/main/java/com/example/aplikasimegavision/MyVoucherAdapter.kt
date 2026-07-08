package com.example.aplikasimegavision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyVoucherAdapter(
    private val listVoucher: List<VoucherAktif>,
    private val onGunakanClick: (VoucherAktif) -> Unit
) : RecyclerView.Adapter<MyVoucherAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaVoucher: TextView = view.findViewById(R.id.tvNamaVoucherKu)
        val btnGunakan: View = view.findViewById(R.id.btnGunakan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_voucher, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voucher = listVoucher[position]

        // Mengubah teks judul sesuai data dari Firebase
        holder.tvNamaVoucher.text = voucher.namaVoucher

        // Mengirim data voucher ke Fragment untuk dihapus saat diklik
        holder.btnGunakan.setOnClickListener { onGunakanClick(voucher) }
    }

    override fun getItemCount(): Int = listVoucher.size
}