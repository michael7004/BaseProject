package com.example.indianic.baseproject.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.utills.Constants;
import com.example.indianic.baseproject.utills.Preference;
import com.example.indianic.baseproject.utills.Utills;
import com.example.indianic.baseproject.webservice.WSLogin;

import static com.example.indianic.baseproject.utills.Utills.showSnackbarNonSticky;

/**
 * LoginActivity class created on 24/04/17.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private final int PERMISSION_REQUEST_WRITE_STORAGE_CODE = 100;
    private long mLastClickTime = 0;
    private TextInputLayout inputLayoutmail;
    private TextInputLayout inputLayouPassword;
    private EditText etEmail;
    private EditText etPassWord;
    private TextView tvForgotPassWord;
    private TextView tvRegistration;
    private Button btnSummit;
    private Button btnFaceBook;
    private ScrollView svParent;
    private AsyncLogin asyncLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    public void initView() {
        Utills.hideSoftKeyboard(LoginActivity.this);
        svParent = (ScrollView) findViewById(R.id.activity_login_sv_main);
        inputLayoutmail = (TextInputLayout) findViewById(R.id.activity_login_tnl_email);
        inputLayouPassword = (TextInputLayout) findViewById(R.id.activity_login_tnl_password);
        etEmail = (EditText) findViewById(R.id.activity_login_et_email);
        etPassWord = (EditText) findViewById(R.id.activity_login_et_password);
        tvForgotPassWord = (TextView) findViewById(R.id.activity_login_iv_forgot_password);

        btnSummit = (Button) findViewById(R.id.activity_login_btn_login);

        tvForgotPassWord.setOnClickListener(this);

        btnSummit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < Constants.MAX_CLICK_INTERVAL) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (v.getId()) {
            case R.id.activity_login_btn_login:
                if (validation()) {
                    final String WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                    if (Utills.checkForPermission(LoginActivity.this, WRITE_STORAGE_PERMISSION)) {
                        login(etEmail.getText().toString(), etPassWord.getText().toString());

//                        startActivity(new Intent(this, MainActivity.class));
//                        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
//                        finish();
                    } else {
                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{WRITE_STORAGE_PERMISSION}, PERMISSION_REQUEST_WRITE_STORAGE_CODE);
                    }
                }
                break;


            case R.id.activity_login_iv_forgot_password:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
                finish();
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_WRITE_STORAGE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    login(etEmail.getText().toString(), etPassWord.getText().toString());
                } else {
                    showSnackbarNonSticky(svParent, getString(R.string.err_msg_permission_write_phone), true, LoginActivity.this);
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (asyncLogin != null && asyncLogin.getStatus() == AsyncTask.Status.RUNNING) {
            asyncLogin.cancel(true);
        }
    }

    /**
     * API call for Login
     *
     * @param clientID
     */
    private void login(String clientID, String password) {
//        clientID = Utills.encrypt(clientID);
//        password = Utills.encrypt(password);
        if (Utills.isNetworkAvailable(LoginActivity.this) && !TextUtils.isEmpty(clientID) && !TextUtils.isEmpty(password)) {
            if (asyncLogin != null && asyncLogin.getStatus() == AsyncTask.Status.PENDING) {
                asyncLogin.execute(clientID, password);
            } else if (asyncLogin == null || asyncLogin.getStatus() == AsyncTask.Status.FINISHED) {
                asyncLogin = new AsyncLogin();
                asyncLogin.execute(clientID, password);
            }
        } else {
            Utills.showSnackbarNonSticky(svParent, getString(R.string.msg_no_internet), true, LoginActivity.this);
        }
    }


    /**
     * Validate user input for further process
     *
     * @return true if fields are validate or false
     */
    private boolean validation() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            showSnackbarNonSticky(svParent, getString(R.string.err_msg_enter_client_id), true, LoginActivity.this);
            etEmail.requestFocus();
            return false;
        } else if (!Utills.isValidEmail(etEmail.getText().toString())) {
            showSnackbarNonSticky(svParent, getString(R.string.err_msg_enter_valid_email_msg), true, LoginActivity.this);
            etEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etPassWord.getText().toString())) {
            showSnackbarNonSticky(svParent, getString(R.string.err_msg_enter_password), true, LoginActivity.this);
            etPassWord.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    /**
     * AsyncTask for login
     */
    private class AsyncLogin extends AsyncTask<String, Void, String> {
        private WSLogin wsLogin;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsLogin = new WSLogin(LoginActivity.this);
            progressDialog = Utills.showProgressDialog(LoginActivity.this, getString(R.string.msg_loading), false);
        }

        @Override
        protected String doInBackground(String... params) {
            return wsLogin.executeService(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Utills.dismissProgressDialog(progressDialog);
            if (isCancelled()) {
                return;
            }
            if (wsLogin != null && wsLogin.isSuccess()) {

                Preference.getInstance().savePreferenceData(Constants.PRE_IS_LOGIN, true);
                Preference.getInstance().savePreferenceData(Constants.PRE_USER_ID, wsLogin.getRegid());
                Preference.getInstance().savePreferenceData(Constants.PRE_FULL_NAME, wsLogin.getFullname());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
                finish();
            } else {
                Utills.showSnackbarNonSticky(svParent, wsLogin != null ? "Email or password wrong" : null, true, LoginActivity.this);
            }
        }
    }

}
