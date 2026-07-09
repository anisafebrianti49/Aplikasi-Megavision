# 📱 MyApp

## 📄 1. Latar Belakang & Deskripsi Proyek
Aplikasi **MyApp** merupakan solusi digital berbasis mobile yang dikembangkan khusus untuk **PT Megavision (PT Cemerlang Multimedia)**. Berdasarkan analisis sistem yang berjalan pada laporan OOAD Kelompok 2, aplikasi ini dirancang untuk mentransformasi dan mengintegrasikan layanan pelanggan konvensional menjadi ekosistem digital yang responsif, transparan, dan efisien.

Aplikasi ini dibangun secara murni menggunakan **Android Native (Kotlin)** tanpa framework cross-platform, mengutamakan performa optimal, manajemen memori yang efisien, dan kepatuhan penuh terhadap Android Jetpack Guidelines.

**Masalah yang Diselesaikan:**
* Memangkas antrean manual pendaftaran pelanggan baru.
* Mempercepat pelaporan gangguan jaringan (komplain) secara real-time.
* Menyediakan transparansi data tagihan bulanan dan riwayat pembayaran pelanggan.
* Meningkatkan retensi pelanggan melalui sistem interaktif akumulasi dan penukaran poin loyalitas.

## 👥 2. Daftar Anggota Kelompok & Pembagian Peran (Kelompok 2)
Proyek ini dikembangkan oleh Kelompok 2 - Kelas TIF RP 24D CNS, Departemen Teknik Informatika, Fakultas Industri Kreatif, Universitas Teknologi Bandung (UTB).

| Nama Anggota | NPM / NIM | Peran Utama & Tanggung Jawab Teknis |
| :--- | :---: | :--- |
| **Anisa Febrianti** | 24552011287 | **Developer Fitur Autentikasi & Profil**<br>• Merancang dan mengimplementasikan halaman Login.<br>• Membuat antarmuka Profile pengguna. |
| **Dhenia Putri Nuraini** | 24552011311 | **Developer Fitur Layanan & Reward**<br>• Mengembangkan fitur Upgrade Paket.<br>• Membuat antarmuka dan integrasi Live Chat.<br>• Mengimplementasikan fitur penukaran Voucher / Poin Loyalitas. |
| **Ega Silfhia** | 24552011313 | **Frontend Integrator & Core Developer**<br>• Mengembangkan Halaman Utama (Dashboard).<br>• Bertanggung jawab menyatukan (integrasi) seluruh halaman/fitur dari anggota lain agar menjadi satu aplikasi yang utuh dan berjalan lancar tanpa crash. |
| **Fitri Aulia** | 24552011318 | **Developer Fitur Tagihan & Dukungan**<br>• Mengimplementasikan halaman Tagihan (Billing).<br>• Membuat antarmuka Inbox (Kotak Masuk).<br>• Mengembangkan fitur Bantuan (Help Center). |

## 🚀 3. Fitur Utama Aplikasi
Aplikasi ini memiliki 5 modul utama yang berjalan secara dinamis:

* **Modul Autentikasi & Profil Pelanggan**
  * Registrasi pelanggan baru dengan validasi input yang ketat.
  * Login aman dan manajemen profil pengguna.
* **Modul Katalog Produk & Promo Interaktif**
  * Menampilkan daftar paket internet dan TV kabel PT Megavision yang tersedia.
  * Penampilan promo terbaru menggunakan komponen Slider/Carousel View.
* **Modul Manajemen Tagihan (Billing System)**
  * Pengecekan jumlah tagihan aktif secara real-time.
  * Menampilkan riwayat pembayaran sukses dan inbox notifikasi.
* **Modul Sistem Keluhan & Bantuan (Ticketing Layanan)**
  * Pusat Bantuan dan Live Chat untuk komunikasi dengan Customer Service.
  * Fitur untuk melakukan Upgrade Paket langganan internet.
* **Modul Loyalitas & Reward Poin**
  * Akumulasi poin otomatis setiap setelah melakukan pembayaran tagihan.
  * Katalog penukaran poin dengan voucher potongan harga layanan.

## 🛠️ 4. Teknologi & Library yang Digunakan (Tech Stack)
Aplikasi dikembangkan menggunakan ekosistem Android modern:
* **Bahasa Pemrograman:** Kotlin 1.9+
* **Arsitektur:** MVVM (Model-View-ViewModel) Pattern
* **UI Framework:** Android Native View System (XML Layouts, Material Design 3 Components)
* **Asynchronous & Concurrency:** Kotlin Coroutines & Flow
* **Networking / API:** Retrofit 2 & OkHttp (untuk komunikasi data dengan server)
* **Local Database / Persistence:** Room Database (manajemen caching data lokal)
* **Image Loading:** Glide / Coil (pemuatan gambar asinkron berkinerja tinggi)
* **Jetpack Components:** ViewModel, LiveData, Navigation Component, ViewBinding

