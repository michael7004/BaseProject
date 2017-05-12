package com.example.indianic.baseproject.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.adapter.FragmentHomeSubAdapter;
import com.example.indianic.baseproject.adapter.ViewPagerAdapter;
import com.example.indianic.baseproject.model.AndroidVersion;

import java.util.ArrayList;

/**
 * HomeFragment class created on 11/05/17.
 */

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    protected View view;

    private ViewPager intro_images;
    private RecyclerView recyclerView;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;


    private int[] mImageResources = {
            R.drawable.cover4,
            R.drawable.cover4,
            R.drawable.cover4,
            R.drawable.cover4,
            R.drawable.cover4
    };

    public Integer[] mImageSubResources = {
            R.drawable.cover4,
            R.drawable.cover4,
            R.drawable.cover4,
            R.drawable.cover4,
            R.drawable.cover4
    };


    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    private final String android_image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void initView(View view) {
        intro_images = (ViewPager) view.findViewById(R.id.pager_introduction);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_rv_sub_slider);
        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);
        mAdapter = new ViewPagerAdapter(getActivity(), mImageResources);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);


        ArrayList<AndroidVersion> androidVersions = prepareData();
        FragmentHomeSubAdapter adapter = new FragmentHomeSubAdapter(getActivity(), androidVersions);



        intro_images.setAdapter(mAdapter);
        recyclerView.setAdapter(adapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();
    }

    private ArrayList<AndroidVersion> prepareData() {

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for (int i = 0; i < android_version_names.length; i++) {
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(android_version_names[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);


    }

    @Override
    public void trackScreen() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void setUiPageViewController() {
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


}
