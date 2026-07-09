package com.example.aplikasimegavision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VoucherAdapter(
    private val voucherList: List<Voucher>,
    private val onItemClick: (Voucher) -> Unit
) : RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>() {
    inner class VoucherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaVoucher: TextView = itemView.findViewById(R.id.tvNamaVoucher)
        private val tvDeskripsiSingkat: TextView = itemView.findViewById(R.id.tvDeskripsiSingkat)
        private val tvPoinDibutuhkan: TextView = itemView.findViewById(R.id.tvPoinDibutuhkan)

        private val tvDiskon: TextView = itemView.findViewById(R.id.tvDiskon)

        fun bind(voucher: Voucher) {
            tvNamaVoucher.text = voucher.namaVoucher
            tvDeskripsiSingkat.text = voucher.deskripsi
            tvPoinDibutuhkan.text = "${voucher.poinDibutuhkan} Poin"

            val nominalSaja = voucher.namaVoucher.substringAfter("Diskon Tagihan ")
            tvDiskon.text = "Potongan $nominalSaja pada tagihan ini!"

            itemView.setOnClickListener {
                onItemClick(voucher)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_katalog_voucher, parent, false)
        return VoucherViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        holder.bind(voucherList[position])
    }

    override fun getItemCount(): Int = voucherList.size
}