## 📂 5. Struktur Repositori Proyek
Repositori ini disusun secara rapi sesuai standar industri dan instruksi UAS:

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
    │   │   │   │   └── utils/         <-- Helper & Kelas Ekstensi
    │   │   │   └── res/               <-- Aset Desain (Layout, Drawable, Values, Font)
    │   └── build.gradle.kts
    ├── build.gradle.kts
    └── README.md                      <-- Dokumentasi Proyek Ini

## 📸 6. Cuplikan Aplikasi (Screenshots)
Berikut adalah tampilan dari aplikasi Megavision:

![Halaman 1](https://github.com/user-attachments/assets/ee3b4c7a-ec21-45b9-83a4-798b086f4b5a)

![Halaman 2](https://github.com/user-attachments/assets/759b361f-ff53-4d5d-87b9-a8016725bdf9)

## 🎥 7. Video Demo Aplikasi
Penasaran bagaimana aplikasinya berjalan secara langsung? Kamu bisa melihat video demonstrasi penuhnya beserta penjelasan fitur-fiturnya pada tautan Google Drive di bawah ini:

👉 https://drive.google.com/drive/folders/1aBOoS0Ys6ncG49XoiJrGdnPXF0BXjF6n 👈

---

## 💻 8. Cara Menjalankan Aplikasi (Setup & Cloning)

Terdapat dua cara untuk mencoba dan menjalankan aplikasi MyApp ini:

### Cara 1: Instalasi Langsung (Via File APK)
Ini adalah cara tercepat untuk menguji aplikasi langsung di *smartphone* Android tanpa perlu melakukan proses *build*.

1. Buka folder `apk/` yang ada di dalam repositori ini.
2. Unduh file `app-release.apk`.
3. Pindahkan file tersebut ke *smartphone* Android Anda.
4. Buka *File Manager*, klik APK tersebut untuk menginstal. *(Pastikan izin "Install from Unknown Sources" pada HP Anda sudah aktif).*
5. Aplikasi MyApp siap digunakan!

---

### Cara 2: Menjalankan Source Code (Via Android Studio & Firebase)
Karena aplikasi ini terintegrasi dengan layanan **Firebase**, ikuti langkah-langkah berikut agar *source code* dapat berjalan tanpa *error* di Android Studio:

1. **Clone Repository ke Komputer Lokal**
   Buka *Terminal*, *Command Prompt*, atau *Git Bash*, kemudian jalankan perintah berikut:
   git clone https://github.com/anisafebrianti49/Aplikasi-Megavision

2. **Buka Proyek di Android Studio**
  - Buka aplikasi **Android Studio**.
  - Klik menu **File -> Open**.
  - Cari dan pilih folder `Aplikasi-Megavision` hasil kloning tadi, lalu klik **OK**.

3. **Konfigurasi Firebase (`google-services.json`)**
  - Aplikasi ini membutuhkan koneksi ke *project* Firebase Kelompok 2.
  - Periksa ke dalam folder `app/` pada mode *Project*.
  - **Penting:** Pastikan file `google-services.json` sudah berada di dalam direktori `app/` tersebut. *(Catatan untuk Penguji: Jika file tersebut tidak terbawa saat cloning karena masuk ke dalam aturan `.gitignore`, mohon hubungi perwakilan kelompok kami untuk mendapatkan salinan file `google-services.json` tersebut).*

4. **Sinkronisasi Gradle (Gradle Sync)**
  - Pastikan komputer terhubung ke internet dengan stabil.
  - Android Studio akan secara otomatis mengunduh seluruh *library* dan *dependency* (termasuk *library* Firebase dan Retrofit).
  - Tunggu hingga proses *Gradle Sync* selesai tanpa indikator *loading* di pojok kanan bawah.

5. **Jalankan Aplikasi (Build & Run)**
  - Siapkan target *deployment* (HP Android fisik dengan USB Debugging aktif, atau jalankan Emulator AVD).
  - Setelah perangkat terdeteksi di menu atas, klik tombol hijau **Run 'app' (▶)** atau tekan `Shift + F10`.
  - Android Studio akan melakukan proses *building*, dan aplikasi akan terbuka secara otomatis.