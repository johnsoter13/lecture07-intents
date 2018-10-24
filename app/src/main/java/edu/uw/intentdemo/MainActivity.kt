package edu.uw.intentdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View


class MainActivity : AppCompatActivity() {

    private val TAG = "**Main**"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val launchButton = findViewById<View>(R.id.btn_launch)
        launchButton.setOnClickListener {
            Log.v(TAG, "Launch button pressed")


        }

    }

    fun callNumber(v: View) {
        Log.v(TAG, "Call button pressed")

    }

    fun takePicture(v: View?) {
        Log.v(TAG, "Camera button pressed")

    }

    fun sendMessage(v: View) {
        Log.v(TAG, "Message button pressed")

    }

}
