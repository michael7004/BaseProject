package com.example.indianic.baseproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.indianic.baseproject.R;

/**
 * Created by umesh nepali 21 apr 2017.
 * Description:
 * Android splash screen are normally used to show user some kind of progress before the app loads completely.
 * Some people uses splash screen just to show case their app / company logo for a couple of second.
 * 1 Splash screen use case scenarios.
 * Case 1:Show spash screen when making network call/preparing app.
 * E.g: Downloading data and storing it sqlite database.
 * Downloading required images.
 * Fetching and parsing json/xml.
 * Sending device information and registering device to server side.
 * 2.Two way to implement spalash screen.
 * 1 Android Splash Screen Using Timer.
 * 2 Android Splash Screen When Making Network (http) Calls.
 */

public class SplashActivity extends BaseActivity {

    private Handler handler;
    private final int TIME_INTERVAL = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        setHandler();

    }

    @Override
    public void initView() {

    }

    /**
     * Handler to start the Runnable Interface after the delay
     * of 3000 seconds
     */
    private void setHandler() {
        handler = new Handler();
        handler.postDelayed(runnable, TIME_INTERVAL);
    }

    /**
     * Starts a Runnable Interface to switch the Splash Screen
     * to Login Screen.
     */
    final Runnable runnable = new Runnable() {

        @Override
        public void run() {


            final Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
            finish();


        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // remove the runnable set with the Handler
        // if the Activity gets destroyed in any case
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }


}
