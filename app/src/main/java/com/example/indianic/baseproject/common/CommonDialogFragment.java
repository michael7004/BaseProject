package com.example.indianic.baseproject.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.indianic.baseproject.R;

/**
 * CommonDialogFragment class created on 10/05/17.
 */

 public  class  CommonDialogFragment extends DialogFragment {


    private ImageView ivProifile;

    public static CommonDialogFragment newInstance() {
        CommonDialogFragment f = new CommonDialogFragment();
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
//        ivProifile = (ImageView) v.findViewById(R.id.fragment_dialog_iv_profile);
//        final String imagePathpart = jsonobjectToChange.optString(TAG_IMAGE);
//        Glide.with(this)
//                .load(imagePathpart).diskCacheStrategy(DiskCacheStrategy.NONE).fitCenter()
//                .skipMemoryCache(true)
//                .placeholder(R.drawable.pf_pic)
//                .into(ivProifile);


        return v;
    }

}

