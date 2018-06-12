package com.example.ytnb.neverforget

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val fragments = arrayOf(
            "com.example.ytnb.neverforget.MysizeFragment",
            "com.example.ytnb.neverforget.PropertyFragment",
            "com.example.ytnb.neverforget.MemorialFragment"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        drawer_layout.openDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_mysize -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, Fragment.instantiate(this@MainActivity, fragments[0]))
                        .commit()
            }
            R.id.nav_property -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, Fragment.instantiate(this@MainActivity, fragments[1]))
                        .commit()
            }
            R.id.nav_memorial -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, Fragment.instantiate(this@MainActivity, fragments[2]))
                        .commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
