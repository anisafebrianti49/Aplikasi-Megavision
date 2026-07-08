package com.example.aplikasimegavision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VoucherAdapter(
    private val voucherList: List<Voucher>,
    private val onItemClick: (Voucher) -> Unit // Fungsi lambda untuk mendeteksi klik
) : RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>() {

    // 1. ViewHolder: Tempat kita menghubungkan ID dari XML ke variabel Kotlin
    inner class VoucherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaVoucher: TextView = itemView.findViewById(R.id.tvNamaVoucher)
        private val tvDeskripsiSingkat: TextView = itemView.findViewById(R.id.tvDeskripsiSingkat)
        private val tvPoinDibutuhkan: TextView = itemView.findViewById(R.id.tvPoinDibutuhkan)

        // INI TAMBAHANNYA: Menghubungkan ID tvDiskon dari XML
        private val tvDiskon: TextView = itemView.findViewById(R.id.tvDiskon)

        fun bind(voucher: Voucher) {
            // Memasukkan data ke masing-masing TextView
            tvNamaVoucher.text = voucher.namaVoucher
            tvDeskripsiSingkat.text = voucher.deskripsi
            tvPoinDibutuhkan.text = "${voucher.poinDibutuhkan} Poin"

            // LOGIKA BARU: Membuat teks hijau otomatis
            // Mengambil bagian "Rp 10.000" dari string "Diskon Tagihan Rp 10.000"
            val nominalSaja = voucher.namaVoucher.substringAfter("Diskon Tagihan ")
            tvDiskon.text = "Potongan $nominalSaja pada tagihan ini!"

            // Memberikan aksi ketika kartu (item) diklik
            itemView.setOnClickListener {
                onItemClick(voucher)
            }
        }
    }

    // 2. onCreateViewHolder: Memanggil layout item_katalog_voucher.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_katalog_voucher, parent, false)
        return VoucherViewHolder(view)
    }

    // 3. onBindViewHolder: Menyuruh ViewHolder memasang data sesuai urutan (posisi)
    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        holder.bind(voucherList[position])
    }

    // 4. getItemCount: Memberi tahu adapter berapa total data yang ada
    override fun getItemCount(): Int = voucherList.size
}