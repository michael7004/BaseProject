package com.example.indianic.baseproject.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.OffLineVideoPlayerActivity;
import com.example.indianic.baseproject.model.OffLineVideoModel;

import java.io.File;
import java.util.ArrayList;

/**
 * FragmentPdfAdapter class created on 01/05/17.
 */

public class FragmentOffLineVideoAdapter extends RecyclerView.Adapter<FragmentOffLineVideoAdapter.MyViewHolder> {

    private ArrayList<OffLineVideoModel> offLineVideoModels;
    private Context context;
    private FragmentManager manager;
    private Fragment fragment;


    public FragmentOffLineVideoAdapter(Context context, ArrayList<OffLineVideoModel> offLineVideoModels, FragmentManager manager, Fragment fragment) {
        this.context = context;
        this.offLineVideoModels = offLineVideoModels;
        this.manager = manager;
        this.fragment = fragment;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fragment_off_line_video, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvTitle.setText(offLineVideoModels.get(position).getTitle());
        holder.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String VideoURL = offLineVideoModels.get(position).getPath();
                Intent intent = new Intent(context, OffLineVideoPlayerActivity.class);
                intent.putExtra("VID_PATH_OFF_LINE", VideoURL);
                context.startActivity(intent);

//
//                if (MyDownloadsFragment.isVideos) {
////
////                    final FragmentTransaction fragmentTransaction = manager.beginTransaction();
////                    final DialogFragment newFragment = CommonDialogVideoFragment.newInstance();
////                    Bundle args = new Bundle();
////                    args.putString("path", offLineVideoModels.get(position).getPath());
////                    newFragment.setArguments(args);
////                    newFragment.show(fragmentTransaction, "");
//
//                    String VideoURL = offLineVideoModels.get(position).getPath();
//                    Intent intent = new Intent(context, OffLineVideoPlayerActivity.class);
//                    intent.putExtra("VID_PATH_OFF_LINE", VideoURL);
//                    context.startActivity(intent);
//
//
//                } else {
//                    final FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                    final DialogFragment newFragment = CommonDialogFragment.newInstance();
//                    newFragment.show(fragmentTransaction, "");
//                }
            }
        });
        holder.ivMoreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.ivMoreOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu_delete);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                File fdelete = new File(offLineVideoModels.get(position).getPath());
                                if (fdelete.exists()) {
                                    if (fdelete.delete()) {

                                        offLineVideoModels.remove(position);
                                        notifyDataSetChanged();


                                    } else {

                                    }
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
        return offLineVideoModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView ivProfile;
        public ImageView ivMoreOption;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.row_fragment_off_line_tv_title);
            ivProfile = (ImageView) view.findViewById(R.id.row_fragment_off_line_iv_logo);
            ivMoreOption = (ImageView) view.findViewById(R.id.row_fragment_off_line_iv_more_option);

        }


    }


}

