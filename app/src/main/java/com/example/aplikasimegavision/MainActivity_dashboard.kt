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
import android.content.Context
import com.example.aplikasimegavision.UI.upgradepaket.UpgradePaketFragment
import com.example.aplikasimegavision.ui.profile.ProfileFragment


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
        homeContent = findViewById(R.id.home_content)
        fragmentContainer = findViewById(R.id.fragment_container)

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