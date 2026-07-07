package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class DetailSolusiUpgradeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // FIX: Sudah diarahkan ke nama file XML yang benar sesuai tab atas Android Studio kamu
        return inflater.inflate(R.layout.fragment_detail_solusi_upgrade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageView>(R.id.btn_back_detail_upgrade)
        val btnYa = view.findViewById<Button>(R.id.btn_ya_solusi)
        val btnTidak = view.findViewById<Button>(R.id.btn_tidak_solusi)
        val tvSolusi = view.findViewById<TextView>(R.id.tv_deskripsi_solusi)

        // Mengisi teks konten solusi manual
        tvSolusi?.text = "1. Buka aplikasi browser Anda.\n2. Akses situs www.speedtest.net\n3. Klik tombol 'GO' dan tunggu hingga proses kalkulasi selesai."

        // 1. Aksi Tombol Back (Kembali ke list pertanyaan upgrade)
        btnBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 2. Aksi Tombol YA (Kembali ke Dasbor Bantuan Utama)
        btnYa?.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BantuanFragment())
                .commit()
        }

        btnTidak?.setOnClickListener {
            // TODO: Ini tombol ke Live Chat.
            // Menunggu digabungkan dengan class Live Chat yang asli buatan teman.
            // Jika sudah di-merge, hapus tanda komentar (//) di bawah ini dan sesuaikan nama fragment-nya:

            // parentFragmentManager.beginTransaction()
            //     .replace(R.id.fragment_container, NamaClassLiveChatAsli())
            //     .addToBackStack(null)
            //     .commit()
        }
        }
    }
