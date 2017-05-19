package com.example.indianic.baseproject.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.adapter.FragmentHomeSubAdapter;
import com.example.indianic.baseproject.adapter.ViewPagerAdapter;
import com.example.indianic.baseproject.model.ProductSliderModel;
import com.example.indianic.baseproject.model.PromotionalSliderModel;
import com.example.indianic.baseproject.utills.Utills;
import com.example.indianic.baseproject.webservice.WSProductionSliderList;
import com.example.indianic.baseproject.webservice.WSPromotionSliderList;

import java.util.ArrayList;

/**
 * HomeFragment class created on 11/05/17.
 */

public class HomeFragment extends BaseFragment {

    protected View view;
    private ViewPager intro_images;
    private RecyclerView recyclerView;
    private LinearLayout pager_indicator;
    private LinearLayout linearLayout;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;
    private AsynProductList asynProductList;
    private AsynPromotionList asynPromotionList;


    public static String pdfUrl = "http://mosaicdesigns.in/webservice/app_product_slider";
    public static String BannerUrl = "http://mosaicdesigns.in/webservice/app_banner_silder";

//    http://mosaicdesigns.in/assets/promotional-banners/1.png

    private ArrayList<ProductSliderModel> productSliderModels = new ArrayList<>();
    private ArrayList<PromotionalSliderModel> promotionalSliderModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void initView(View view) {
        getPromotionaList();
        getProductList();
        intro_images = (ViewPager) view.findViewById(R.id.pager_introduction);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_rv_sub_slider);
        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);
        linearLayout = (LinearLayout) view.findViewById(R.id.fragment_home_ll_parent);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void trackScreen() {

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

    /**
     * API call for get Promotional list
     */
    private void getPromotionaList() {

        if (Utills.isNetworkAvailable(getActivity())) {
            if (asynPromotionList != null && asynPromotionList.getStatus() == AsyncTask.Status.PENDING) {
                asynPromotionList.execute();
            } else if (asynPromotionList == null || asynPromotionList.getStatus() == AsyncTask.Status.FINISHED) {
                asynPromotionList = new AsynPromotionList();
                asynPromotionList.execute();
            }
        } else {
            Utills.showSnackbarNonSticky(linearLayout, getString(R.string.msg_no_internet), true, getActivity());
        }
    }


    /**
     * API call for get Product list
     */
    private void getProductList() {

        if (Utills.isNetworkAvailable(getActivity())) {
            if (asynProductList != null && asynProductList.getStatus() == AsyncTask.Status.PENDING) {
                asynProductList.execute();
            } else if (asynProductList == null || asynProductList.getStatus() == AsyncTask.Status.FINISHED) {
                asynProductList = new AsynProductList();
                asynProductList.execute();
            }
        } else {
            Utills.showSnackbarNonSticky(linearLayout, getString(R.string.msg_no_internet), true, getActivity());
        }
    }

    /**
     * /**
     * AsyncTask for Productlist
     */
    private class AsynProductList extends AsyncTask<Void, Void, String> {
        private WSProductionSliderList wsProductionSliderList;
//        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsProductionSliderList = new WSProductionSliderList(getActivity(), productSliderModels);
//            progressDialog = Utills.showProgressDialog(getActivity(), getString(R.string.msg_loading), false);
        }

        @Override
        protected String doInBackground(Void... params) {
            return wsProductionSliderList.executeService();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Utills.dismissProgressDialog(progressDialog);
            if (isCancelled()) {
                return;
            }
            if (wsProductionSliderList != null && wsProductionSliderList.isSuccess()) {
                Log.d("Data", String.valueOf(wsProductionSliderList.getProductSliderModels()));
                ArrayList<ProductSliderModel> productSliderModels = wsProductionSliderList.getProductSliderModels();
                FragmentHomeSubAdapter adapter = new FragmentHomeSubAdapter(getActivity(), productSliderModels);
                recyclerView.setAdapter(adapter);

            } else {
                Utills.showSnackbarNonSticky(linearLayout, wsProductionSliderList != null ? "Something went wrong" : null, true, getActivity());
            }
        }
    }


    /**
     * /**
     * AsyncTask for Promotionlist
     */
    private class AsynPromotionList extends AsyncTask<Void, Void, String> implements ViewPager.OnPageChangeListener {
        private WSPromotionSliderList wsPromotionSliderList;
//        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsPromotionSliderList = new WSPromotionSliderList(getActivity(), promotionalSliderModels);
//            progressDialog = Utills.showProgressDialog(getActivity(), getString(R.string.msg_loading), false);
        }

        @Override
        protected String doInBackground(Void... params) {
            return wsPromotionSliderList.executeService();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Utills.dismissProgressDialog(progressDialog);
            if (isCancelled()) {
                return;
            }
            if (wsPromotionSliderList != null && wsPromotionSliderList.isSuccess()) {
//                Log.d("Data", String.valueOf(wsPromotionSliderList.getProductSliderModels()));
                ArrayList<PromotionalSliderModel> promotionalSliderModels = wsPromotionSliderList.getPromotionalSliderModels();
                mAdapter = new ViewPagerAdapter(getActivity(), promotionalSliderModels);
                intro_images.setAdapter(mAdapter);
                intro_images.setCurrentItem(0);
                intro_images.setOnPageChangeListener(this);
                setUiPageViewController();
            } else {
                Utills.showSnackbarNonSticky(linearLayout, wsPromotionSliderList != null ? "Something went wrong" : null, true, getActivity());
            }
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
    }


}
