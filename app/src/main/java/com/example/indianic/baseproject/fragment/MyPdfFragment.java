package com.example.indianic.baseproject.fragment;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;
import com.example.indianic.baseproject.adapter.FragmentPdfAdapter;
import com.example.indianic.baseproject.model.MyPdfModel;
import com.example.indianic.baseproject.utills.Constants;
import com.example.indianic.baseproject.utills.Preference;
import com.example.indianic.baseproject.utills.Utills;
import com.example.indianic.baseproject.webservice.WSMyPdfList;

import java.util.ArrayList;

/**
 * MyPdfFragment class created on 01/05/17.
 */

public class MyPdfFragment extends BaseFragment {

//    http://mosaicdesigns.in/assets/videodownloads/32.pdf

    private RecyclerView recyclerViewPdf;
    private ArrayList<String> arrayList;
    private ArrayList<MyPdfModel> arrayListMyPdf = new ArrayList<>();
    private FragmentPdfAdapter fragmentPdfAdapter;
    private GridLayoutManager lLayout;
    private FragmentManager manager;


    private View view;
    private RelativeLayout relativeLayout;
    private Context context;
    private TextView tvNoData;
    private AsynPDFList asynPDFList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pdf, container, false);
        return view;
    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("My Pdfs");
//        ((MainActivity) getActivity()).setTitleColor(ContextCompat.getColor(context, R.color.colorWhite));

        manager = getFragmentManager();
        recyclerViewPdf = (RecyclerView) view.findViewById(R.id.fragment_pdf_rv_main);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.fragment_my_pdf_rl_container);
        tvNoData = (TextView) view.findViewById(R.id.fragment_my_pdf_tv_no_data);
        getPDfList(Preference.getInstance().mSharedPreferences.getString(Constants.PRE_USER_ID,""));
    }

    @Override
    public void trackScreen() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (asynPDFList != null && asynPDFList.getStatus() == AsyncTask.Status.RUNNING) {
            asynPDFList.cancel(true);
        }
    }

    /**
     * API call for Pdf LIst
     *
     * @param clientID
     */
    private void getPDfList(String clientID) {
        if (Utills.isNetworkAvailable(getActivity())) {
            if (asynPDFList != null && asynPDFList.getStatus() == AsyncTask.Status.PENDING) {
                asynPDFList.execute(clientID);
            } else if (asynPDFList == null || asynPDFList.getStatus() == AsyncTask.Status.FINISHED) {
                asynPDFList = new AsynPDFList();
                asynPDFList.execute(clientID);
            }
        } else {
            Utills.showSnackbarNonSticky(relativeLayout, getString(R.string.msg_no_internet), true, getActivity());
        }
    }

    /**
     * AsyncTask for VideoList
     */
    private class AsynPDFList extends AsyncTask<String, Void, ArrayList<MyPdfModel>> {
        private WSMyPdfList wsMyPdfList;
        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsMyPdfList = new WSMyPdfList(getActivity());
            progressDialog = Utills.showProgressDialogNew(getActivity(), getString(R.string.msg_loading), false);
        }

        @Override
        protected ArrayList<MyPdfModel> doInBackground(String... params) {
            return wsMyPdfList.executeService(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<MyPdfModel> result) {
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
            if (wsMyPdfList != null) {
                Log.d("MyPDf", String.valueOf(result.size()));
                fragmentPdfAdapter = new FragmentPdfAdapter(getActivity(), result, manager);
                recyclerViewPdf.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                recyclerViewPdf.setAdapter(fragmentPdfAdapter);
            } else {
                Utills.showSnackbarNonSticky(relativeLayout, wsMyPdfList != null ? "Something went wrong" : null, true, getActivity());
            }
        }
    }


}
