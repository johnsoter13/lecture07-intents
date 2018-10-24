package edu.uw.intentdemo

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * Created by joelross on 10/17/17.
 */

class PowerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.v("TAG", "received! ${intent.toString()}")

        val message: String? = when(intent.action) {
            Intent.ACTION_POWER_CONNECTED ->  "Now charging..."
            Intent.ACTION_POWER_DISCONNECTED -> "Power disconnected..."
            Intent.ACTION_BATTERY_LOW -> "Battery is low!" //blocked by system notification?
            Intent.ACTION_BATTERY_OKAY -> "Battery is okay now!"
            MainActivity.ACTION_SMS_STATUS -> {
                if (resultCode == Activity.RESULT_OK) {
                    "Message sent!"
                } else {
                    "Error sending message"
                }
            }
            else -> null
        }

        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        val TAG = "MY_RECEIVER"
    }
}
