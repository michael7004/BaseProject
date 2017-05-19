package com.example.indianic.baseproject.adapter;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.common.CommonDialogVideoFragment;
import com.example.indianic.baseproject.fragment.MyDownloadsFragment;
import com.example.indianic.baseproject.model.OffLinePdfModel;

import java.io.File;
import java.util.ArrayList;

/**
 * FragmentPdfAdapter class created on 01/05/17.
 */

public class FragmentOffLinePdfAdapter extends RecyclerView.Adapter<FragmentOffLinePdfAdapter.MyViewHolder> {

    private ArrayList<OffLinePdfModel> offLinePdfModels;
    private Context context;
    private FragmentManager manager;


    public FragmentOffLinePdfAdapter(Context context, ArrayList<OffLinePdfModel> offLinePdfModels, FragmentManager manager) {
        this.context = context;
        this.offLinePdfModels = offLinePdfModels;
        this.manager = manager;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fragment_off_line_pdf, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvTitle.setText(offLinePdfModels.get(position).getTitle());
        holder.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (MyDownloadsFragment.isVideos) {
                    final FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    final DialogFragment newFragment = CommonDialogVideoFragment.newInstance();
                    newFragment.show(fragmentTransaction, "");
                } else {
                    File file = new File(offLinePdfModels.get(position).getPath());
                    Uri path = Uri.fromFile(file);
                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pdfOpenintent.setDataAndType(path, "application/pdf");
                    try {
                        context.startActivity(pdfOpenintent);
                    } catch (ActivityNotFoundException e) {

                    }
                }
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


                                File fdelete = new File(offLinePdfModels.get(position).getPath());
                                if (fdelete.exists()) {
                                    if (fdelete.delete()) {
                                        offLinePdfModels.remove(position);
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
        return offLinePdfModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView ivProfile;
        public ImageView ivMoreOption;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.row_fragment_off_line_pdf_tv_title);
            ivProfile = (ImageView) view.findViewById(R.id.row_fragment_off_line_pdf_iv_logo);
            ivMoreOption = (ImageView) view.findViewById(R.id.row_fragment_off_line_pdf_iv_more_option);

        }


    }


}

