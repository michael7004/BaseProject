package com.example.indianic.baseproject.adapter;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.common.CommonDialogFragment;
import com.example.indianic.baseproject.common.CommonDialogVideoFragment;
import com.example.indianic.baseproject.fragment.MyDownloadsFragment;

import java.util.ArrayList;

/**
 * FragmentPdfAdapter class created on 01/05/17.
 */

public class FragmentOffLineAdapter extends RecyclerView.Adapter<FragmentOffLineAdapter.MyViewHolder> {

    private ArrayList<String> horizontalList;
    private Context context;
    private FragmentManager manager;


    public FragmentOffLineAdapter(Context context, ArrayList<String> horizontalList, FragmentManager manager) {
        this.context = context;
        this.horizontalList = horizontalList;
        this.manager=manager;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fragment_off_line, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvTitle.setText(horizontalList.get(position));
        holder.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(MyDownloadsFragment.isVideos) {
                    final FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    final DialogFragment newFragment = CommonDialogVideoFragment.newInstance();
                    newFragment.show(fragmentTransaction, "");
                }
                else
                {
                    final FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    final DialogFragment newFragment = CommonDialogFragment.newInstance();
                    newFragment.show(fragmentTransaction, "");
                }
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
                                Toast.makeText(context, "This functionality not implemented yet from menu 1=" + position, Toast.LENGTH_SHORT).show();
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
        return horizontalList.size();
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

