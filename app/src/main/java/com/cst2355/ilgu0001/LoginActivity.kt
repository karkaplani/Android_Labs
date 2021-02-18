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

    private lateinit var emailText: EditText
    private lateinit var loginButton: Button
    private var email: String? = null //Email gotten

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(activity_login)

        loginButton = findViewById(R.id.LoginButton)

        val localLoginButton = this.loginButton
        emailText = findViewById<EditText>(R.id.EmailText)

        val prefs = getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE)
        email = prefs.getString(emailP, "")
        emailText?.setText(email)

        localLoginButton?.setOnClickListener {
            val goToProfile = Intent(this@LoginActivity, ProfileActivity::class.java)
            goToProfile.putExtra("EMAIL", emailText?.text.toString())
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
