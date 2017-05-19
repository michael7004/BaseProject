package com.example.indianic.baseproject.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.model.PromotionalSliderModel;

import java.util.ArrayList;

/**
 * ViewPagerAdapter class created on 11/05/17.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mResources;
    ArrayList<PromotionalSliderModel> promotionalSliderModels;

    public ViewPagerAdapter(Context mContext, ArrayList<PromotionalSliderModel> promotionalSliderModels) {
        this.mContext = mContext;
        this.promotionalSliderModels = promotionalSliderModels;
    }

    @Override
    public int getCount() {
        if(promotionalSliderModels != null){
            return promotionalSliderModels.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);

        Glide.with(mContext)
                .load("http://mosaicdesigns.in/assets/promotional-banners/"+promotionalSliderModels.get(position).getA_psid()+".png")

//                .placeholder(R.drawable.loading_spinner)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
