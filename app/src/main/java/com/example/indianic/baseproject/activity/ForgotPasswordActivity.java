package com.example.indianic.baseproject.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.utills.Utills;
import com.example.indianic.baseproject.webservice.WSForgotPassword;

import static com.example.indianic.baseproject.utills.Utills.showSnackbarNonSticky;

/**
 * ForgotPasswordActivity class created on 01/05/17.
 */

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    private final int PERMISSION_REQUEST_WRITE_STORAGE_CODE = 100;
    private TextInputLayout tilForgotPassword;
    private EditText etEmail;
    private Button btnSend;
    private ConstraintLayout svParent;
    private AsyncForgotPassword asyncForgotPassword;
    private String success = "Success";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
    }

    @Override
    public void initView() {
        tilForgotPassword = (TextInputLayout) findViewById(R.id.activity_forgot_password_til_title);
        etEmail = (EditText) findViewById(R.id.activity_forgot_password_et_forgot_password);
        btnSend = (Button) findViewById(R.id.activity_forgot_password_btn_submit);
        svParent = (ConstraintLayout) findViewById(R.id.activity_forgot_password_cl_container);
        btnSend.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_forgot_password_btn_submit:
                if (validation()) {
                    final String WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                    if (Utills.checkForPermission(ForgotPasswordActivity.this, WRITE_STORAGE_PERMISSION)) {
                        login(etEmail.getText().toString().trim());
                    } else {
                        ActivityCompat.requestPermissions(ForgotPasswordActivity.this, new String[]{WRITE_STORAGE_PERMISSION}, PERMISSION_REQUEST_WRITE_STORAGE_CODE);
                    }
                }
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_WRITE_STORAGE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    login(etEmail.getText().toString());
                } else {
                    showSnackbarNonSticky(svParent, getString(R.string.err_msg_permission_write_phone), true, ForgotPasswordActivity.this);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (asyncForgotPassword != null && asyncForgotPassword.getStatus() == AsyncTask.Status.RUNNING) {
            asyncForgotPassword.cancel(true);
        }
    }

    /**
     * Validate user input for further process
     *
     * @return true if fields are validate or false
     */
    private boolean validation() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            showSnackbarNonSticky(svParent, getString(R.string.err_msg_enter_client_id), true, ForgotPasswordActivity.this);
            etEmail.requestFocus();
            return false;
        } else if (!Utills.isValidEmail(etEmail.getText().toString())) {
            showSnackbarNonSticky(svParent, getString(R.string.err_msg_enter_valid_email_msg), true,this);
            etEmail.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    /**
     * API call for forgot password
     *
     * @param clientID
     */
    private void login(String clientID) {
        if (Utills.isNetworkAvailable(ForgotPasswordActivity.this) && !TextUtils.isEmpty(clientID)) {
            if (asyncForgotPassword != null && asyncForgotPassword.getStatus() == AsyncTask.Status.PENDING) {
                asyncForgotPassword.execute(clientID);
            } else if (asyncForgotPassword == null || asyncForgotPassword.getStatus() == AsyncTask.Status.FINISHED) {
                asyncForgotPassword = new AsyncForgotPassword();
                asyncForgotPassword.execute(clientID);
            }
        } else {
            Utills.showSnackbarNonSticky(svParent, getString(R.string.msg_no_internet), true, ForgotPasswordActivity.this);
        }
    }

    /**
     * AsyncTask for login
     */
    private class AsyncForgotPassword extends AsyncTask<String, Void, String> {
        private WSForgotPassword wsForgotPassword;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsForgotPassword = new WSForgotPassword(ForgotPasswordActivity.this);
            progressDialog = Utills.showProgressDialog(ForgotPasswordActivity.this, getString(R.string.msg_loading), false);
        }

        @Override
        protected String doInBackground(String... params) {
            return wsForgotPassword.executeService(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Utills.dismissProgressDialog(progressDialog);
            if (isCancelled()) {
                return;
            }
            if (wsForgotPassword != null && wsForgotPassword.getSucessNew().equalsIgnoreCase(success)) {
                Toast.makeText(ForgotPasswordActivity.this, wsForgotPassword.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
                finish();


            } else {
                showSnackbarNonSticky(svParent, wsForgotPassword != null ? "email is wrong" : null, true, ForgotPasswordActivity.this);

            }
        }
    }

}
