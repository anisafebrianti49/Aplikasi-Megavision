package com.example.aplikasimegavision

import com.example.aplikasimegavision.R
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi View sesuai ID yang ada di activity_main.xml
        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val tvEmail = findViewById<TextView>(R.id.tv_email)
        val btnGantiAkun = findViewById<Button>(R.id.btn_ganti_akun)

        // Di xml kamu, btn_cs_floating dibuat menggunakan CardView, bukan ImageButton
        val btnCsFloating = findViewById<CardView>(R.id.btn_cs_floating)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        btnGantiAkun.setOnClickListener {
            Toast.makeText(this, "Membuka Panel Ganti Akun", Toast.LENGTH_SHORT).show()
        }

        btnCsFloating.setOnClickListener {
            Toast.makeText(this, "Menghubungi Sales Advisor Resmi...", Toast.LENGTH_SHORT).show()
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_beranda -> {
                    Toast.makeText(this, "Halaman Beranda Aktif", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_tagihan -> {
                    Toast.makeText(this, "Rincian Tagihan Anda: Rp 62.500", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    Toast.makeText(this, "Fitur segera hadir!", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }
    }
}