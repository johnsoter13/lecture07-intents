package edu.uw.intentdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //action bar "back"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // extra values sent from main activity
        intent.extras.apply {
            val message = this.getString(MainActivity.EXTRA_MESSAGE)
            val subtitle = findViewById<View>(R.id.txt_second) as TextView
            subtitle.text = "Received: " + message!!
        }

    }
}
