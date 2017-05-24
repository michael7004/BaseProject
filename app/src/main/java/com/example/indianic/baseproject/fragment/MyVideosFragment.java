package com.example.indianic.baseproject.fragment;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;
import com.example.indianic.baseproject.adapter.FragmentVidPdfAdapter;
import com.example.indianic.baseproject.adapter.FragmentVideosAdapter;
import com.example.indianic.baseproject.model.VidPdfListModel;
import com.example.indianic.baseproject.model.VideosListModel;
import com.example.indianic.baseproject.utills.Constants;
import com.example.indianic.baseproject.utills.Preference;
import com.example.indianic.baseproject.utills.Utills;
import com.example.indianic.baseproject.webservice.WSVidPdfList;
import com.example.indianic.baseproject.webservice.WSVideosList;

import java.util.ArrayList;

/**
 * MyPdfFragment class created on 01/05/17.
 */

public class MyVideosFragment extends BaseFragment implements View.OnClickListener {

    //    http://mosaicdesigns.in/webservice/select_vcPdfs
//    http://mosaicdesigns.in/assets/videodownloads/32.pdf
    private long mLastClickTime = 0;
    public static boolean isPdfs = false;
    public static boolean isVideos = true;
    private RecyclerView recyclerVideos;
    private ArrayList<String> arrayList;
    private FragmentVideosAdapter fragmentVideoAdapter;
    private FragmentVidPdfAdapter fragmentVidPdfAdapter;
    private GridLayoutManager lLayout;
    private FragmentManager manager;
    private View view;
    private RelativeLayout relativeLayout;
    private Context context;
    private TextView tvVideos;
    private TextView tvPdfs;
    private TextView tvNoData;
    private AsynVideoList asynVideoList;
    private AsynVidPdfList asynVidPdfList;

    private ArrayList<VideosListModel> videosListModels;
    private ArrayList<VidPdfListModel> vidPdfListModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_videos, container, false);
        return view;


    }


    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("My Videos");
