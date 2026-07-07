package com.example.aplikasimegavision

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.aplikasimegavision.UI.pengaduan.PengaduanScreen

class HalamanPengaduan_CS : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                // LANGSUNG MENAMPILKAN HALAMAN PENGADUAN
                // Saat tombol back di halaman pengaduan diklik, activity akan otomatis ditutup
                PengaduanScreen(
                    onBackClicked = { finish() }
                )
            }
        }
    }
}