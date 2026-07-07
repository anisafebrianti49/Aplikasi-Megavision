package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InboxTabFragment : Fragment() {

    companion object {
        fun newInstance(tabPosition: Int): InboxTabFragment {
            val fragment = InboxTabFragment()
            val args = Bundle()
            args.putInt("TAB_POSITION", tabPosition)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inbox_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvInboxItems = view.findViewById<RecyclerView>(R.id.rvInboxItems)
        val layoutEmptyState = view.findViewById<LinearLayout>(R.id.layoutEmptyState)

        val tabPosition = arguments?.getInt("TAB_POSITION") ?: 0

        if (tabPosition == 2) {
            // Khusus tab Promo dibuat kosong
            rvInboxItems.visibility = View.GONE
            layoutEmptyState.visibility = View.VISIBLE
        } else {
            // Tab Semua dan Personal menampilkan list data
            rvInboxItems.visibility = View.VISIBLE
            layoutEmptyState.visibility = View.GONE

            rvInboxItems.layoutManager = LinearLayoutManager(context)

            // Mengisi data dummy persis seperti gambar tugasmu
            val listPesan = ArrayList<InboxItem>()
            listPesan.add(InboxItem(
                "Pemberitahuan Pemblokiran",
                "15 Nov 2024",
                "Internet Anda terblokir, silahkan lakukan pembayaran agar dapat menikmati layanan internet kembali.",
                true
            ))
            listPesan.add(InboxItem(
                "Notifikasi Gangguan Pada Jaringan Internet",
                "24 May 2024",
                "Pelanggan Megavision yang Terhormat,\n\nMohon maaf..",
                true
            ))
            listPesan.add(InboxItem(
                "Informasi Maintenance Internet Megavision",
                "15 May 2024",
                "Kami menginformasikan bahwa demi meningkatkan kual..",
                true
            ))
            listPesan.add(InboxItem(
                "Promo Spesial Megavision: Bayar 4 Dapat 5!",
                "26 Mar 2024",
                "Halo Sobat MV,\n\nPromo khusus pelanggan setia! TH..",
                true
            ))
            listPesan.add(InboxItem(
                "Migrasi Channel Analog ke Digital",
                "30 Jan 2024",
                "Pelanggan Setia Megavision,\n\nPada akhir Januari ..",
                false // ini sudah dibaca (tidak ada titik merah)
            ))

            val adapter = InboxAdapter(listPesan)
            rvInboxItems.adapter = adapter
        }
    }
}