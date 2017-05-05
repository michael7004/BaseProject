package com.example.indianic.baseproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indianic.baseproject.R;

import java.util.ArrayList;

/**
 * FragmentPdfAdapter class created on 01/05/17.
 */

public class FragmentOffLineAdapter extends RecyclerView.Adapter<FragmentOffLineAdapter.MyViewHolder> {

    private ArrayList<String> horizontalList;
    private Context context;


    public FragmentOffLineAdapter(Context context, ArrayList<String> horizontalList) {
        this.context = context;
        this.horizontalList = horizontalList;
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
                Toast.makeText(context, "This functionality not implemented yet" + position, Toast.LENGTH_SHORT).show();
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

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.row_fragment_off_line_tv_title);
            ivProfile = (ImageView) view.findViewById(R.id.row_fragment_off_line_iv_logo);

        }


    }


}

