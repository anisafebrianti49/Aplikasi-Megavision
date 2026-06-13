package com.example.aplikasimegavision

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class InboxPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    // Menentukan jumlah tab, yaitu ada 3 (Semua, Personal, Promo)
    override fun getItemCount(): Int = 3

    // Menentukan fragment mana yang dibuka berdasarkan posisi tabnya
    override fun createFragment(position: Int): Fragment {
        return InboxTabFragment.newInstance(position)
    }
}