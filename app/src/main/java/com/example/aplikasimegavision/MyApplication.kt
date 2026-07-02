package com.example.aplikasimegavision

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Ini kunci agar Firebase aktif di seluruh proses aplikasi
        FirebaseApp.initializeApp(this)
    }
}