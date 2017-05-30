package com.example.indianic.baseproject.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.utills.Utills;
import com.example.indianic.baseproject.webservice.WSExamId;

/**
 * CommonDialogPdfUnlockFragment class created on 11/05/17.
 */

public class CommonDialogPdfUnlockFragment extends DialogFragment {


    private ImageView ivProifile;
    private EditText etExamId;
    private RelativeLayout relativeLayout;
    private AsyncCheckExamID asyncLogin;
    private String sucessMsg="Success";

    public static CommonDialogPdfUnlockFragment newInstance() {
        CommonDialogPdfUnlockFragment f = new CommonDialogPdfUnlockFragment();

        return f;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

//        // the content
//        final RelativeLayout root = new RelativeLayout(getActivity());
//        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(root);

//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_custom_pdf_unlock, container, false);
        final TextInputLayout inl = (TextInputLayout) v.findViewById(R.id.fragment_cmmn_d_pdf_unlock_til_exam_id);
        etExamId = (EditText) v.findViewById(R.id.fragment_cmmn_d_pdf_unlock_et_exam_id);
        relativeLayout= (RelativeLayout) v.findViewById(R.id.dialog_custom_pdf_unlock_rl_container);
        final Button btnSubmit = (Button) v.findViewById(R.id.fragment_cmmn_d_pdf_unlock_bnt_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validation())
                {
                    CheckExamId(etExamId.getText().toString());
                }
                else {
                    Utills.showSnackbarNonSticky(relativeLayout,"Enter exam id ", true,getActivity());
                }

            }
        });


        return v;
    }

    private void CheckExamId(String id) {

        if (Utills.isNetworkAvailable(getActivity())) {
            if (asyncLogin != null && asyncLogin.getStatus() == AsyncTask.Status.PENDING) {
                asyncLogin.execute(id);
            } else if (asyncLogin == null || asyncLogin.getStatus() == AsyncTask.Status.FINISHED) {
                asyncLogin = new AsyncCheckExamID();
                asyncLogin.execute(id,etExamId.getText().toString().trim());
            }
        } else {
            Utills.showSnackbarNonSticky(relativeLayout, getString(R.string.msg_no_internet), true, getActivity());
        }
    }

    /**
     * Validate user input for further process
     *
     * @return true if fields are validate or false
     */
    private boolean validation() {
        if (TextUtils.isEmpty(etExamId.getText().toString())) {
            Utills.showSnackbarNonSticky(relativeLayout,"Enter exam id ", true,getActivity());
            etExamId.requestFocus();
            return false;
        }  else {
            return true;
        }
    }

    /**
     * AsyncTask for login
     */
    private class AsyncCheckExamID extends AsyncTask<String, Void, String> {
        private WSExamId wsID;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wsID = new WSExamId(getActivity());
            progressDialog = Utills.showProgressDialog(getActivity(), getString(R.string.msg_loading), false);
        }

        @Override
        protected String doInBackground(String... params) {
            return wsID.executeService(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Utills.dismissProgressDialog(progressDialog);
            if (isCancelled()) {
                return;
            }
            if (wsID != null && wsID.getSuccess().equalsIgnoreCase(sucessMsg)) {
//                Utills.showSnackbarNonSticky(relativeLayout, wsID != null ? wsID.getMessage() : null, true, getActivity());
                Toast.makeText(getActivity(), wsID.getMessage(), Toast.LENGTH_SHORT).show();
                dismiss();


            } else {
                Toast.makeText(getActivity(), "Exam id not found", Toast.LENGTH_SHORT).show();
//                Utills.showSnackbarNonSticky(relativeLayout, wsID != null ? "Exam id not found" : null, true, getActivity());
            }
        }
    }


}
