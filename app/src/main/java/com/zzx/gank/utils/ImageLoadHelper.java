package com.zzx.gank.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zzx.gank.R;

/**
 * Created by zuozhixiang on 17/10/27.
 */

public class ImageLoadHelper {
    private static ImageLoadHelper imageLoadHelper;

    private ImageLoadHelper() {
    }

    public static ImageLoadHelper get() {
        if (imageLoadHelper == null) {
            synchronized (ImageLoadHelper.class) {
                if (imageLoadHelper == null) {
                    imageLoadHelper = new ImageLoadHelper();
                }
            }
        }
        return imageLoadHelper;
    }

    public void displayImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.color.md_grey_300)
                .error(R.color.md_red_300)
                .animate(R.anim.fade_in)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public void displayImage(ImageView imageView, String url, int width, int height) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.color.md_grey_300)
                .error(R.color.md_red_300)
                .animate(R.anim.fade_in)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
