package com.cst2355.ilgu0001

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class ProfileActivity : AppCompatActivity() { //eskfdfkls

    private var nameText: EditText? = null
    private var emailText: EditText? = null
    private var mImageButton: ImageButton? = null

    val REQUEST_IMAGE_CAPTURE = 1
    val ACTIVITY_NAME = "PROFILE_ACTIVITY"

    private var functionMessage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        functionMessage = getString(R.string.FunctionMessage)

        Log.e(ACTIVITY_NAME, functionMessage + "onCreate")

        nameText = findViewById(R.id.NameText)
        emailText = findViewById(R.id.MailText)
        mImageButton = findViewById(R.id.PhotoButton)

        var localEmailText = this.emailText
        var localMImageButton = this.mImageButton

        val fromMain = intent
        localEmailText?.setText(fromMain.getStringExtra("EMAIL"))

        localMImageButton?.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(ACTIVITY_NAME, functionMessage + "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(ACTIVITY_NAME, functionMessage + "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(ACTIVITY_NAME, functionMessage + "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(ACTIVITY_NAME, functionMessage + "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(ACTIVITY_NAME, functionMessage + "onDestroy")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data!!.extras
            val imageBitmap = extras!!["data"] as Bitmap?
            mImageButton?.setImageBitmap(imageBitmap)
        }
    }
}