package com.XHWJ.xiaoAiTongXue.baseadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.XHWJ.xiaoAiTongXue.base.BaseFragment;

import java.util.List;

/**
 * 碎片fragment的适配器
 */
public class BaseFragmentViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public BaseFragmentViewPagerAdapter( FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

}