//        ((MainActivity) getActivity()).setTitleColor(.getColor(context, R.color.colorWhite));
        manager = getFragmentManager();
        videosListModels = new ArrayList<>();
        vidPdfListModels = new ArrayList<>();
        recyclerVideos = (RecyclerView) view.findViewById(R.id.fragment_videos_rv_main);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.fragment_videos_rv_container);
        tvVideos = (TextView) view.findViewById(R.id.fragment_videos_tv_videos);
        tvPdfs = (TextView) view.findViewById(R.id.fragment_videos_tv_pdfs);
        tvNoData = (TextView) view.findViewById(R.id.fragment_my_downloads_tv_no_data);

        tvVideos.setOnClickListener(this);
        tvPdfs.setOnClickListener(this);
        getVideosList(Preference.getInstance().mSharedPreferences.getString(Constants.PRE_USER_ID,""));


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
            case R.id.fragment_videos_tv_videos:
                tvVideos.setVisibility(View.VISIBLE);
                tvVideos.setBackgroundResource(R.color.colorAccent);
                tvPdfs.setBackgroundResource(android.R.color.transparent);
                tvVideos.setTextColor(getResources().getColor(R.color.colorWhite));
                tvPdfs.setTextColor(getResources().getColor(R.color.color_all_category_none_select_text));
                isPdfs = false;
                getVideosList(Preference.getInstance().mSharedPreferences.getString(Constants.PRE_USER_ID,""));
                break;

            case R.id.fragment_videos_tv_pdfs:
                Utills.hideSoftKeyboard(getActivity());
                tvVideos.setBackgroundResource(android.R.color.transparent);
                tvPdfs.setBackgroundResource(R.color.colorAccent);
                tvVideos.setTextColor(getResources().getColor(R.color.color_all_category_none_select_text));
                tvPdfs.setTextColor(getResources().getColor(R.color.colorWhite));
                isPdfs = true;
                isVideos = false;
                getVidPdfList(Preference.getInstance().mSharedPreferences.getString(Constants.PRE_USER_ID,""));
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (asynVideoList != null && asynVideoList.getStatus() == AsyncTask.Status.RUNNING) {
            asynVideoList.cancel(true);
        }
        if (asynVidPdfList != null && asynVidPdfList.getStatus() == AsyncTask.Status.RUNNING) {
            asynVidPdfList.cancel(true);
        }
    }

    private void setFirstTab() {
        Utills.hideSoftKeyboard(getActivity());
        tvVideos.setVisibility(View.VISIBLE);
        tvVideos.setBackgroundResource(R.color.colorAccent);
        tvPdfs.setBackgroundResource(android.R.color.transparent);
        tvVideos.setTextColor(getResources().getColor(R.color.colorWhite));
        tvPdfs.setTextColor(getResources().getColor(R.color.color_all_category_none_select_text));
        isVideos = true;
        isPdfs = false;


    }

    /**
     * API call for resend pin
     *
     * @param clientID
     */
    private void getVideosList(String clientID) {
        if (Utills.isNetworkAvailable(getActivity())) {
            if (asynVideoList != null && asynVideoList.getStatus() == AsyncTask.Status.PENDING) {
                asynVideoList.execute(clientID);
            } else if (asynVideoList == null || asynVideoList.getStatus() == AsyncTask.Status.FINISHED) {
                asynVideoList = new AsynVideoList();
                asynVideoList.execute(clientID);
            }
        } else {
            Utills.showSnackbarNonSticky(relativeLayout, getString(R.string.msg_no_internet), true, getActivity());
        }
    }


    /**
     * API call for resend pin
     *
     * @param clientID
     */
    private void getVidPdfList(String clientID) {
        if (Utills.isNetworkAvailable(getActivity())) {
            if (asynVidPdfList != null && asynVidPdfList.getStatus() == AsyncTask.Status.PENDING) {
                asynVidPdfList.execute(clientID);
            } else if (asynVidPdfList == null || asynVidPdfList.getStatus() == AsyncTask.Status.FINISHED) {
                asynVidPdfList = new AsynVidPdfList();
                asynVidPdfList.execute(clientID);
            }
        } else {
            Utills.showSnackbarNonSticky(relativeLayout, getString(R.string.msg_no_internet), true, getActivity());
        }
    }

    /**
     * AsyncTask for VideoList
     */
    private class AsynVideoList extends AsyncTask<String, Void, ArrayList<VideosListModel>> {
        private WSVideosList wsVideosList;
        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsVideosList = new WSVideosList(getActivity());
            progressDialog = Utills.showProgressDialogNew(getActivity(), getString(R.string.msg_loading), false);
        }

        @Override
        protected ArrayList<VideosListModel> doInBackground(String... params) {
            return wsVideosList.executeService(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<VideosListModel> result) {
            super.onPostExecute(result);
            Utills.dismissProgressDialogNew(progressDialog);
            if (isCancelled()) {
                return;
            }

            if (result.size() <= 0) {
                tvNoData.setVisibility(View.VISIBLE);
                Utills.dismissProgressDialogNew(progressDialog);
            } else {
                Utills.dismissProgressDialogNew(progressDialog);
            }
            if (wsVideosList != null) {

                fragmentVideoAdapter = new FragmentVideosAdapter(getActivity(), result, manager);
                recyclerVideos.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                recyclerVideos.setAdapter(fragmentVideoAdapter);
                setFirstTab();

            } else {
                Utills.showSnackbarNonSticky(relativeLayout, wsVideosList != null ? "Something went wrong" : null, true, getActivity());
            }
        }
    }

    /**
     * AsyncTask for VideoList
     */
    private class AsynVidPdfList extends AsyncTask<String, Void, ArrayList<VidPdfListModel>> {
        private WSVidPdfList wsVidPdfList;
        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsVidPdfList = new WSVidPdfList(getActivity());
            progressDialog = Utills.showProgressDialogNew(getActivity(), getString(R.string.msg_loading), false);
        }

        @Override
        protected ArrayList<VidPdfListModel> doInBackground(String... params) {
            return wsVidPdfList.executeService(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<VidPdfListModel> result) {
            super.onPostExecute(result);
            if (isCancelled()) {
                return;
            }

            if (result.size() <= 0) {
                tvNoData.setVisibility(View.VISIBLE);
                Utills.dismissProgressDialogNew(progressDialog);
            } else {
                Utills.dismissProgressDialogNew(progressDialog);
            }
            if (wsVidPdfList != null) {
                fragmentVidPdfAdapter = new FragmentVidPdfAdapter(getActivity(), result, manager);
                recyclerVideos.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                recyclerVideos.setAdapter(fragmentVidPdfAdapter);
            } else {
                Utills.showSnackbarNonSticky(relativeLayout, wsVidPdfList != null ? "Something went wrong" : null, true, getActivity());
            }
        }
    }


}
