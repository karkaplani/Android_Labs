package com.cst2355.ilgu0001

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class TestToolbar : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        const val RESPONSE_RETURN_TO_LOGIN = 500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toolbar)

        val tBar = findViewById<View>(R.id.myToolbar) as Toolbar
        setSupportActionBar(tBar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, tBar, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var message = ""
        when (item.itemId) {
            R.id.choice1 -> message = "You Clicked on Item 1"
            R.id.choice2 -> message = "You Clicked on Item 2"
            R.id.choice3 -> message = "You Clicked on Item 3"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        lateinit var message: String
        lateinit var nextActivity: Intent

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        when (item.itemId) {
            R.id.drawerChatButton -> {
                message = "You clicked on Chat"
                nextActivity = Intent(this@TestToolbar, ChatRoomActivity::class.java)
                startActivity(nextActivity)
            }
            R.id.drawerWeatherButton -> {
                message = "You clicked on Weather"
                nextActivity = Intent(this@TestToolbar, WeatherForecast::class.java)
            }
            R.id.drawerLoginButton -> {
                message = "You clicked on Login"
                setResult(RESPONSE_RETURN_TO_LOGIN, Intent())
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        Toast.makeText(this, "NavigationDrawer: $message", Toast.LENGTH_SHORT).show()

        //nextActivity?.let { startActivity(it) }
        startActivity(nextActivity)
        return false
    }
}