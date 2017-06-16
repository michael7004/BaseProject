package com.example.indianic.baseproject.fragment;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;
import com.example.indianic.baseproject.adapter.FragmentOffLinePdfAdapter;
import com.example.indianic.baseproject.adapter.FragmentOffLineVideoAdapter;
import com.example.indianic.baseproject.model.OffLinePdfModel;
import com.example.indianic.baseproject.model.OffLineVideoModel;
import com.example.indianic.baseproject.utills.Utills;

import java.io.File;
import java.util.ArrayList;

/**
 * MyPdfFragment class created on 01/05/17.
 */

public class MyDownloadsFragment extends BaseFragment {
    private long mLastClickTime = 0;
    public static boolean isPdfs = false;
    public static boolean isVideos = true;
    private RecyclerView recyclerViewOffLine;

    private FragmentOffLineVideoAdapter fragmentOffLineVideoAdapter;
    private FragmentOffLinePdfAdapter fragmentOffLinePdfAdapter;
    private FragmentManager manager;
    private GridLayoutManager lLayout;
    private View view;
    private Context context;

    private TextView tvVideos;
    private TextView tvPdfs;
    private TextView tvNoData;

    private ArrayList<OffLineVideoModel> offLineVideArrayList = new ArrayList<>();
    private ArrayList<OffLinePdfModel> offLinepdfArrayList = new ArrayList<>();
    private AsyncVideoList asyncVideoList;
    private AsyncPdfList asyncPdfList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_downloads, container, false);
        return view;


    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("My Download ");
