package com.example.indianic.baseproject.webservice;

import android.content.Context;
import android.util.Log;

import com.example.indianic.baseproject.model.VidPdfListModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * API call for login
 */
public class WSpdfList {
    private Context context;

    private String vdid;
    private String vcpkgid;
    private String filetitle;
    private String description;
    private String dlprice;
    private String extension;
    private String commonvideo;
    private String seqno;
    private String pageurl;
    private String createdon;
    private String createdby;
    private String delflag;


    private ArrayList<VidPdfListModel> vidPdfListModels=new ArrayList<>();


    public WSpdfList(Context context) {
        this.context = context;
        this.vidPdfListModels = vidPdfListModels;

    }


    /**
     * @param clientID Client ID
     */
    public ArrayList<VidPdfListModel> executeService(final String clientID) {
        final String url = WSConstants.BASE_URL + WSConstants.METHOD_PDF_LIST;
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
    private ArrayList<VidPdfListModel> parseResponse(final String response) {
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
                        vdid = jsonObject.optString("vdid");
                        vcpkgid = jsonObject.optString("vcpkgid");
                        filetitle = jsonObject.optString("filetitle");
                        description = jsonObject.optString("description");
                        dlprice = jsonObject.optString("dlprice");
                        extension = jsonObject.optString("extension");
                        commonvideo = jsonObject.optString("commonvideo");
                        seqno = jsonObject.optString("seqno");
                        pageurl = jsonObject.optString("pageurl");
                        createdon = jsonObject.optString("createdon");
                        createdby = jsonObject.optString("createdby");
                        delflag = jsonObject.optString("delflag");

                        VidPdfListModel vidPdfListModel = new VidPdfListModel();
                        vidPdfListModel.setVdid(vdid);
                        vidPdfListModel.setVcpkgid(vcpkgid);
                        vidPdfListModel.setFiletitle(filetitle);
                        vidPdfListModel.setDescription(description);
                        vidPdfListModel.setDlprice(dlprice);
                        vidPdfListModel.setExtension(extension);
                        vidPdfListModel.setCommonvideo(commonvideo);
                        vidPdfListModel.setSeqno(seqno);
                        vidPdfListModel.setPageurl(pageurl);
                        vidPdfListModel.setCreatedon(createdon);
                        vidPdfListModel.setCreatedby(createdby);
                        vidPdfListModel.setDelflag(delflag);
                        vidPdfListModels.add(vidPdfListModel);

                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vidPdfListModels;
    }

}
