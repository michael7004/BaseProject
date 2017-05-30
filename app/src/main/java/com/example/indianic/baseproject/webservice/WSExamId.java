package com.example.indianic.baseproject.webservice;

import android.content.Context;
import android.util.Log;

import com.example.indianic.baseproject.utills.Constants;
import com.example.indianic.baseproject.utills.Preference;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * API call for Check Exam id
 */
public class WSExamId {
    private Context context;
    private String message;
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public WSExamId(final Context context) {
        this.context = context;
    }


    /**
     * @param ID Client ID
     */
    public String executeService(final String ID) {
        final String url = WSConstants.BASE_URL + WSConstants.METHOD_UPDATE_EXAM_ID;
        final String response = new WSUtils().callServiceHttpPost(context, url, generateRegisterRequest(ID));
        return parseResponse(response);
    }

    /**
     * Generate RequestBody.
     */
    private RequestBody generateRegisterRequest(final String ID) {
        final WSConstants wsConstants = new WSConstants();
        final FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        formEncodingBuilder.add(wsConstants.PARAMS_ID, Preference.getInstance().mSharedPreferences.getString(Constants.PRE_USER_ID,""));
        formEncodingBuilder.add(wsConstants.PARAMS_EXAM_MSG_ID,ID);

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

                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.optString("error");
                message = jsonObject.getString("message");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
