package com.XHWJ.xiaoAiTongXue.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.XHWJ.xiaoAiTongXue.utils.GlideUtils;


/**
 * Created by 宋宝春 on 2018/11/21.
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private Context context;

    public BaseRecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        //指定一个初始为8
        views = new SparseArray<>(8);
    }

    /**
     * 30      * 取得一个RecyclerHolder对象
     * 31      * @param context 上下文
     * 32      * @param itemView 子项
     * 33      * @return 返回一个RecyclerHolder对象
     * 34
     */
    public static BaseRecyclerViewHolder getRecyclerHolder(Context context, View itemView) {
        return new BaseRecyclerViewHolder(context, itemView);
    }

    public SparseArray<View> getViews() {
        return this.views;
    }

    /**
     * 通过view的id获取对应的控件，如果没有则加入views中
     *
     * @param viewId 控件的id
     * @return 返回一个控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 59      * 设置字符串
     * 60
     */
    public BaseRecyclerViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 68      * 设置图片
     * 69
     */
    public BaseRecyclerViewHolder setImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseRecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 86      * 设置图片
     * 87
     */
    public BaseRecyclerViewHolder setImageByUrl(int viewId, String url) {
        GlideUtils.loadImage(context, url, (ImageView) getView(viewId),null);
        return this;
    }
}
