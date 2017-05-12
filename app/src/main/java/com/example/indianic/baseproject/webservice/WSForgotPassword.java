package com.example.indianic.baseproject.webservice;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * API call for login
 */
public class WSForgotPassword {
    private Context context;
    private String message;
    private boolean success;
    private String code;
    private String SucessNew;

    public String getSucessNew() {
        return SucessNew;
    }

    public void setSucessNew(String sucessNew) {
        SucessNew = sucessNew;
    }

    public WSForgotPassword(final Context context) {
        this.context = context;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    /**
     * @param clientID Client ID
     */
    public String executeService(final String clientID) {
        final String url = WSConstants.BASE_URL + WSConstants.METHOD_FORGOT_PASSWORD;
        final String response = new WSUtils().callServiceHttpPostInvalid(context, url, generateRegisterRequest(clientID));
        return parseResponse(response);
    }

    /**
     * Generate RequestBody.
     */
    private RequestBody generateRegisterRequest(final String clientID) {
        final WSConstants wsConstants = new WSConstants();
        final FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        formEncodingBuilder.add(wsConstants.PARAMS_CLIENT_CODE, clientID);

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


                message=response;

                JSONObject jsonObject=new JSONObject(response);
                message=jsonObject.optString("message");
                SucessNew = jsonObject.optString("error");;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
