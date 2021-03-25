package com.cst2355.ilgu0001

import android.R.layout
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class EmptyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)

        val dataToPass = intent.extras
        val dFragment = DetailsFragment()
        dFragment.arguments = dataToPass

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, dFragment)
            .commit()
    }
}