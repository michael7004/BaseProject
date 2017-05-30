package com.example.indianic.baseproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.model.ProductSliderModel;

import java.util.ArrayList;

/**
 * FragmentHomeSubAdapter class created on 11/05/17.
 */

public class FragmentHomeSubAdapter extends RecyclerView.Adapter<FragmentHomeSubAdapter.ViewHolder> {
    private ArrayList<ProductSliderModel> productSliderModel;
    private Context context;

    public FragmentHomeSubAdapter(Context context, ArrayList<ProductSliderModel> productSliderModel) {
        this.productSliderModel = productSliderModel;
        this.context = context;
    }

    @Override
    public FragmentHomeSubAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_fragment_home, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FragmentHomeSubAdapter.ViewHolder viewHolder, final int i) {

//        viewHolder.tv_android.setText(ProductSliderModel.get(i).getA_psid());

        viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String url = productSliderModel.get(i).getA_pshref();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        Glide.with(context)
                .load("http://mosaicdesigns.in/assets/promotional-products/" + productSliderModel.get(i).getA_psid() + ".png")

//             .placeholder(R.drawable.loading_spinner)

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.img_android);

    }

    @Override
    public int getItemCount() {
        return productSliderModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        private TextView tv_android;
        private ImageView img_android;

        public ViewHolder(View view) {
            super(view);
//            tv_android = (TextView) view.findViewById(R.id.row_fragment_home_tv_title);
            img_android = (ImageView) view.findViewById(R.id.row_fragment_home_iv_logo);
        }
    }

}

