package com.example.indianic.baseproject.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;
import com.example.indianic.baseproject.utills.Constants;
import com.example.indianic.baseproject.utills.Preference;
import com.example.indianic.baseproject.utills.Utills;
import com.example.indianic.baseproject.webservice.WSFeedback;

import static com.example.indianic.baseproject.utills.Utills.showSnackbarNonSticky;

/**
 * MyPdfFragment class created on 01/05/17.
 */

public class FeedBackFragment extends BaseFragment {


    private EditText etMsgArea;
    private Button btnSubmit;
    private View view;
    private RelativeLayout svParent;
    private Context context;
    private AsyncFeedback AsyncFeedback;
    private String msg = "Feedback has been submitted successfully";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        return view;


    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("FeedBack");
//        ((MainActivity) getActivity()).setTitleColor(ContextCompat.getColor(context, R.color.colorWhite));

        etMsgArea = (EditText) view.findViewById(R.id.fragment_feed_back_et_info_area);
        svParent = (RelativeLayout) view.findViewById(R.id.fragment_feed_back_rl_container);
        btnSubmit = (Button) view.findViewById(R.id.fragment_feed_back_btn_submit);
        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void trackScreen() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.fragment_feed_back_btn_submit:
                if (validation()) {
                    feedBack(Preference.getInstance().mSharedPreferences.getString(Constants.PRE_USER_ID, ""), etMsgArea.getText().toString().trim());
                }

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (AsyncFeedback != null && AsyncFeedback.getStatus() == AsyncTask.Status.RUNNING) {
            AsyncFeedback.cancel(true);
        }

    }

    /**
     * Validate user input for further process
     *
     * @return true if fields are validate or false
     */
    private boolean validation() {
        if (TextUtils.isEmpty(etMsgArea.getText().toString())) {
            showSnackbarNonSticky(svParent, getString(R.string.err_msg_enter_msg), true, getActivity());
            etMsgArea.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    /**
     * API call for resend pin
     *
     * @param clientID
     */
    private void feedBack(String clientID, String password) {
//        clientID = Utills.encrypt(clientID);
//        password = Utills.encrypt(password);
        if (Utills.isNetworkAvailable(getActivity()) && !TextUtils.isEmpty(clientID) && !TextUtils.isEmpty(password)) {
            if (AsyncFeedback != null && AsyncFeedback.getStatus() == AsyncTask.Status.PENDING) {
                AsyncFeedback.execute(clientID, password);
            } else if (AsyncFeedback == null || AsyncFeedback.getStatus() == AsyncTask.Status.FINISHED) {
                AsyncFeedback = new AsyncFeedback();
                AsyncFeedback.execute(clientID, password);
            }
        } else {
            Utills.showSnackbarNonSticky(svParent, getString(R.string.msg_no_internet), true, getActivity());
        }
    }

    /**
     * AsyncTask for FeedBack
     */
    private class AsyncFeedback extends AsyncTask<String, Void, String> {
        private WSFeedback wsFeedback;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsFeedback = new WSFeedback(getActivity());
            progressDialog = Utills.showProgressDialog(getActivity(), getString(R.string.msg_loading), false);
        }

        @Override
        protected String doInBackground(String... params) {
            return wsFeedback.executeService(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Utills.dismissProgressDialog(progressDialog);
            if (isCancelled()) {
                return;
            }
            if (wsFeedback != null && wsFeedback.getMessage().equalsIgnoreCase(msg)) {
                Utills.showSnackbarNonSticky(svParent, wsFeedback != null ? wsFeedback.getMessage() : null, true, getActivity());
                Utills.hideSoftKeyboard(getActivity());
                etMsgArea.setText("");


            } else {
                Utills.showSnackbarNonSticky(svParent, wsFeedback != null ? "Something went wrong" : null, true, getActivity());
            }
        }
    }


}
