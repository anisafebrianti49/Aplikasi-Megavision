package com.example.aplikasimegavision

object RiwayatManager {
    // Menggunakan mutableListOf supaya daftarnya bisa ditambah data baru
    val dataRiwayat = mutableListOf(
        RiwayatPoin("Tukar Voucher Diskon Rp 50.000", "05 Juli 2026 • 10:15 WIB", 450, false),
        RiwayatPoin("Pembayaran Tagihan Juli", "01 Juli 2026 • 08:30 WIB", 100, true),
        RiwayatPoin("Bonus Pengguna Setia", "28 Juni 2026 • 16:45 WIB", 50, true),
        RiwayatPoin("Tukar Voucher Diskon Rp 20.000", "20 Juni 2026 • 19:20 WIB", 200, false),
        RiwayatPoin("Pembayaran Tagihan Juni", "02 Juni 2026 • 09:10 WIB", 100, true),
        RiwayatPoin("Pendaftaran Akun Baru", "01 Juni 2026 • 14:00 WIB", 500, true)
    )
}