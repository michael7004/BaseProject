package com.example.indianic.baseproject.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.indianic.baseproject.R;

/**
 * CommonDialogPdfUnlockFragment class created on 11/05/17.
 */

public class CommonDialogPdfUnlockFragment extends DialogFragment {


    private ImageView ivProifile;

    public static CommonDialogPdfUnlockFragment newInstance() {
        CommonDialogPdfUnlockFragment f = new CommonDialogPdfUnlockFragment();

        return f;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_custom_pdf_unlock, container, false);
        final TextInputLayout inl = (TextInputLayout) v.findViewById(R.id.fragment_cmmn_d_pdf_unlock_til_exam_id);
        final EditText etExamId = (EditText) v.findViewById(R.id.fragment_cmmn_d_pdf_unlock_et_exam_id);
        final Button btnSubmit = (Button) v.findViewById(R.id.fragment_cmmn_d_pdf_unlock_bnt_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return v;
    }

}
