package com.example.indianic.baseproject.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;
import com.example.indianic.baseproject.adapter.FragmentPdfAdapter;

import java.util.ArrayList;

/**
 * MyPdfFragment class created on 01/05/17.
 */

public class MyPdfFragment extends BaseFragment {

    private RecyclerView recyclerViewPdf;
    private ArrayList<String> arrayList;
    private FragmentPdfAdapter fragmentPdfAdapter;
    private GridLayoutManager lLayout;
    private FragmentManager manager;


    private View view;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pdf, container, false);
        return view;


    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("My Pdfs");
//        ((MainActivity) getActivity()).setTitleColor(ContextCompat.getColor(context, R.color.colorWhite));
        recyclerViewPdf = (RecyclerView) view.findViewById(R.id.fragment_pdf_rv_main);
        arrayList = new ArrayList<>();
        arrayList.add("Tutorial");
        arrayList.add("Unlock");
        arrayList.add("Example");
        arrayList.add("Tutorial");
        arrayList.add("Unlock");
        arrayList.add("Mosaic book");
        arrayList.add("Latest courde");
        arrayList.add("Latest");
        arrayList.add("Unlock");
        arrayList.add("Fashion design");
        manager=getFragmentManager();
        fragmentPdfAdapter = new FragmentPdfAdapter(getActivity(), arrayList,manager);
        recyclerViewPdf.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewPdf.setAdapter(fragmentPdfAdapter);

    }

    @Override
    public void trackScreen() {

    }


}
