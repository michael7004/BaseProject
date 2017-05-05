package com.example.indianic.baseproject.common;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.example.indianic.baseproject.R;

import java.util.Hashtable;

public class FontCache {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        if (name == null || TextUtils.isEmpty(name)) {
            name = context.getString(R.string.font_open_sans);
        }
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + name);
//            	if (tf.isBold()) {
//    				tf = Typeface.createFromAsset(context.getAssets(), name + "_bold.ttf");
//    			} else {
//    				tf = Typeface.createFromAsset(context.getAssets(), name + "_regular.ttf");
//				}
            } catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
     }
}