package com.cst2355.ilgu0001

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.cst2355.ilgu0001.R.layout.activity_main_linear
import com.google.android.material.snackbar.Snackbar
import com.cst2355.ilgu0001.R.layout.activity_main_relative as activity_main_relative

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main_relative)

        val myButton: Button = findViewById<Button>(R.id.myButton)
        val mySwitch : Switch = findViewById<Switch>(R.id.mySwitch)

        val toastText: String = getString(R.string.ToastText)
        val switchText: String = getString(R.string.SnackText)
        val switchOn: String = getString(R.string.SwitchOn)
        val switchOff: String = getString(R.string.SwitchOff)

        myButton.setOnClickListener {
            Toast.makeText(applicationContext, toastText, Toast.LENGTH_LONG).show()
        }

        mySwitch.setOnCheckedChangeListener { _, isChecked ->
            var b = false

            var state = switchOff
            if (isChecked) {
                state = switchOn
                b = true
            } else if (!isChecked) {
                state = switchOff
                b = false
            }
            var snackbar = Snackbar.make(mySwitch, "$switchText $state", Snackbar.LENGTH_LONG).show()
        }
    }
}
