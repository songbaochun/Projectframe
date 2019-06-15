package com.XHWJ.xiaoAiTongXue.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.XHWJ.xiaoAiTongXue.utils.GlideUtils;


/**
 * Created by 宋宝春 on 2017/11/13.
 */

public class BaseListViewHolder {

    //现在对于int作为键的官方推荐用SparseArray替代HashMap
    private final SparseArray<View> views;
    private View convertView;
    private Context context;

    private BaseListViewHolder(Context context, ViewGroup parent, int itemLayoutId, int position) {
        this.context = context;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(itemLayoutId, parent, false);
        convertView.setTag(this);
    }

    /**
     * 拿到一个BaseListViewHolder对象
     */
    public static BaseListViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new BaseListViewHolder(context, parent, layoutId, position);
        }
        return (BaseListViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置字符串
     */
    public BaseListViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseListViewHolder setImageResource(int viewId, int drawableId) {
        GlideUtils.loadImage(context, drawableId, (ImageView) getView(viewId),null);
        return this;
    }

    /**
     * 设置图片为圆形
     */
    public BaseListViewHolder setCircleImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        GlideUtils.loadCircleImage(context, drawableId, iv,null);
        return this;
    }

    /**
     * 设置图片为圆形
     */
    public BaseListViewHolder setCircleImageUrl(int viewId, String drawableId) {
        ImageView iv = getView(viewId);
        GlideUtils.loadCircleImage(context, drawableId, iv,null);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseListViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseListViewHolder setImageByUrl(int viewId, String url) {
        GlideUtils.loadImage(context, url, (ImageView) getView(viewId),null);
        return this;
    }
}
