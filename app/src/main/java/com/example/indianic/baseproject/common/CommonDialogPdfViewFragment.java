package com.example.indianic.baseproject.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.indianic.baseproject.R;

/**
 * CommonDialogFragment class created on 10/05/17.
 */

 public  class CommonDialogPdfViewFragment extends DialogFragment {


    private ImageView ivProifile;
    private WebView webView;
    @SuppressLint("SetJavaScriptEnabled")

    public static CommonDialogPdfViewFragment newInstance() {
        CommonDialogPdfViewFragment f = new CommonDialogPdfViewFragment();
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
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_common_dialog_pdf_view, container, false);
        final WebView wvPdf = (WebView) v.findViewById(R.id.fragment_cmnn_dl_vb_container);
        wvPdf.getSettings().setJavaScriptEnabled(true);
        String pdf = "http://mosaicdesigns.in/assets/videodownloads/32.pdf";
        wvPdf.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

        return v;
    }

}

