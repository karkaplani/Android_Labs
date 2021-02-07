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


class ProfileActivity : AppCompatActivity() {

    private var nameText: EditText? = null
    private var emailText: EditText? = null
    private var mImageButton: ImageButton? = null

    val REQUEST_IMAGE_CAPTURE = 1
    val ACTIVITY_NAME = "PROFILE_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Log.e(ACTIVITY_NAME, " in function: " + "onCreate")

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

    @Override
    protected fun onActivityResultt(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data.extras
            val imageBitmap = extras!!["data"] as Bitmap?
            mImageButton?.setImageBitmap(imageBitmap)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(ACTIVITY_NAME, " in function: " + "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.e(ACTIVITY_NAME, " in function: " + "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(ACTIVITY_NAME, " in function: " + "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.e(ACTIVITY_NAME, " in function: " + "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.e(ACTIVITY_NAME, " in function: " + "onResume")
    }
}