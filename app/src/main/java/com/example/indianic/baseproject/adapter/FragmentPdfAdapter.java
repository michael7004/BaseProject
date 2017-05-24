package com.example.indianic.baseproject.adapter;

import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.common.CommonDialogPdfLibFragment;
import com.example.indianic.baseproject.common.CommonDialogPdfUnlockFragment;
import com.example.indianic.baseproject.model.MyPdfModel;
import com.example.indianic.baseproject.utills.Utills;

import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * FragmentPdfAdapter class created on 01/05/17.
 */

public class FragmentPdfAdapter extends RecyclerView.Adapter<FragmentPdfAdapter.MyViewHolder> {

//    http://mosaicdesigns.in/assets/pdfdownloads/6.pdf

    private ArrayList<MyPdfModel> arrayListMyPdf;
    private Context context;
    private FragmentManager manager;
    private String isUnlock = "Unlock";
    private DownloadManager downloadManager;
    private long Pdf_DownloadId;
    private long Pdf_DownloadNormal;
    boolean isComplete = false;
    private String Global_Postion;
    private ProgressDialog progressDialog;


    public FragmentPdfAdapter(Context context, ArrayList<MyPdfModel> arrayListMyPdf, FragmentManager manager) {
        this.context = context;
        this.arrayListMyPdf = arrayListMyPdf;
        this.manager = manager;
        //set filter to only when download is complete and register broadcast receiver
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(downloadReceiver, filter);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fragment_pdf, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (arrayListMyPdf.get(position).getDlprice().equalsIgnoreCase("0")) {

            holder.tvTitle.setText(arrayListMyPdf.get(position).getFiletitle());
            holder.ivProfile.setBackground(context.getResources().getDrawable(R.drawable.ic_pdf));

            holder.ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri image_uri = Uri.parse("http://mosaicdesigns.in/assets/pdfdownloads/" + arrayListMyPdf.get(position).getDid() + ".pdf");
                    Global_Postion = arrayListMyPdf.get(position).getDid() + ".pdff";
                    Pdf_DownloadId = DownloadData(image_uri, arrayListMyPdf.get(position).getDid());


                }
            });


            holder.ivMoreOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //creating a popup menu

                    PopupMenu popup = new PopupMenu(context, holder.ivMoreOption);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.options_menu_download);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.menu1:
                                    //handle menu1 click
                                    Uri image_uri_normal = Uri.parse("http://mosaicdesigns.in/assets/pdfdownloads/" + arrayListMyPdf.get(position).getDid() + ".pdf");
                                    Global_Postion = arrayListMyPdf.get(position).getDid() + ".pdff";
                                    Pdf_DownloadNormal = DownloadDataNormal(image_uri_normal, arrayListMyPdf.get(position).getDid());
                                    break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();

                }
            });

        } else {
            holder.tvTitle.setText(arrayListMyPdf.get(position).getFiletitle());
            holder.ivProfile.setBackground(context.getResources().getDrawable(R.drawable.ic_lock_red));
            holder.ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utills.displayDialog(context, "You should unlock this file");
                }
            });


            holder.ivMoreOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //creating a popup menu

                    PopupMenu popup = new PopupMenu(context, holder.ivMoreOption);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.options_menu_pdf_unlock);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.menu_pdf_unlock:
                                    //handle menu1 click
                                    final FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                    final DialogFragment newFragment = CommonDialogPdfUnlockFragment.newInstance();
                                    newFragment.show(fragmentTransaction, "");

                                    break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();

                }
            });


        }


    }


    @Override
    public int getItemCount() {
        return arrayListMyPdf.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView ivProfile;
        public ImageView ivMoreOption;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.row_fragment_pdf_tv_title);
            ivProfile = (ImageView) view.findViewById(R.id.row_fragment_pdf_iv_logo);
            ivMoreOption = (ImageView) view.findViewById(R.id.row_fragment_pdf_iv_more_option);
        }


    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (referenceId == Pdf_DownloadId) {
                Utills.dismissProgressDialog(progressDialog);
                final FragmentTransaction fragmentTransaction = manager.beginTransaction();
                final DialogFragment newFragment = CommonDialogPdfLibFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("path", "/sdcard/Android/data/com.example.indianic.baseproject/files/Documents/" + Global_Postion);
                newFragment.setArguments(args);
                newFragment.show(fragmentTransaction, "");
            } else {
//                Toast toast = Toast.makeText(context,
//                        "PDF Download Completed", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 25, 400);
//                toast.show();
            }

        }
    };

    private long DownloadData(Uri uri, String id) {
        long downloadReference;
        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //Setting title of request
        request.setTitle("Data Download");
        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOCUMENTS, id + ".pdff");
        //Enqueue download and save the referenceId
        downloadReference = downloadManager.enqueue(request);
        progressDialog = Utills.showProgressDialog(context, context.getString(R.string.msg_loading), true);
        return downloadReference;
    }

    private long DownloadDataNormal(Uri uri, String id) {
        long downloadReference;
        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //Setting title of request
        request.setTitle("Data Download");
        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, id + ".pdff");
        //Enqueue download and save the referenceId
        downloadReference = downloadManager.enqueue(request);
        return downloadReference;
    }
}

