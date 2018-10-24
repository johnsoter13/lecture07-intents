package edu.uw.intentdemo

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.telephony.SmsManager
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private val TAG = "**Main**"

    private val REQUEST_IMAGE_CAPTURE = 1

    // Why do we use companion objects?
    companion object {
        const val EXTRA_MESSAGE = "edu.uw.intentdemo.message"
        const val ACTION_SMS_STATUS = "edu.uw.intentdemo.ACTION_SMS_STATUS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val launchButton = findViewById<View>(R.id.btn_launch)
        launchButton.setOnClickListener {
            Log.v(TAG, "Launch button pressed")

            // Explicit intent: we are sending this intent directly
            //                   context,                         target
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra(EXTRA_MESSAGE, "Greetings from sunny MainActivity!")
            startActivity(intent)
        }

        //handle broadcasts
        val batteryFilter = IntentFilter()
        batteryFilter.addAction(Intent.ACTION_BATTERY_LOW)
        batteryFilter.addAction(Intent.ACTION_BATTERY_OKAY)
        batteryFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        batteryFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        this.registerReceiver(PowerReceiver(), batteryFilter)

    }

    fun callNumber(v: View) {
        Log.v(TAG, "Call button pressed")

        // implicit intent: sending an intent out to anyone who can handle the intent
        val intent = Intent(Intent.ACTION_DIAL) // this intent sends an action
        intent.data = Uri.parse("tel:206-685-1622") // different than extra, this actually sends the data for the intent
        if (intent.resolveActivity(packageManager) != null) { // tests to see if someone can actually run this intent
            startActivity(intent)
        }
    }

    fun takePicture(v: View?) {
        Log.v(TAG, "Camera button pressed")

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // take a picture
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE) // do this intent AND send a reply back
                                                        // This is the id that gets sent back so we know which reply is which
        }

    }

    fun sendMessage(v: View) {
        Log.v(TAG, "Message button pressed")

        val intent = Intent(ACTION_SMS_STATUS)
        val pendingIntent = PendingIntent.getBroadcast(this@MainActivity, 0, intent, 0)

        //Requires targetSdk < 23 as written
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage("5554", null, "This is a test message!", pendingIntent, null)
        //                         target,       message
    }

    // called when we get a response back
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // if response is camera code and result is ok
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // do something with the data
            val extras = data.extras?.apply {
                val imageBitmap = get("data") as Bitmap
                val imageView = findViewById<ImageView>(R.id.img_thumbnail)
                imageView.setImageBitmap(imageBitmap)
            }
        }
        // super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu) //inflate into this menu

        val item = menu.findItem(R.id.menu_item_share)
        val shareProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider

        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:206-685-1622")

        shareProvider.setShareIntent(intent)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.hello_menu_item -> {
                Log.v(TAG, "Hello!")
                true //handled
            }
            R.id.picture_menu_item -> {
                takePicture(null)
                true //handled
            }
            R.id.toast_menu_item -> {
                Toast.makeText(this, "Hi!", Toast.LENGTH_SHORT).show()
                true //handled
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        val inflater = menuInflater //get!
        inflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        Log.v(TAG, "You clicked a context menu item!")
        return super.onContextItemSelected(item)
    }
}
