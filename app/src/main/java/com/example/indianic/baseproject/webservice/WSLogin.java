package com.example.indianic.baseproject.webservice;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * API call for login
 */
public class WSLogin {
    private Context context;
    private String message;


    private boolean success;
    private String code;
    private String regid = "";
    private String email = "";
    private String fullname = "";


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    private String examid = "";
    private String regstatus = "";
    private String actflag = "";
    private String delfalg = "";


    public WSLogin(final Context context) {
        this.context = context;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    /**
     * @param clientID Client ID
     * @param password Password
     */
    public String executeService(final String clientID, final String password, final String uniqueId) {
        final String url = WSConstants.BASE_URL + WSConstants.METHOD_STUDENT_LOGIN;
        final String response = new WSUtils().callServiceHttpPost(context, url, generateRegisterRequest(clientID, password, uniqueId));
        return parseResponse(response);
    }

    /**
     * Generate RequestBody.
     */
    private RequestBody generateRegisterRequest(final String clientID, final String password, final String uniqueId) {
        final WSConstants wsConstants = new WSConstants();
        final FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        formEncodingBuilder.add(wsConstants.PARAMS_CLIENT_CODE, clientID);
        formEncodingBuilder.add(wsConstants.PARAMS_PASSWORD, password);
        formEncodingBuilder.add(wsConstants.PARAMS_DEVICEID, "12");
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


                Object json = new JSONTokener(response).nextValue();
                if (json instanceof JSONObject) {
                    JSONObject jsonObjectError = new JSONObject(response);
                    message = jsonObjectError.optString(wsConstants.PARAMS_MSG);
                    success = false;
                }
                //you have an object
                else if (json instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Log.d("Log", jsonObject.toString());
                    regid = jsonObject.optString(wsConstants.PARAMS_ID);
                    email = jsonObject.optString(wsConstants.PARAMS_EMAIL);
                    fullname = jsonObject.optString(wsConstants.PARAMS_FNAME);
                    examid = jsonObject.optString(wsConstants.PARAMS_EXAMID);
                    regstatus = jsonObject.optString(wsConstants.PARAMS_REGSTATUS);
                    actflag = jsonObject.optString(wsConstants.PARAMS_ACTFLAG);
                    delfalg = jsonObject.optString(wsConstants.PARAMS_DELFLAG);
                    success = true;
                }
                //you have an array


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
