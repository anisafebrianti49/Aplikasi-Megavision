package com.example.aplikasimegavision

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity_dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        // Inisialisasi View sesuai ID yang ada di activity_main.xml
        // tv_email DIHAPUS karena sudah tidak ada lagi di XML (header
        // sekarang cuma Welcome + Username + Status)
        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val btnGantiAkun = findViewById<Button>(R.id.btn_ganti_akun)

        // Di xml kamu, btn_cs_floating dibuat menggunakan CardView, bukan ImageButton
        val btnCsFloating = findViewById<CardView>(R.id.btn_cs_floating)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Fix nav bar "gepeng" -> beri padding bawah sebesar tinggi
        // system navigation bar (gesture bar / 3-button nav), berlaku
        // otomatis di semua merk HP karena pakai API standar AndroidX
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigation) { view, insets ->
            val systemBarsInset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                systemBarsInset.bottom
            )
            insets
        }

        btnGantiAkun.setOnClickListener {
            Toast.makeText(this, "Membuka Panel Ganti Akun", Toast.LENGTH_SHORT).show()
        }

        btnCsFloating.setOnClickListener {
            val intent = Intent(this, FiturDheniaActivity::class.java)
            startActivity(intent)
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