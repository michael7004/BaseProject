package com.example.indianic.baseproject.utills;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.indianic.baseproject.BaseProjectApplication;
import com.example.indianic.baseproject.R;


/**
 * Created on 24/04/2017
 * <p>
 * Purpose of this class is to save data in preferance and retrive values from preferance througout the lifewcycle of application
 * This class is hold methods for storing and retriving values from preference.
 */
public class UtillPreference {

    private static UtillPreference mPreference;
    public SharedPreferences mSharedPreferences;


    private UtillPreference() {
        mSharedPreferences = BaseProjectApplication.mAppInstance.getSharedPreferences(BaseProjectApplication.mAppInstance.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /**
     * @return the {@link SharedPreferences} object that will be used to save values in the application preference
     */
    public static UtillPreference getInstance() {
        if (mPreference == null) {
            mPreference = new UtillPreference();
        }
        return mPreference ;
    }

    /**
     * Stores the {@link String} value in the preference
     *
     * @param key   {@link String} parameter for the key for the values in preference
     * @param value {@link String} parameter for the value to be stored in preference
     */
    public void savePreferenceData(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }



    /**
     * Stores the {@link Boolean} value in the preference
     *
     * @param key   {@link String} parameter for the key for the values in preference
     * @param value {@link Boolean} parameter for the value to be stored in preference
     */
    public void savePreferenceData(String key, Boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    /**
     * Stores the {@link Integer} value in the preference
     *
     * @param key   {@link String} parameter for the key for the values in preference
     * @param value {@link Integer} parameter for the value to be stored in preference
     */
    public void savePreferenceData(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void clearPreferenceData() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
//        editor.putString(key, value);
        editor.apply();
    }


}
