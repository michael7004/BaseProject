package com.example.indianic.baseproject.webservice;

import android.content.Context;
import android.util.Log;

import com.example.indianic.baseproject.fragment.HomeFragment;
import com.example.indianic.baseproject.model.ProductSliderModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * API call for login
 */
public class WSProductionSliderList {

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



    ArrayList<ProductSliderModel> productSliderModels;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public WSProductionSliderList(final Context context, ArrayList<ProductSliderModel> productSliderModels) {
        this.context = context;
        this.productSliderModels=productSliderModels;
    }



    /**
     *
     */
    public String executeService() {
        final String url = HomeFragment.pdfUrl;
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

                for (int i = 0; i <jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("Log", jsonObject.toString());
                    a_psid = jsonObject.optString("a_psid");
                    a_psalt = jsonObject.optString("a_psalt");
                    a_pstitle = jsonObject.optString("a_pstitle");
                    a_pshref = jsonObject.optString("a_pshref");
                    delflag = jsonObject.optString("delflag");
                    ProductSliderModel productSliderModel=new ProductSliderModel();
                    productSliderModel.setA_psid(a_psid);
                    productSliderModel.setA_psalt(a_psalt);
                    productSliderModel.setA_pstitle(a_pstitle);
                    productSliderModel.setA_pshref(a_pshref);
                    productSliderModel.setDelflag(delflag);
                    productSliderModels.add(productSliderModel);
                }
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public ArrayList<ProductSliderModel> getProductSliderModels() {
        return productSliderModels;
    }
}
