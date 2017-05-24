package com.example.indianic.baseproject.webservice;

import android.content.Context;
import android.util.Log;

import com.example.indianic.baseproject.model.MyPdfModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * API call for login
 */
public class WSMyPdfList {
    private Context context;
    private String did;
    private String smid;
    private String filetitle;
    private String dlprice;
    private String ordid;
    private String prdname;
    private String examid;

    private ArrayList<MyPdfModel> arrayListMyPdf = new ArrayList<>();


    public WSMyPdfList(Context context) {
        this.context = context;
        this.arrayListMyPdf = arrayListMyPdf;

    }


    /**
     * @param clientID Client ID
     */
    public ArrayList<MyPdfModel> executeService(final String clientID) {
        final String url = WSConstants.BASE_URL + WSConstants.METHOD_MY_PDF_LIST;
        final String response = new WSUtils().callServiceHttpPost(context, url, generateRegisterRequest(clientID));
        return parseResponse(response);
    }

    /**
     * Generate RequestBody.
     */
    private RequestBody generateRegisterRequest(final String clientID) {
        final WSConstants wsConstants = new WSConstants();
        final FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        formEncodingBuilder.add(wsConstants.PARAMS_ID, clientID);
        return formEncodingBuilder.build();
    }

    /**
     * response from add review api
     *
     * @param response Response in string format
     */
    private ArrayList<MyPdfModel> parseResponse(final String response) {
        String result = null;
        Log.d("Register Response", response);
        if (response != null && response.trim().length() > 0) {
            try {
                final WSConstants wsConstants = new WSConstants();
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONArray jsonArrayNest = jsonArray.getJSONArray(i);

                    for (int j = 0; j < jsonArrayNest.length(); j++) {

                        JSONObject jsonObject = jsonArrayNest.getJSONObject(j);


                        did = jsonObject.optString("did");
                        smid = jsonObject.optString("smid");
                        filetitle = jsonObject.optString("filetitle");
                        dlprice = jsonObject.optString("dlprice");
                        ordid = jsonObject.optString("ordid");
                        prdname = jsonObject.optString("prdname");
                        examid = jsonObject.optString("examid");


                        MyPdfModel myPdfModel = new MyPdfModel();

                        myPdfModel.setDid(did);
                        myPdfModel.setSmid(smid);
                        myPdfModel.setFiletitle(filetitle);
                        myPdfModel.setDlprice(dlprice);
                        myPdfModel.setOrdid(ordid);
                        myPdfModel.setPrdname(prdname);
                        myPdfModel.setExamid(examid);
                        arrayListMyPdf.add(myPdfModel);


                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayListMyPdf;
    }

}
