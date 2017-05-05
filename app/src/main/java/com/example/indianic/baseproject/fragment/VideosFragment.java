package com.example.indianic.baseproject.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;
import com.example.indianic.baseproject.adapter.FragmentVideosAdapter;

import java.util.ArrayList;

/**
 * PdfFragment class created on 01/05/17.
 */

public class VideosFragment extends BaseFragment {
    private RecyclerView recyclerVideos;
    private ArrayList<String> arrayList;
    private FragmentVideosAdapter fragmentVideoAdapter;
    private GridLayoutManager lLayout;


    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_videos, container, false);
        return view;


    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("Video");
        recyclerVideos = (RecyclerView) view.findViewById(R.id.fragment_videos_rv_main);
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
        fragmentVideoAdapter = new FragmentVideosAdapter(getActivity(), arrayList);
        recyclerVideos.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerVideos.setAdapter(fragmentVideoAdapter);


    }

    @Override
    public void trackScreen() {

    }
}
