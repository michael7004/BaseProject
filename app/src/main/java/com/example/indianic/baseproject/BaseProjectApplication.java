package com.example.indianic.baseproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created class BaseProjectApplication on 21/04/17.
 * Application class ,It loads first.
 */

public class BaseProjectApplication extends Application {

    public static BaseProjectApplication mAppInstance;
    public SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = getSharedPreferences(this.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);


    }


}
