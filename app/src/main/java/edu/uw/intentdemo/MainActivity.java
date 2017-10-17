package edu.uw.intentdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "**Main**";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View launchButton = findViewById(R.id.btn_launch);
        launchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Launch button pressed");


            }
        });

    }

    public void callNumber(View v) {
        Log.v(TAG, "Call button pressed");


    }

    public void takePicture(View v) {
        Log.v(TAG, "Camera button pressed");


    }

    public void sendMessage(View v) {
        Log.v(TAG, "Message button pressed");


    }

}
