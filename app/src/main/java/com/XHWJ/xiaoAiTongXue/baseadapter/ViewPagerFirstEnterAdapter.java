package com.XHWJ.xiaoAiTongXue.baseadapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.XHWJ.xiaoAiTongXue.callback.ClickEventCallbackInterface;

import java.util.List;

/***
 * 导航页适配器
 */
public class ViewPagerFirstEnterAdapter extends PagerAdapter {

    private List<View> viewList;
    private ViewPager viewPager;
    private ClickEventCallbackInterface clickEventCallbackInterface;

    public ViewPagerFirstEnterAdapter(ViewPager viewPager, List<View> viewList) {
        this.viewPager = viewPager;
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, final int position, Object object) {
        final View view = viewList.get(position);
        ((ViewPager) container).removeView(view);

    }

    @Override
    public Object instantiateItem(View container, final int position) {
        final View view = viewList.get(position);
        ((ViewPager) container).addView(view, 0);
        if (position == viewList.size() - 1) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickEventCallbackInterface.onClickListener(view, position);
                }
            });
        }
        return viewList.get(position);
    }

    public void setClickEventCallbackInterface(ClickEventCallbackInterface clickEventCallbackInterface) {
        this.clickEventCallbackInterface = clickEventCallbackInterface;

    }
}
