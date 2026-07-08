package com.example.aplikasimegavision

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.aplikasimegavision.databinding.ActivityProfileAuthBinding

class ProfileAuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment_auth
            ) as NavHostFragment

        navController = navHostFragment.navController

        // Cek status login dari SharedPreferences
        val prefs = getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getString("USER_ID", null)
        val sudahLogin = !userId.isNullOrEmpty()

        // Kalau sudah login, ubah start destination-nya jadi langsung ke ProfileFragment
        if (sudahLogin) {
            val navGraph = navController.navInflater.inflate(R.navigation.nav_graph_auth)
            navGraph.setStartDestination(R.id.profileFragment)
            navController.graph = navGraph
        }
        // Kalau belum login, biarkan default (mulai dari loginFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}