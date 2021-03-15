package com.cst2355.ilgu0001

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


@Suppress("DEPRECATION")
class WeatherForecast : AppCompatActivity() {

    lateinit var weatherImage: ImageView
    lateinit var currentTemperature: TextView
    lateinit var minTemp: TextView
    lateinit var maxTemp: TextView
    lateinit var uvRating: TextView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        weatherImage = findViewById(R.id.weatherImage)
        currentTemperature = findViewById(R.id.currentTemp)
        minTemp = findViewById(R.id.minTemp)
        maxTemp = findViewById(R.id.maxTemp)
        uvRating = findViewById(R.id.uvRating)
        progressBar = findViewById(R.id.progressBar)

        progressBar.visibility = View.INVISIBLE

        val req = ForecastQuery()
        req.execute(
            "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric",
            "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389"
        )
    }

    @Suppress("DEPRECATION")
    inner class ForecastQuery : AsyncTask<String, kotlin.Int, String>() {

        lateinit var uv: String //Here
        lateinit var min: String
        lateinit var max: String
        lateinit var currentTemp: String
        lateinit var picture: Bitmap

        //http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric <- arg0
        //http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389 <- arg1

        override fun doInBackground(vararg args: String?): String? {
            try {

                //create a URL object of what server to contact:
                val url = URL(args[0])
                val uvUrl = URL(args[1])
                lateinit var icon: String

                //open the connection
                val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                val uvConnection: HttpURLConnection = uvUrl.openConnection() as HttpURLConnection

                //wait for data:
                val response: InputStream = urlConnection.inputStream
                val uvResponse: InputStream = uvConnection.inputStream

                //From part 3: slide 19
                val factory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = false
                val xpp = factory.newPullParser()
                xpp.setInput(response, "UTF-8")


                //From part 3, slide 20
                var eventType = xpp.eventType //The parser is currently at START_DOCUMENT

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        //If you get here, then you are pointing at a start tag
                        when (xpp.name) {
                            "temperature" -> {
                                currentTemp = xpp.getAttributeValue(null, "value")
                                publishProgress(25,50,75)

                                min = xpp.getAttributeValue(null, "min")
                                publishProgress(25,50,75)

                                max = xpp.getAttributeValue(null, "max")
                                publishProgress(25,50,75)
                            }

                            "weather" -> {
                                icon = xpp.getAttributeValue(null, "icon")
                                publishProgress(25,50,75)
                            }
                        }
                    }
                    eventType = xpp.next() //move to the next xml event and store it in a variable
                }

                var iconURL = URL("http://openweathermap.org/img/w/$icon.png")
                val iconConnect : HttpURLConnection = iconURL.openConnection() as HttpURLConnection
                iconConnect.connect()
                var responseCode: Int = iconConnect.responseCode

                if(responseCode == 200) {
                    picture = BitmapFactory.decodeStream(iconConnect.inputStream)
                    publishProgress(100)

                    val outputStream: FileOutputStream = openFileOutput("$picture.png", Context.MODE_PRIVATE)
                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
                    outputStream.flush()
                    outputStream.close()
                }

                var fname: String = "$picture.png"
                var file : File = baseContext.getFileStreamPath(fname)

                Log.i("MainActivity", "looking for: $fname")

                if(file.exists()) {
                    lateinit var fis: FileInputStream
                    try {
                        fis = openFileInput("$fname")
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                    var bm: Bitmap = BitmapFactory.decodeStream(fis)
                    Log.i("MainActivity", "You found the image locally")
                } else {
                    Log.i("MainActivity", "You need to download the image")
                }

                val reader = BufferedReader(InputStreamReader(uvResponse, "UTF-8"), 8)
                val sb = StringBuilder()

                lateinit var line: String
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line).append("\n")
                }
                val result = sb.toString() //result is the whole string

                val jObject = JSONObject(result)
                val value = jObject.getDouble("value").toFloat()
                uv = value.toString()

            } catch (e: Exception) {
                e.run { printStackTrace() }
            }
            return "Done"
        }
        override fun onProgressUpdate(vararg value: Int?) {
            progressBar.visibility = View.VISIBLE
            value[0]?.let { progressBar.progress = it }
        }

        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: String?) {
            progressBar.visibility = View.INVISIBLE
            weatherImage.setImageBitmap(picture)
            currentTemperature.text = "Current temperature: $currentTemp"
            minTemp.text = "Min temperature: $min"
            maxTemp.text = "Max temperature: $max"
            //uvRating.text = "UV: $uv"
        }
    }
}