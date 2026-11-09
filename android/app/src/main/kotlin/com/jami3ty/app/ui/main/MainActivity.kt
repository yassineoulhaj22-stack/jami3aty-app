package com.jami3ty.app.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jami3ty.app.R
import com.jami3ty.app.ui.discover.DiscoverFragment
import com.jami3ty.app.ui.home.HomeFragment
import com.jami3ty.app.ui.inbox.InboxFragment
import com.jami3ty.app.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> show(HomeFragment())
                R.id.nav_discover -> show(DiscoverFragment())
                R.id.nav_inbox -> show(InboxFragment())
                R.id.nav_profile -> show(ProfileFragment())
                R.id.nav_plus -> {
                    Toast.makeText(this, R.string.coming_soon, Toast.LENGTH_SHORT).show()
                    false
                }
                else -> false
            }
        }
        if (savedInstanceState == null) nav.selectedItemId = R.id.nav_home
    }

    private fun show(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        return true
    }
}
