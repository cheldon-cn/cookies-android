package com.cycle.util;

import android.widget.ImageView;

import com.cycle.GlideApp;

/**
 * @author cycle.member
 * @date 2018/12/23
 */
public class GlideUtil {
    private GlideUtil() {}

    public static void loadImage(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);
    }
}
