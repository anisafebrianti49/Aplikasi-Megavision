package com.example.aplikasimegavision

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity_dashboard : AppCompatActivity() {

    private lateinit var homeContent: View
    private lateinit var fragmentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        // Inisialisasi View sesuai ID yang ada di activity_main.xml
        // tv_email DIHAPUS karena sudah tidak ada lagi di XML (header
        // sekarang cuma Welcome + Username + Status)
        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val btnGantiAkun = findViewById<Button>(R.id.btn_ganti_akun)
        val btnCsPengaduan = findViewById<CardView>(R.id.btn_cs_pengaduan)

        // Di xml kamu, btn_cs_floating dibuat menggunakan CardView, bukan ImageButton
        val btnCsFloating = findViewById<CardView>(R.id.btn_cs_floating)
        val btnMetodePembayaran = findViewById<CardView>(R.id.btn_metode_pembayaran)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val btnUpgradePaket = findViewById<CardView>(R.id.btn_upgrade_paket)
        homeContent = findViewById(R.id.home_content)
        fragmentContainer = findViewById(R.id.fragment_container)

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
            val intent = Intent(this, HalamanPengaduan_CS::class.java)
            startActivity(intent)
        }

        btnMetodePembayaran.setOnClickListener {
            showFragment(MetodePembayaranFragment())
        }

        btnCsPengaduan.setOnClickListener {
            val intent = Intent(this, HalamanPengaduan_CS::class.java)
            startActivity(intent)
        }

        btnUpgradePaket.setOnClickListener {
            showFragment(UpgradePaketFragment())
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_beranda -> {
                    showHome()
                    true
                }
                R.id.nav_tagihan -> {
                    showFragment(TagihanFragment())
                    true
                }
                R.id.nav_inbox -> {
                    showFragment(InboxFragment())
                    true
                }
                R.id.nav_voucher -> {
                    // TODO: ganti Toast ini kalau halaman Voucher sudah dibuat
                    Toast.makeText(this, "Fitur Voucher segera hadir!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profil -> {
                    // Buka ProfileAuthActivity secara normal -> mulai dari
                    // LoginFragment (sesuai start destination di nav_graph_auth).
                    // User harus login dulu sebelum bisa masuk ke ProfileFragment.
                    val intent = Intent(this, ProfileAuthActivity::class.java)
                    startActivity(intent)
                    // Tetap tampilkan Beranda di belakang layar dashboard
                    showHome()
                    true
                }
                else -> false
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            if (fragmentContainer.visibility == View.VISIBLE) {
                bottomNavigation.selectedItemId = R.id.nav_beranda
            } else {
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    /** Menampilkan kembali konten Beranda dan menyembunyikan fragment container. */
    private fun showHome() {
        fragmentContainer.visibility = View.GONE
        homeContent.visibility = View.VISIBLE
        supportFragmentManager.popBackStack(
            null,
            androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    /** Menyembunyikan Beranda dan menampilkan fragment yang dipilih di fragment_container. */
    private fun showFragment(fragment: androidx.fragment.app.Fragment) {
        homeContent.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}