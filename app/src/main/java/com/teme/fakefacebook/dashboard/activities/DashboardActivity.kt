package com.teme.fakefacebook.dashboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teme.fakefacebook.R
import com.teme.fakefacebook.dashboard.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setSupportActionBar(findViewById(R.id.toolbar))

        setupViewPager()

        setupTabLayout()
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tab_layout, view_pager2) { tab, position ->
            tab.icon = when (position) {
                0 -> ContextCompat.getDrawable(this, R.drawable.ic_home_black)
                1 -> ContextCompat.getDrawable(this, R.drawable.ic_groups_black)
                2 -> ContextCompat.getDrawable(this, R.drawable.ic_market_black)
                3 -> ContextCompat.getDrawable(this, R.drawable.ic_notifications_black)
                4 -> ContextCompat.getDrawable(this, R.drawable.ic_menu_black)
                else -> ContextCompat.getDrawable(this, R.drawable.ic_menu_black)
            }
        }.attach()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this, 5)
        view_pager2?.adapter = adapter
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}