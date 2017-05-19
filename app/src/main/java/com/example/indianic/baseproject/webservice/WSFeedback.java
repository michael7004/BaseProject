package com.example.indianic.baseproject.webservice;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * API call for login
 */
public class WSFeedback {
    private Context context;

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    private boolean success;
    private String code;


    public WSFeedback(final Context context) {
        this.context = context;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }


    /**
     * @param clientID Client ID
     * @param feedback feedback
     */
    public String executeService(final String clientID, final String feedback) {
        final String url = WSConstants.BASE_URL + WSConstants.METHOD_STUDENT_FEEDBACKS;
        final String response = new WSUtils().callServiceHttpPost(context, url, generateRegisterRequest(clientID, feedback));
        return parseResponse(response);
    }

    /**
     * Generate RequestBody.
     */
    private RequestBody generateRegisterRequest(final String clientID, final String feedback) {
        final WSConstants wsConstants = new WSConstants();
        final FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        formEncodingBuilder.add(wsConstants.PARAMS_ID, clientID);
        formEncodingBuilder.add(wsConstants.PARAMS_FEED_BACK, feedback);
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
                JSONObject jsonObject = new JSONObject(response);
                Log.d("Log", jsonObject.toString());
                message = jsonObject.getString("error");
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
