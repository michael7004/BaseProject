package com.example.indianic.baseproject.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;
import com.example.indianic.baseproject.adapter.FragmentOffLineAdapter;
import com.example.indianic.baseproject.utills.Utills;

import java.util.ArrayList;

/**
 * MyPdfFragment class created on 01/05/17.
 */

public class MyDownloadsFragment extends BaseFragment {
    private long mLastClickTime = 0;
    public static boolean isPdfs = false;
    public static boolean isVideos = true;
    private RecyclerView recyclerViewOffLine;
    private ArrayList<String> arrayList;
    private FragmentOffLineAdapter fragmentOffLineAdapter;
    private FragmentManager manager;
    private GridLayoutManager lLayout;
    private View view;
    private Context context;

    private TextView tvVideos;
    private TextView tvPdfs;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_downloads, container, false);
        return view;


    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("My Download ");
//        ((MainActivity) getActivity()).setTitleColor(ContextCompat.getColor(context, R.color.colorWhite));
        recyclerViewOffLine = (RecyclerView) view.findViewById(R.id.fragment_off_line_rv_main);
        tvVideos = (TextView) view.findViewById(R.id.fragment_my_downloads_tv_videos);
        tvPdfs = (TextView) view.findViewById(R.id.fragment_my_downloads_tv_pdfs);
        getVideos();

//        arrayList = new ArrayList<>();
//        arrayList.add("Tutorial");
//        arrayList.add("Advace concept of design");
//        arrayList.add("Example");
//        arrayList.add("Tutorial");
//        arrayList.add("Mosaic guide book");
//        arrayList.add("Mosaic book");
//        arrayList.add("Latest courde");
//        arrayList.add("Latest design technique");
//        arrayList.add("Trending");
//        arrayList.add("Fashion design");
//        fragmentOffLineAdapter = new FragmentOffLineAdapter(getActivity(), arrayList);
//        recyclerViewOffLine.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//        recyclerViewOffLine.setAdapter(fragmentOffLineAdapter);


    }

    @Override
    public void trackScreen() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        Utills.hideSoftKeyboard(getActivity());

        switch (v.getId()) {
            case R.id.fragment_my_downloads_tv_videos:
                tvVideos.setVisibility(View.VISIBLE);
                tvVideos.setBackgroundResource(R.color.color_all_category_select_bg);
                tvPdfs.setBackgroundResource(android.R.color.transparent);
                tvVideos.setTextColor(getResources().getColor(R.color.colorWhite));
                tvPdfs.setTextColor(getResources().getColor(R.color.color_all_category_none_select_text));
                isPdfs = false;
                isVideos=true;
                getVideos();
                break;

            case R.id.fragment_my_downloads_tv_pdfs:

                Utills.hideSoftKeyboard(getActivity());
                tvVideos.setBackgroundResource(android.R.color.transparent);
                tvPdfs.setBackgroundResource(R.color.color_all_category_select_bg);
                tvVideos.setTextColor(getResources().getColor(R.color.color_all_category_none_select_text));
                tvPdfs.setTextColor(getResources().getColor(R.color.colorWhite));
                isPdfs = true;
                isVideos=false;
                getPdfs();
                break;
        }
    }

    private void getPdfs() {
        arrayList = new ArrayList<>();
        arrayList.add("pdf 1");
        arrayList.add("pdf 2");
        arrayList.add("pdf 3");
        arrayList.add("pdf 4");
        arrayList.add("pdf 5");
        arrayList.add("pdf 6");
        arrayList.add("pdf 7");
        arrayList.add("pdf 8");
        arrayList.add("pdf 9");
        arrayList.add("pdf 10");
        fragmentOffLineAdapter = new FragmentOffLineAdapter(getActivity(), arrayList,manager);
        recyclerViewOffLine.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewOffLine.setAdapter(fragmentOffLineAdapter);

    }

    private void getVideos() {
        arrayList = new ArrayList<>();
        arrayList.add("Tutorial");
        arrayList.add("Advace");
        arrayList.add("Example");
        arrayList.add("Tutorial");
        arrayList.add("Mosaic guide book");
        arrayList.add("Mosaic book");
        arrayList.add("Latest courde");
        arrayList.add("Latest design technique");
        arrayList.add("Trending");
        arrayList.add("Fashion design");
        manager=getFragmentManager();
        fragmentOffLineAdapter = new FragmentOffLineAdapter(getActivity(), arrayList,manager);
        recyclerViewOffLine.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewOffLine.setAdapter(fragmentOffLineAdapter);
        tvVideos.setOnClickListener(this);
        tvPdfs.setOnClickListener(this);
        setFirstTab();
    }


    private void setFirstTab() {
        Utills.hideSoftKeyboard(getActivity());
        tvVideos.setVisibility(View.VISIBLE);
        tvVideos.setBackgroundResource(R.color.color_all_category_select_bg);
        tvPdfs.setBackgroundResource(android.R.color.transparent);
        tvVideos.setTextColor(getResources().getColor(R.color.colorWhite));
        tvPdfs.setTextColor(getResources().getColor(R.color.color_all_category_none_select_text));
        isPdfs = false;

    }
}
