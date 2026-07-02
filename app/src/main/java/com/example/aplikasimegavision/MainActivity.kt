package com.example.aplikasimegavision

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TagihanFragment())
                .commit()
        }

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
                    // DIUBAH DI SINI: Kembalikan ke BantuanFragment() utama
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