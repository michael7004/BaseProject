package com.example.indianic.baseproject.webservice;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.indianic.baseproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class WSUtils {
    private Context context;
    /**
     * Service call for POST method
     *
     * @param context     activity context
     * @param url         api url
     * @param requestBody request parameter
     * @return response of API
     */
    String callServiceHttpPost(final Context context, final String url, final RequestBody requestBody) {
        this.context=context;

        Log.d(WSUtils.class.getSimpleName(), "Request Url : " + url);
        Log.d(WSUtils.class.getSimpleName(), String.format("Request String : %s", requestBody.toString()));
        String responseString;
        try {
            final OkHttpClient okHttpClient = new OkHttpClient();
            final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
            okHttpClientBuilder.connectTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            final Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(url);
            requestBuilder.post(requestBody);
            final Request request = requestBuilder.build();
            final Response response = okHttpClient.newCall(request).execute();
            responseString = response.body().string();
            //Log.d("Response", responseString);
            if (TextUtils.isEmpty(responseString) || !isJSONValid(responseString)) {
                responseString = getNetWorkError(context);
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseString = getNetWorkError(context);
        }
        return responseString;
    }

    /**
     * Service call for POST method
     *
     * @param context     activity context
     * @param url         api url
     * @param requestBody request parameter
     * @return response of API
     */
    String callServiceHttpPostInvalid(final Context context, final String url, final RequestBody requestBody) {
        this.context=context;

        Log.d(WSUtils.class.getSimpleName(), "Request Url : " + url);
        Log.d(WSUtils.class.getSimpleName(), String.format("Request String : %s", requestBody.toString()));
        String responseString;
        try {
            final OkHttpClient okHttpClient = new OkHttpClient();
            final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
            okHttpClientBuilder.connectTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            final Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(url);
            requestBuilder.post(requestBody);
            final Request request = requestBuilder.build();
            final Response response = okHttpClient.newCall(request).execute();
            responseString = response.body().string();
            Log.d("Response", responseString);
//            if (TextUtils.isEmpty(responseString) || !isJSONValid(responseString)) {
//                responseString = getNetWorkError(context);
//            }
        } catch (IOException e) {
            e.printStackTrace();
            responseString = getNetWorkError(context);
        }
        return responseString;
    }


    /**
     * Service call for GET method
     *
     * @param context activity context
     * @param url     api url
     * @return response of API
     */
    String callServiceHttpGet(final Context context, final String url) {
        this.context=context;
        Log.d(WSUtils.class.getSimpleName(), "Request Url : " + url);

        String responseString;
        try {
            final OkHttpClient okHttpClient = new OkHttpClient();
            final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
            okHttpClientBuilder.connectTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            final Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(url);
            requestBuilder.get();
            final Request request = requestBuilder.build();
            final Response response = okHttpClient.newCall(request).execute();
            responseString = response.body().string();
            if (TextUtils.isEmpty(responseString) || !isJSONValid(responseString)) {
                responseString = getNetWorkError(context);
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseString = getNetWorkError(context);
        }
        return responseString;
    }

    String postUploadUserProfilePhotosInMultiPart(final Context context, final String url, final RequestBody multipartBody) {
        this.context=context;
        final Response response;

        Log.d(WSUtils.class.getSimpleName(), "Request Url : " + url);
        Log.d(WSUtils.class.getSimpleName(), String.format("Request String : %s", multipartBody.toString()));
        String responseString;
        try {
            final OkHttpClient okHttpClient = new OkHttpClient();
            final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
            okHttpClientBuilder.connectTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(WSConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            final Request.Builder builder = new Request.Builder();
            builder.url(url);
            builder.put(multipartBody);
            response = okHttpClient.newCall(builder.build()).execute();
            responseString = response.body().string();
            if (TextUtils.isEmpty(responseString) || !isJSONValid(responseString)) {
                responseString = getNetWorkError(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseString = getNetWorkError(context);
        }
        return responseString;
    }

    /**
     * Generate network error message
     *
     * @param context activity context
     * @return string response of json
     */
    private String getNetWorkError(final Context context) {
        this.context=context;
        final JSONObject jsonObject = new JSONObject();
        try {
            final WSConstants wsConstants = new WSConstants();
            jsonObject.put(wsConstants.PARAMS_RESULT, "0");
            jsonObject.put(wsConstants.PARAMS_CODE, 101);
            jsonObject.put(wsConstants.PARAMS_MESSAGE, context.getString(R.string.msg_network_error));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * Checks the json is valid or not
     *
     * @param test value of JSON
     * @return return tru is valid json
     */
    private boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
