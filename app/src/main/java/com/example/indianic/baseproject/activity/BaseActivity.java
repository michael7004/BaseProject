package com.example.indianic.baseproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by umesh nepali on 21/04/17.
 * 1.What is scenario where we need to make base activity.
 * case 1:Usually one screen is represented by an activity.
 * Suppose, you have 3 custom activity classes (i.e, you writes them) in your android project.
 * And you extended these 3 custom activity from android.app.Activity class.
 * Suggestion:What I am suggesting is to create an activity from android.app.Activity called baseActivity and make the 3 custom activity to extend from baseActivity.
 * case 2:Android common menu options for multiple activities.
 * case 3:Adding a Toolbar and Navigation Drawer Across all Activities of an Android App
 * <p>
 * 2.Why do we need to make an baseActivity class and then make your other activities as a subclass of it?
 * Uses 1:You can avoid code duplication of you application wise common ui tasks. Suppose you want to show progressDialog in different activities.
 * Just write code to show a progressDialog in your baseActivity and call that method from other activity.
 * Uses 2:Your application menu should be consistent across different application. For example, you have a settings screen. Option menu is containing the navigation to settings screen.
 * You should display the settings throughout the application, means regardless of activity.
 * User want to access the settings screen from anywhere in application. This feature can be easily achieved, but you have to override onCreateOptionsMenu in each of your activity or,
 * you can override it once in your baseActivity(Not understand clearly).
 * <p>
 * What is Abstract from official oracal site.
 */


/**
 * Notice that,BaseActivity is an abstract class. The reason is, we are not going to instantiate it.
 * That’s why you don’t need to add the MyAppBaseActivity class in AndroidManifest.xml file
 */
public abstract class BaseActivity extends AppCompatActivity {


    public abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


}
