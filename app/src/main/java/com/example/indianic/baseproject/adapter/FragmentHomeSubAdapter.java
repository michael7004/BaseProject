package com.example.indianic.baseproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.model.AndroidVersion;

import java.util.ArrayList;

/**
 * FragmentHomeSubAdapter class created on 11/05/17.
 */

public class FragmentHomeSubAdapter extends RecyclerView.Adapter<FragmentHomeSubAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;

    public FragmentHomeSubAdapter(Context context, ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public FragmentHomeSubAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_fragment_home, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FragmentHomeSubAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_android.setText(android.get(i).getAndroid_version_name());
        Glide.with(context)
                .load(android.get(i).getAndroid_image_url())
                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.img_android);

    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_android;
        private ImageView img_android;

        public ViewHolder(View view) {
            super(view);
            tv_android = (TextView) view.findViewById(R.id.row_fragment_home_tv_title);
            img_android = (ImageView) view.findViewById(R.id.row_fragment_home_iv_logo);
        }
    }

}

