package com.example.indianic.baseproject.adapter;

import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.indianic.baseproject.common.CommonDialogVideoFragment;
import com.example.indianic.baseproject.fragment.MyVideosFragment;
import com.example.indianic.baseproject.model.VideosListModel;

import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * FragmentVideosAdapter class created on 05/05/17.
 */

public class FragmentVideosAdapter extends RecyclerView.Adapter<FragmentVideosAdapter.MyViewHolder> {

    private ArrayList<VideosListModel> videosListModels;


    private Context context;
    private FragmentManager manager;
    private String isUnlock = "Unlock";
    private DownloadManager downloadManager;
    private long Image_DownloadId, Music_DownloadId;


    public FragmentVideosAdapter(Context context, ArrayList<VideosListModel> videosListModels, FragmentManager manager) {
        this.context = context;
        this.videosListModels = videosListModels;
        this.manager = manager;
        //set filter to only when download is complete and register broadcast receiver
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(downloadReceiver, filter);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fragment_video, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.tvTitle.setText(videosListModels.get(position).getFiletitle());
        holder.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String VideoURL = "http://mosaicdesigns.in/assets/videodownloads/" + videosListModels.get(position).getVdid() + ".mp4";
                if (MyVideosFragment.isVideos) {
                    final FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    final DialogFragment newFragment = CommonDialogVideoFragment.newInstance();
                    Bundle args = new Bundle();
                    args.putString("path", VideoURL);
                    newFragment.setArguments(args);
                    newFragment.show(fragmentTransaction, "");
                } else {
                    String pdf = "http://mosaicdesigns.in/assets/videodownloads/32.pdf";
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
                    context.startActivity(browserIntent);
                }

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
                                if (MyVideosFragment.isVideos) {
                                    Toast.makeText(context, "Video Download started", Toast.LENGTH_SHORT).show();
                                    String VideoURL = "http://mosaicdesigns.in/assets/videodownloads/" + videosListModels.get(position).getVdid() + ".mp4";
                                    Uri music_uri = Uri.parse(VideoURL);
                                    Music_DownloadId = DownloadData(music_uri, videosListModels.get(position).getVdid());
                                } else {
                                    Toast.makeText(context, "PDF Download started", Toast.LENGTH_SHORT).show();
                                    Uri image_uri = Uri.parse("http://mosaicdesigns.in/assets/videodownloads/32.pdf");
                                    Image_DownloadId = DownloadData(image_uri, videosListModels.get(position).getVdid());
                                }
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

    @Override
    public int getItemCount() {
        return videosListModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView ivProfile;
        public ImageView ivMoreOption;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.row_fragment_video_tv_title);
            ivProfile = (ImageView) view.findViewById(R.id.row_fragment_video_iv_logo);
            ivMoreOption = (ImageView) view.findViewById(R.id.row_fragment_video_iv_more_option);
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
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, id + ".mp44");


        else {
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "mosaic.pdf");
        }
        //Enqueue download and save the referenceId
        downloadReference = downloadManager.enqueue(request);
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
            }

        }
    };


}

