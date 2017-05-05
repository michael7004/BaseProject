package com.example.indianic.baseproject.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;
import com.example.indianic.baseproject.adapter.FragmentOffLineAdapter;

import java.util.ArrayList;

/**
 * PdfFragment class created on 01/05/17.
 */

public class OffLineVideosFragment extends BaseFragment {
    private RecyclerView recyclerViewOffLine;
    private ArrayList<String> arrayList;
    private FragmentOffLineAdapter fragmentOffLineAdapter;
    private GridLayoutManager lLayout;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_off_line_videos, container, false);
        return view;


    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("Off Line ");
        recyclerViewOffLine = (RecyclerView) view.findViewById(R.id.fragment_off_line_rv_main);
        arrayList = new ArrayList<>();
        arrayList.add("Tutorial");
        arrayList.add("Advace concept of design");
        arrayList.add("Example");
        arrayList.add("Tutorial");
        arrayList.add("Mosaic guide book");
        arrayList.add("Mosaic book");
        arrayList.add("Latest courde");
        arrayList.add("Latest design technique");
        arrayList.add("Trending");
        arrayList.add("Fashion design");
        fragmentOffLineAdapter = new FragmentOffLineAdapter(getActivity(), arrayList);
        recyclerViewOffLine.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewOffLine.setAdapter(fragmentOffLineAdapter);


    }

    @Override
    public void trackScreen() {

    }
}
