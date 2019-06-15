package com.XHWJ.xiaoAiTongXue.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.XHWJ.xiaoAiTongXue.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.XHWJ.xiaoAiTongXue.MyApplication;
import com.example.network.GlideApp;

import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

/**
 * Created by lenovo on 2017/7/23.
 */
public class GlideUtils {

    /**
     * 封装glide加载图片
     *
     * @param url
     * @param imageView
     */
    public static void loadCircleImage(Context context, Object url, ImageView imageView, Drawable defaultImage) {
        if (context == null) {
            context = MyApplication.getContext();
        }
        //todo 需要改进一下
        RequestOptions options = new RequestOptions()
                .bitmapTransform(new CropSquareTransformation())
                .placeholder(defaultImage)
                .error(defaultImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(imageView);


    }

    public static void loadImage(Context context, Object url, ImageView imageView, Drawable defaultImage) {
        if (context == null) {
            context = MyApplication.getContext();
        }
        RequestOptions options = new RequestOptions()
                .placeholder(defaultImage)
                .error(defaultImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        //加载图片
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }


    public static void loadFilletImage(Context context, Object url,
                                       int radius, int margin, ImageView imageView, Drawable defaultImage) {
        if (context == null) {
            context = MyApplication.getContext();
        }
        RequestOptions options = new RequestOptions()
                .bitmapTransform(new RoundedCornersTransformation(radius, margin, RoundedCornersTransformation.CornerType.ALL))
                .placeholder(defaultImage)
                .error(defaultImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(url)
                .apply(options).into(imageView);


        GlideApp.with(context)
                .load(url)
                //设置加载失败后的图片显示
                .error(defaultImage)
                .centerCrop()
                //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                .skipMemoryCache(false)
                //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //设置图片加载的优先级
                .priority(Priority.HIGH)
                .transform(new RoundedCornersTransformation(radius, margin, RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void loadThumbnail(Context context, Object url,
                                     float thumbnail, ImageView imageView, Drawable defaultImage) {
        if (context == null) {
            context = MyApplication.getContext();
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(defaultImage)
                .error(defaultImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .thumbnail(thumbnail)
                .apply(options)
                .into(imageView);
    }

}
