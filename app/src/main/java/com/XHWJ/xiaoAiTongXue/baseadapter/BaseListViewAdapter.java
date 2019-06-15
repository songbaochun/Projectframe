package com.XHWJ.xiaoAiTongXue.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 宋宝春 on 2017/11/13.
 * 多功能适配器类
 */

public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> list;
    private LayoutInflater inflater;
    private int itemLayoutId;

    public BaseListViewAdapter(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseListViewHolder holder = getBaseListViewHolder(position, convertView, parent);
        convert(holder, (T) getItem(position), position);
        return holder.getConvertView();
    }

    public abstract void convert(BaseListViewHolder holder, T item, int position);

    private BaseListViewHolder getBaseListViewHolder(int position, View convertView, ViewGroup parent) {
        return BaseListViewHolder.get(context, convertView, parent, itemLayoutId, position);
    }

    public void upData(List<T> newlist) {
        list = newlist;
        notifyDataSetChanged();
    }
}
