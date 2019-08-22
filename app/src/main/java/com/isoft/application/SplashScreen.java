package com.isoft.application;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoft.application.R;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        // run();
        checkAndRequestPermissions();


        //animator for the image view
        ImageView view = (ImageView) findViewById(R.id.splash_image);
        ObjectAnimator imageAnimator = ObjectAnimator.ofFloat(view, "translationY", 100f);
        imageAnimator.setDuration(1500);
        imageAnimator.start();

        //animator for the text view
        TextView text = (TextView) findViewById(R.id.splash_text);
        ObjectAnimator textAnimator = ObjectAnimator.ofFloat(text, "translationX", 100f);
        textAnimator.setDuration(2500);
        textAnimator.start();

        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(3 * 1000);

                    // After 5 seconds redirect to another intent
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);

                    //Remove activity
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // start thread
        background.start();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean checkAndRequestPermissions() {
        int c1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int c2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int c3 = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (c1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (c2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (c3 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(
                    new String[listPermissionsNeeded.size()]
            ), 1);
            return false;
        }
        return true;
    }


    @Override
    public void run() {
        //check if storage read permission is granted, if not grant it
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            // if not granted start a new thread and grant the
            // request on that thread to avoid ANR (Android Not Responding) error ;
            //  backgroundRead.start();
        }
    }
}
