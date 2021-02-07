package com.cst2355.ilgu0001

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.cst2355.ilgu0001.R.layout.activity_login


class LoginActivity : AppCompatActivity() {

    //Shared preference variables
    private val sharedPrefs = "sharedPrefs"
    private val emailP = "email"
    //Elements
    private var emailText: EditText? = null
    private var loginButton: Button? = null
    private var email: String? = null //Email gotten

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(activity_login)

        loginButton = findViewById(R.id.LoginButton)
        val localLoginButton = this.loginButton

        val prefs = getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE)
        email = prefs.getString(emailP, "")

        localLoginButton?.setOnClickListener {
            val goToProfile = Intent(this@LoginActivity, ProfileActivity::class.java)
            goToProfile.putExtra("email", emailText?.text.toString())
            startActivity(goToProfile)
        }
    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(emailP, emailText?.text.toString())
        edit.commit()
    }
}
