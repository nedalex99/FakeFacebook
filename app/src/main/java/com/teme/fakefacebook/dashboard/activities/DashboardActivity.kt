package com.teme.fakefacebook.dashboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.teme.fakefacebook.R
import com.teme.fakefacebook.dashboard.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val id = FirebaseAuth.getInstance().currentUser?.uid

        setSupportActionBar(findViewById(R.id.toolbar))

        val adapter = ViewPagerAdapter(this, 5)
        view_pager2?.adapter = adapter
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(tab_layout, view_pager2) { tab, position ->
            /*when (position) {
                0 -> {
                    //tab.text = "Home"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_home_black)
                }
                1 -> {
                    //tab.text = "Groups"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_groups_black)
                }
                2 -> {
                    //tab.text = "MarketPlace"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_market_black)
                }
                3 -> {
                    //tab.text = "Notifications"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_notifications_black)
                }
                4 -> {
                    //tab.text = "Menu"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_menu_black)
                }
            }*/
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
}