package com.example.indianic.baseproject.webservice;

import android.content.Context;
import android.util.Log;

import com.example.indianic.baseproject.fragment.HomeFragment;
import com.example.indianic.baseproject.model.PromotionalSliderModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * API call for login
 */
public class WSPromotionSliderList {

//    http://mosaicdesigns.in/webservice/app_banner_silder

//    http://mosaicdesigns.in/assets/promotional-banners/1.png

    private Context context;
    private String message;


    private boolean success;
    private String a_psid;
    private String a_psalt;
    private String a_pstitle;
    private String a_pshref;
    private String delflag;


    ArrayList<PromotionalSliderModel> promotionalSliderModels;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public WSPromotionSliderList(final Context context, ArrayList<PromotionalSliderModel> promotionalSliderModels) {
        this.context = context;
        this.promotionalSliderModels = promotionalSliderModels;
    }


    /**
     *
     */
    public String executeService() {
        final String url = HomeFragment.BannerUrl;
        final String response = new WSUtils().callServiceHttpGet(context, url);
        return parseResponse(response);
    }

    /**
     * Generate RequestBody.
     */
    private RequestBody generateRegisterRequest() {
        final WSConstants wsConstants = new WSConstants();
        final FormBody.Builder formEncodingBuilder = new FormBody.Builder();
//        formEncodingBuilder.add(wsConstants.PARAMS_CLIENT_CODE, clientID);
//        formEncodingBuilder.add(wsConstants.PARAMS_PASSWORD, password);
        return formEncodingBuilder.build();
    }

    /**
     * response from add review api
     *
     * @param response Response in string format
     */
    private String parseResponse(final String response) {
        String result = null;
        Log.d("Register Response", response);
        if (response != null && response.trim().length() > 0) {
            try {
                final WSConstants wsConstants = new WSConstants();
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("Log", jsonObject.toString());


                    a_psid = jsonObject.optString("a_hsid");
                    a_psalt = jsonObject.optString("a_hsalt");
                    a_pstitle = jsonObject.optString("a_hstitle");
                    a_pshref = jsonObject.optString("a_hshref");
                    delflag = jsonObject.optString("delflag");

                    PromotionalSliderModel promotionalSliderModel = new PromotionalSliderModel();
                    promotionalSliderModel.setA_psid(a_psid);
                    promotionalSliderModel.setA_psalt(a_psalt);
                    promotionalSliderModel.setA_pstitle(a_pshref);
                    promotionalSliderModel.setA_pshref(a_pshref);
                    promotionalSliderModel.setDelflag(delflag);
                    promotionalSliderModels.add(promotionalSliderModel);

                }
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public ArrayList<PromotionalSliderModel> getPromotionalSliderModels() {
        return promotionalSliderModels;
    }


}
