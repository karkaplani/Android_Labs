package com.cst2355.ilgu0001

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class ProfileActivity : AppCompatActivity() {

    private var nameText: EditText? = null
    private var emailText: EditText? = null
    private var mImageButton: ImageButton? = null

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_RETURN_TO_LOGIN = 2
    val RESPONSE_RETURN_TO_LOGIN = 500
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

        val chatButton: Button = findViewById(R.id.ChatButton)
        val weatherButton: Button = findViewById(R.id.WeatherButton)
        val toolbarButton: Button = findViewById(R.id.ToolbarButton)

        chatButton?.setOnClickListener{
            val goToChat = Intent(this@ProfileActivity, ChatRoomActivity::class.java)
            startActivity(goToChat)
        }

        weatherButton?.setOnClickListener{
            val goToForecast = Intent(this@ProfileActivity, WeatherForecast::class.java)
            startActivityForResult(goToForecast, REQUEST_RETURN_TO_LOGIN)
        }

        toolbarButton?.setOnClickListener{
            val goToolbar = Intent(this@ProfileActivity, TestToolbar::class.java)
            startActivity(goToolbar)
        }
    }

    private fun dispatchTakePictureIntent() {
        Log.e(ACTIVITY_NAME, "In dispatchTakePictureIntent")
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
        if(requestCode == REQUEST_RETURN_TO_LOGIN && resultCode == RESPONSE_RETURN_TO_LOGIN) {
            Log.e(ACTIVITY_NAME, "Returning to the login page.. ");
            finish();  //return to the login page.
        }
    }
}