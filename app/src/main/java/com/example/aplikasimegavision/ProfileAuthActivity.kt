package com.example.myappmegavision.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.ActivityProfileAuthBinding

class ProfileAuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup NavController dari NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_auth) as NavHostFragment
        navController = navHostFragment.navController
    }

    // Handle tombol back hardware agar sesuai dengan back stack Navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}