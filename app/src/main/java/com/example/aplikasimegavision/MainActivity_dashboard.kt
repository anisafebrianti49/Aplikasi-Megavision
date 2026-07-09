package com.example.aplikasimegavision

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.aplikasimegavision.UI.upgradepaket.UpgradePaketFragment
import com.example.aplikasimegavision.ui.profile.ProfileFragment

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity_dashboard : AppCompatActivity() {

    private lateinit var homeContent: View
    private lateinit var fragmentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val btnGantiAkun = findViewById<Button>(R.id.btn_ganti_akun)
        val btnCsPengaduan = findViewById<CardView>(R.id.btn_cs_pengaduan)
        val btnCsFloating = findViewById<CardView>(R.id.btn_cs_floating)
        val btnMetodePembayaran = findViewById<CardView>(R.id.btn_metode_pembayaran)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val btnUpgradePaket = findViewById<CardView>(R.id.btn_upgrade_paket)

        // Inisialisasi tombol Belum Dibayarkan dari kartu tagihan
        val btnBelumDibayar = findViewById<View>(R.id.btn_belum_dibayar) // GANTI DENGAN ID XML KAMU

        homeContent = findViewById(R.id.home_content)
        fragmentContainer = findViewById(R.id.fragment_container)

        // Ambil Data Nama dari Firebase
        val prefs = getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getString("USER_ID", "") ?: ""

        if (userId.isNotEmpty()) {
            val databaseRef = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("pelanggan").child(userId)

            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val namaUser = snapshot.child("nama").value?.toString() ?: ""
                        tvUsername.text = namaUser.ifEmpty { "User Name" }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity_dashboard, "Gagal memuat nama: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

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

        // Aksi ketika tombol Belum Dibayarkan diklik
        btnBelumDibayar.setOnClickListener {
            bottomNavigation.selectedItemId = R.id.nav_tagihan
        }

        btnGantiAkun.setOnClickListener {
            getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply()

            startActivity(Intent(this, ProfileAuthActivity::class.java))
            finish()
        }

        btnUpgradePaket.setOnClickListener {
            showFragment(UpgradePaketFragment())
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
                    val intent = Intent(this, VoucherActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_profil -> {
                    showFragment(ProfileFragment())
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

        intent.getIntExtra("SELECTED_NAV_ITEM", R.id.nav_beranda).let { itemId ->
            if (itemId != R.id.nav_beranda) {
                bottomNavigation.selectedItemId = itemId
            }
        }
    }

    private fun showHome() {
        fragmentContainer.visibility = View.GONE
        homeContent.visibility = View.VISIBLE
        supportFragmentManager.popBackStack(
            null,
            androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun showFragment(fragment: androidx.fragment.app.Fragment) {
        homeContent.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun showAuthNavHost() {
        homeContent.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE

        val navHostFragment = androidx.navigation.fragment.NavHostFragment.create(R.navigation.nav_graph_auth)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commit()
    }
}