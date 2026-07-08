package com.example.aplikasimegavision

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class VoucherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Memanggil wadah yang barusan kita buat
        setContentView(R.layout.activity_voucher)

        // Langsung memunculkan Katalog Voucher saat halaman ini dibuka
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, VoucherCatalogFragment())
                .commit()
        }
    }
}