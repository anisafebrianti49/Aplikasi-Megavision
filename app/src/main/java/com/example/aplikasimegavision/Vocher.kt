package com.example.aplikasimegavision

data class Voucher(
    val idVoucher: String,
    val namaVoucher: String,
    val deskripsi: String,
    val poinDibutuhkan: Int,
    val diskon: Double,
    var statusVoucher: String
)