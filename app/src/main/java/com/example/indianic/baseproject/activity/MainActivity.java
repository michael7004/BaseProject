package com.example.indianic.baseproject.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.fragment.ContactUsFragment;
import com.example.indianic.baseproject.fragment.FeedBackFragment;
import com.example.indianic.baseproject.fragment.HomeFragment;
import com.example.indianic.baseproject.fragment.MyDownloadsFragment;
import com.example.indianic.baseproject.fragment.MyPdfFragment;
import com.example.indianic.baseproject.fragment.MyVideosFragment;
import com.example.indianic.baseproject.utills.Constants;
import com.example.indianic.baseproject.utills.Preference;
import com.example.indianic.baseproject.utills.Utills;

;

/**
 * Main Acitivity created on 28 apr 2017
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    private View view;
    private TextView tvFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Utills.replaceFragment(MainActivity.this, new HomeFragment(), R.id.content_main_container);


    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);

        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_close, R.string.navigation_drawer_open) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                Utills.hideSoftKeyboard(MainActivity.this);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawer.addDrawerListener(actionBarDrawerToggle);


        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        tvFullName = (TextView) headerLayout.findViewById(R.id.nav_header_main_tv_full_name);
        final String strFullName = Preference.getInstance().mSharedPreferences.getString(Constants.PRE_FULL_NAME, "");
        if (!strFullName.equalsIgnoreCase("")) {
            tvFullName.setText(Preference.getInstance().mSharedPreferences.getString(Constants.PRE_FULL_NAME, ""));
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            Utills.hideSoftKeyboard(this);
            getFragmentManager().popBackStack();
        } else {
            buildAlertMessageExit();
        }


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }


    private void buildAlertMessageExit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.TAG_EXIT_WARN_MSG)).setCancelable(false).setPositiveButton(getString(R.string.TAG_YES), new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                callToFinish();
            }
        }).setNegativeButton(getString(R.string.TAG_NO), new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void callToFinish() {
        super.finish();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Utills.replaceFragment(MainActivity.this, new HomeFragment(), R.id.content_main_container);
        } else if (id == R.id.nav_pdf) {
            Utills.replaceFragment(MainActivity.this, new MyPdfFragment(), R.id.content_main_container);
        } else if (id == R.id.nav_videos) {
            Utills.replaceFragment(MainActivity.this, new MyVideosFragment(), R.id.content_main_container);

        } else if (id == R.id.nav_my_downloads) {
            Utills.replaceFragment(MainActivity.this, new MyDownloadsFragment(), R.id.content_main_container);

        } else if (id == R.id.nav_contact_us) {

            Utills.replaceFragment(MainActivity.this, new ContactUsFragment(), R.id.content_main_container);
        } else {
            Utills.replaceFragment(MainActivity.this, new FeedBackFragment(), R.id.content_main_container);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
