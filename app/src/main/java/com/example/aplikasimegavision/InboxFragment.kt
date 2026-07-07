package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class InboxFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inbox, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inisialisasi komponen View dari XML
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutInbox)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerInbox)

        // 2. Pasang Adapter ke ViewPager2
        val adapter = InboxPagerAdapter(this)
        viewPager.adapter = adapter

        // 3. Hubungkan TabLayout dengan ViewPager2 serta Beri Nama Tab-nya
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Semua"
                1 -> tab.text = "Personal"
                2 -> tab.text = "Promo"
            }
        }.attach()
    }
}