//        ((MainActivity) getActivity()).setTitleColor(ContextCompat.getColor(context, R.color.colorWhite));
        manager = getFragmentManager();
        recyclerViewOffLine = (RecyclerView) view.findViewById(R.id.fragment_off_line_rv_main);
        tvVideos = (TextView) view.findViewById(R.id.fragment_my_downloads_tv_videos);
        tvPdfs = (TextView) view.findViewById(R.id.fragment_my_downloads_tv_pdfs);
        tvNoData = (TextView) view.findViewById(R.id.fragment_my_downloads_tv_no_data);
        tvVideos.setOnClickListener(this);
        tvPdfs.setOnClickListener(this);
        getVideosList();


    }

    private ArrayList<OffLinePdfModel> search_Pdf() {


        try {
            File mfile = new File("/storage/emulated/0/Android/data/com.example.indianic.baseproject/files/Download/");
            File[] list = mfile.listFiles();
            if (list.length == 0)
                Toast.makeText(context, " Folder is empty", Toast.LENGTH_SHORT).show();
            else {

                if (offLinepdfArrayList.size() > 0) {
                    offLinepdfArrayList.clear();
                }
                String pdfPattern = ".pdff";
                for (int i = 0; i < list.length; i++)

                    if (list[i].getName().endsWith(pdfPattern)) {
                        Log.d("PDF", list[i].getAbsolutePath());
                        OffLinePdfModel offLinePdfModel = new OffLinePdfModel();
                        offLinePdfModel.setId(list[i].getPath());
                        offLinePdfModel.setTitle(list[i].getName());
                        offLinePdfModel.setPath(list[i].getAbsolutePath());
                        offLinepdfArrayList.add(offLinePdfModel);
                    }

            }
        } catch (Exception e) {
//            Toast.makeText(context, "Folder not created yet", Toast.LENGTH_SHORT).show();
        }

        return offLinepdfArrayList;

    }

    private ArrayList<OffLineVideoModel> search_Video() {
        try {

            File mfile = new File("/storage/emulated/0/Android/data/com.example.indianic.baseproject/files/Download/");
            File[] list = mfile.listFiles();
            if (list.length == 0)
                Log.d("Empty", "Folder is empty");
            else {
                if (offLineVideArrayList.size() > 0) {
                    offLineVideArrayList.clear();
                }
                final String VideoPattern = ".mp44";
                for (int i = 0; i < list.length; i++)
                    if (list[i].getName().endsWith(VideoPattern)) {

                        Log.d("Video", list[i].getAbsolutePath());
                        OffLineVideoModel offLineVideoModel = new OffLineVideoModel();
                        offLineVideoModel.setId(list[i].getPath());
                        offLineVideoModel.setTitle(list[i].getName());
                        offLineVideoModel.setPath(list[i].getAbsolutePath());
                        offLineVideArrayList.add(offLineVideoModel);

                    }
            }
        } catch (Exception e) {
//            Toast.makeText(context, "Folder not created yet", Toast.LENGTH_SHORT).show();
        }

        return offLineVideArrayList;

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
                tvVideos.setBackgroundResource(R.color.colorAccent);
                tvPdfs.setBackgroundResource(android.R.color.transparent);
                tvVideos.setTextColor(getResources().getColor(R.color.colorWhite));
                tvPdfs.setTextColor(getResources().getColor(R.color.color_all_category_none_select_text));
                isPdfs = false;
                isVideos = true;
                getVideosList();
                break;

            case R.id.fragment_my_downloads_tv_pdfs:

                Utills.hideSoftKeyboard(getActivity());
                tvVideos.setBackgroundResource(android.R.color.transparent);
                tvPdfs.setBackgroundResource(R.color.colorAccent);
                tvVideos.setTextColor(getResources().getColor(R.color.color_all_category_none_select_text));
                tvPdfs.setTextColor(getResources().getColor(R.color.colorWhite));
                isPdfs = true;
                isVideos = false;
                getPdfList();

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        if (asyncVideoList != null && asyncVideoList.getStatus() == AsyncTask.Status.RUNNING) {
            asyncVideoList.cancel(true);
        }
        if (asyncPdfList != null && asyncPdfList.getStatus() == AsyncTask.Status.RUNNING) {
            asyncPdfList.cancel(true);
        }

    }


    private void setFirstTab() {
        Utills.hideSoftKeyboard(getActivity());
        tvVideos.setVisibility(View.VISIBLE);
        tvVideos.setBackgroundResource(R.color.colorAccent);
        tvPdfs.setBackgroundResource(android.R.color.transparent);
        tvVideos.setTextColor(getResources().getColor(R.color.colorWhite));
        tvPdfs.setTextColor(getResources().getColor(R.color.color_all_category_none_select_text));
        isPdfs = false;

    }


    private void getVideosList() {

            if (asyncVideoList != null && asyncVideoList.getStatus() == AsyncTask.Status.PENDING) {
                asyncVideoList.execute();
            } else if (asyncVideoList == null || asyncVideoList.getStatus() == AsyncTask.Status.FINISHED) {
                asyncVideoList = new AsyncVideoList();
                asyncVideoList.execute();
            }

    }

    private void getPdfList() {

            if (asyncPdfList != null && asyncPdfList.getStatus() == AsyncTask.Status.PENDING) {
                asyncPdfList.execute();
            } else if (asyncPdfList == null || asyncPdfList.getStatus() == AsyncTask.Status.FINISHED) {
                asyncPdfList = new AsyncPdfList();
                asyncPdfList.execute();
            }

    }

    private class AsyncVideoList extends AsyncTask<Void, Void, ArrayList<OffLineVideoModel>> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utills.showProgressDialogNew(getActivity(), getString(R.string.msg_loading), false);
        }

        @Override
        protected ArrayList<OffLineVideoModel> doInBackground(Void... params) {
            return search_Video();
        }

        @Override
        protected void onPostExecute(ArrayList<OffLineVideoModel> result) {
            super.onPostExecute(result);
            if (isCancelled()) {
                return;
            }
            if (result.size() <= 0) {
                tvNoData.setVisibility(View.VISIBLE);
                Utills.dismissProgressDialogNew(progressDialog);
            } else {
                tvNoData.setVisibility(View.INVISIBLE);
                Utills.dismissProgressDialogNew(progressDialog);
            }
            Log.d("SIZE", String.valueOf(result.size()));
            fragmentOffLineVideoAdapter = new FragmentOffLineVideoAdapter(getActivity(), result, manager, new MyDownloadsFragment());
            recyclerViewOffLine.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            recyclerViewOffLine.setAdapter(fragmentOffLineVideoAdapter);
            setFirstTab();
        }
    }

    private class AsyncPdfList extends AsyncTask<Void, Void, ArrayList<OffLinePdfModel>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utills.showProgressDialogNew(getActivity(), getActivity().getString(R.string.msg_loading), false);
        }

        @Override
        protected ArrayList<OffLinePdfModel> doInBackground(Void... params) {

            return search_Pdf();
        }

        @Override
        protected void onPostExecute(ArrayList<OffLinePdfModel> result) {
            super.onPostExecute(result);
            if (isCancelled()) {
                return;
            }

            if (result.size() <= 0) {
                tvNoData.setVisibility(View.VISIBLE);
                Utills.dismissProgressDialogNew(progressDialog);
            } else {
                tvNoData.setVisibility(View.INVISIBLE);
                Utills.dismissProgressDialogNew(progressDialog);
            }

            Log.d("SIZE", String.valueOf(result.size()));
            fragmentOffLinePdfAdapter = new FragmentOffLinePdfAdapter(getActivity(), result, manager);
            recyclerViewOffLine.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            recyclerViewOffLine.setAdapter(fragmentOffLinePdfAdapter);

        }
    }
}
