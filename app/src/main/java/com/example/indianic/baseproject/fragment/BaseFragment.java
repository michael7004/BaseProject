package com.example.indianic.baseproject.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * BaseFragment class created on 28/04/17.
 */

public abstract class BaseFragment extends android.app.Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public abstract void initView(View view);

    public abstract void trackScreen();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onClick(View v) {
//        Utils.hideSoftKeyboard(getActivity());
    }
}
