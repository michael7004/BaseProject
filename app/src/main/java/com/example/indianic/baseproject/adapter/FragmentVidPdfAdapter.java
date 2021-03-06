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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.common.CommonDialogPdfLibFragment;
import com.example.indianic.baseproject.fragment.MyVideosFragment;
import com.example.indianic.baseproject.model.VidPdfListModel;
import com.example.indianic.baseproject.utills.Utills;

import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * FragmentVideosAdapter class created on 05/05/17.
 */

public class FragmentVidPdfAdapter extends RecyclerView.Adapter<FragmentVidPdfAdapter.MyViewHolder> {

//    http://mosaicdesigns.in/assets/videodownloads/32.pdf

    private ArrayList<VidPdfListModel> vidPdfListModels;
    private Context context;
    private FragmentManager manager;
    private String isUnlock = "Unlock";
    private DownloadManager downloadManager;
    private long Image_DownloadId, Music_DownloadId;
    private long Pdf_DownloadId;
    private ProgressDialog progressDialog;
    private String Global_Postion;


    public FragmentVidPdfAdapter(Context context, ArrayList<VidPdfListModel> vidPdfListModels, FragmentManager manager) {
        this.context = context;
        this.vidPdfListModels = vidPdfListModels;
        this.manager = manager;
        //set filter to only when download is complete and register broadcast receiver
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(downloadReceiver, filter);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fragment_vid_pdf, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.tvTitle.setText(vidPdfListModels.get(position).getFiletitle());
        if(vidPdfListModels.get(position).getCommonvideo().equalsIgnoreCase("")) {
            holder.ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri image_uri = Uri.parse("http://mosaicdesigns.in/assets/videodownloads/" + vidPdfListModels.get(position).getVdid() + ".pdf");
                    Global_Postion = vidPdfListModels.get(position).getVdid() + ".pdff";
                    Pdf_DownloadId = DownloadDatanPatch(image_uri, vidPdfListModels.get(position).getVdid());

                }
            });


            holder.ivMoreOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(context, holder.ivMoreOption);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.options_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu2:
                                    //handle menu1 click
                                    Toast.makeText(context, "PDF Download started", Toast.LENGTH_SHORT).show();
                                    Uri image_uri = Uri.parse("http://mosaicdesigns.in/assets/videodownloads/" + vidPdfListModels.get(position).getVdid() + ".pdf");
                                    Image_DownloadId = DownloadData(image_uri, vidPdfListModels.get(position).getVdid());
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
        else
        {
            holder.ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri image_uri = Uri.parse("http://mosaicdesigns.in/assets/videodownloads/" + vidPdfListModels.get(position).getCommonvideo() + ".pdf");
                    Global_Postion = vidPdfListModels.get(position).getVdid() + ".pdff";
                    Pdf_DownloadId = DownloadDatanPatch(image_uri, vidPdfListModels.get(position).getVdid());

                }
            });


            holder.ivMoreOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(context, holder.ivMoreOption);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.options_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu2:
                                    //handle menu1 click
                                    Toast.makeText(context, "PDF Download started", Toast.LENGTH_SHORT).show();
                                    Uri image_uri = Uri.parse("http://mosaicdesigns.in/assets/videodownloads/" + vidPdfListModels.get(position).getCommonvideo() + ".pdf");
                                    Image_DownloadId = DownloadData(image_uri, vidPdfListModels.get(position).getVdid());
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
        return vidPdfListModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView ivProfile;
        public ImageView ivMoreOption;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.row_fragment_vid_pdf_tv_title);
            ivProfile = (ImageView) view.findViewById(R.id.row_fragment_vid_pdf_iv_logo);
            ivMoreOption = (ImageView) view.findViewById(R.id.row_fragment_vid_pdf_iv_more_option);
        }


    }

    private long DownloadData(Uri uri, String id) {
        long downloadReference;
        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //Setting title of request
        request.setTitle("Data Download");
        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");
        //Set the local destination for the downloaded file to a path within the application's external files directory
        if (MyVideosFragment.isVideos)
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "mosaic.mp4");
        else {
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, id + ".pdff");
        }
        //Enqueue download and save the referenceId
        downloadReference = downloadManager.enqueue(request);
        return downloadReference;
    }

    private long DownloadDatanPatch(Uri uri, String id) {
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


    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (referenceId == Image_DownloadId) {
                Toast toast = Toast.makeText(context, "PDF Download Complete", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else if (referenceId == Music_DownloadId) {
                Toast toast = Toast.makeText(context, "Video Download Complete", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else if (referenceId == Pdf_DownloadId) {
                Utills.dismissProgressDialog(progressDialog);
                final FragmentTransaction fragmentTransaction = manager.beginTransaction();
                final DialogFragment newFragment = CommonDialogPdfLibFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("path", "/sdcard/Android/data/com.example.indianic.baseproject/files/Documents/" + Global_Postion);
                newFragment.setArguments(args);
                newFragment.show(fragmentTransaction, "");
            }

        }
    };


}

