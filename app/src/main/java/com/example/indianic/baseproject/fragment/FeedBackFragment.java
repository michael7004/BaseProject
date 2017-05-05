package com.example.indianic.baseproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;

/**
 * PdfFragment class created on 01/05/17.
 */

public class FeedBackFragment extends BaseFragment {



    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        return view;


    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("FeedBack");


    }

    @Override
    public void trackScreen() {

    }
}
