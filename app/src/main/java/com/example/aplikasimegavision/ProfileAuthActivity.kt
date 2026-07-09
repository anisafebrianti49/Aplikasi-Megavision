package com.example.aplikasimegavision

import android.content.Context
import android.content.Intent
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

        val prefs = getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        val userId = prefs.getString("USER_ID", null)
        val sudahLogin = !userId.isNullOrEmpty()

        if (sudahLogin) {
            startActivity(Intent(this, MainActivity_dashboard::class.java))
            finish()
            return
        }

        binding = ActivityProfileAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment_auth
            ) as NavHostFragment

        navController = navHostFragment.navController
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