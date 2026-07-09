# 📱 MyApp

[![Platform Android](https://img.shields.io/badge/Platform-Android%20Native-green?style=flat-square&logo=android)](https://developer.android.com)
[![Language Kotlin](https://img.shields.io/badge/Language-Kotlin-purple?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen?style=flat-square)](#)
[![Academic Project](https://img.shields.io/badge/Project-UAS%20Pemrograman%20Mobile%201-blue?style=flat-square)](#)

---

## 📄 1. Latar Belakang & Deskripsi Proyek
Aplikasi **MyApp** merupakan solusi digital berbasis mobile yang dikembangkan khusus untuk **PT Megavision (PT Cemerlang Multimedia)**. Berdasarkan analisis sistem yang berjalan pada laporan OOAD Kelompok 2, aplikasi ini dirancang untuk mentransformasi dan mengintegrasikan layanan pelanggan konvensional menjadi ekosistem digital yang responsif, transparan, dan efisien.

Aplikasi ini dibangun secara murni menggunakan **Android Native (Kotlin)** tanpa framework cross-platform, mengutamakan performa optimal, manajemen memori yang efisien, dan kepatuhan penuh terhadap *Android Jetpack Guidelines*.

### Masalah yang Diselesaikan:
- Memangkas antrean manual pendaftaran pelanggan baru.
- Mempercepat pelaporan gangguan jaringan (komplain) secara real-time.
- Menyediakan transparansi data tagihan bulanan dan riwayat pembayaran pelanggan.
- Meningkatkan retensi pelanggan melalui sistem interaktif akumulasi dan penukaran poin loyalitas.

---

## 👥 2. Daftar Anggota Kelompok & Pembagian Peran (Kelompok 2)
Proyek ini dikembangkan oleh Kelompok 2 - Kelas **TIF RP 24D CNS**, Departemen Teknik Informatika, Fakultas Industri Kreatif, **Universitas Teknologi Bandung (UTB)**.

| Nama Anggota | NPM / NIM | Peran Utama & Tanggung Jawab Teknis |
| :--- | :---: | :--- |
| **Anisa Febrianti** | 24552011287 | **Developer Fitur Autentikasi & Profil**<br>• Merancang dan mengimplementasikan halaman **Login**.<br>• Membuat antarmuka **Profile** pengguna. |
| **Dhenia Putri Nuraini** | 24552011311 | **Developer Fitur Layanan & Reward**<br>• Mengembangkan fitur **Upgrade Paket**.<br>• Membuat antarmuka dan integrasi **Live Chat**.<br>• Mengimplementasikan fitur penukaran **Voucher / Poin Loyalitas**. |
| **Ega Silfhia** | 24552011313 | **Frontend Integrator & Core Developer**<br>• Mengembangkan **Halaman Utama (Dashboard)**.<br>• Bertanggung jawab menyatukan (integrasi) seluruh halaman/fitur dari anggota lain agar menjadi satu aplikasi yang utuh dan berjalan lancar tanpa *crash*. |
| **Fitri Aulia** | 24552011318 | **Developer Fitur Tagihan & Dukungan**<br>• Mengimplementasikan halaman **Tagihan (Billing)**.<br>• Membuat antarmuka **Inbox** (Kotak Masuk).<br>• Mengembangkan fitur **Bantuan** (Help Center). |

---

## 🚀 3. Fitur Utama Aplikasi
Aplikasi ini memiliki 5 modul utama yang berjalan secara dinamis:

1. **Modul Autentikasi & Profil Pelanggan**
    - Registrasi pelanggan baru dengan validasi input yang ketat.
    - Login aman dan manajemen profil pengguna.
2. **Modul Katalog Produk & Promo Interaktif**
    - Menampilkan daftar paket internet dan TV kabel PT Megavision yang tersedia.
    - Penampilan promo terbaru menggunakan komponen *Slider/Carousel View*.
3. **Modul Manajemen Tagihan (Billing System)**
    - Pengecekan jumlah tagihan aktif secara real-time.
    - Menampilkan riwayat pembayaran sukses dan *inbox* notifikasi.
4. **Modul Sistem Keluhan & Bantuan (Ticketing Layanan)**
    - Pusat Bantuan dan *Live Chat* untuk komunikasi dengan Customer Service.
    - Fitur untuk melakukan *Upgrade Paket* langganan internet.
5. **Modul Loyalitas & Reward Poin**
    - Akumulasi poin otomatis setiap setelah melakukan pembayaran tagihan.
    - Katalog penukaran poin dengan *voucher* potongan harga layanan.

---

## 🛠️ 4. Teknologi & Library yang Digunakan (Tech Stack)
Aplikasi dikembangkan menggunakan ekosistem Android modern:
- **Bahasa Pemrograman:** Kotlin 1.9+
- **Arsitektur:** MVVM (Model-View-ViewModel) Pattern
- **UI Framework:** Android Native View System (XML Layouts, Material Design 3 Components)
- **Asynchronous & Concurrency:** Kotlin Coroutines & Flow
- **Networking / API:** Retrofit 2 & OkHttp (untuk komunikasi data dengan server)
- **Local Database / Persistence:** Room Database (manajemen caching data lokal)
- **Image Loading:** Glide / Coil (pemuatan gambar asinkron berkinerja tinggi)
- **Jetpack Components:** ViewModel, LiveData, Navigation Component, ViewBinding

---

## 📂 5. Struktur Repositori Proyek
Repositori ini disusun secara rapi sesuai standar industri dan instruksi UAS:

```text
Aplikasi-Megavision/
├── apk/
│   └── app-release.apk            <-- File APK produksi siap instal (Release Build)
├── docs/
│   └── Laporan_OOAD_Kelompok2.pdf <-- Dokumen analisis OOAD PT Megavision
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/megavision/app/
│   │   │   │   ├── data/          <-- Lapisan Data (Repository, Room DB, Retrofit API)
│   │   │   │   ├── ui/            <-- Lapisan UI (Activity, Fragment, Adapter, ViewModel)
│   │   │   │   │   ├── auth/
│   │   │   │   │   ├── dashboard/
│   │   │   │   │   ├── billing/
│   │   │   │   │   └── rewards/
│   │   │   │   └── utils/         <-- Helper & Kelas Ekstensi
│   │   │   └── res/               <-- Aset Desain (Layout, Drawable, Values, Font)
│   └── build.gradle.kts
├── build.gradle.kts
└── README.md                      <-- Dokumentasi Proyek Ini