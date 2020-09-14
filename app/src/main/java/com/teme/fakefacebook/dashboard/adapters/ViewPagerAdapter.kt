package com.teme.fakefacebook.dashboard.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teme.fakefacebook.dashboard.fragments.*

class ViewPagerAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = itemsCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> GroupsFragment()
            2 -> MarketPlaceFragment()
            3 -> GamingFragment()
            4 -> MenuFragment()
            else -> MenuFragment()
        }
    }
}