package com.example.aplikasimegavision

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inisialisasi Bottom Navigation View dari Layout activity_main.xml
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 2. Set halaman default pertama kali aplikasi dibuka
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TagihanFragment())
                .commit()
        }

        // 3. Logika perpindahan halaman saat menu bawah diklik
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_tagihan -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, TagihanFragment())
                        .commit()
                    true
                }
                R.id.menu_inbox -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, InboxFragment())
                        .commit()
                    true
                }
                R.id.menu_bantuan -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BantuanFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}