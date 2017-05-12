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

 public  class CommonDialogPdfDownloadFragment extends DialogFragment {


    private ImageView ivProifile;

    public static CommonDialogPdfDownloadFragment newInstance() {
        CommonDialogPdfDownloadFragment f = new CommonDialogPdfDownloadFragment();
        return f;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_common_dialog_pdf_download, container, false);
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

