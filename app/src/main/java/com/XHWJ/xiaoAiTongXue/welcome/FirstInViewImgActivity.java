package com.XHWJ.xiaoAiTongXue.welcome;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.XHWJ.xiaoAiTongXue.MainActivity;
import com.XHWJ.xiaoAiTongXue.R;
import com.XHWJ.xiaoAiTongXue.baseadapter.ViewPagerFirstEnterAdapter;
import com.XHWJ.xiaoAiTongXue.base.BaseActivity;
import com.XHWJ.xiaoAiTongXue.callback.ClickEventCallbackInterface;
import com.XHWJ.xiaoAiTongXue.utils.AccountUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 宋宝春 on 2018/11/7.
 * 导航页
 */

public class FirstInViewImgActivity extends BaseActivity implements ClickEventCallbackInterface, ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private TextView tv_start_btn;
    private ImageView im_start_btn;
    private List<View> list = new ArrayList<>();  //导航图片的数据源

    @Override
    public int getLayoutResource() {
        return R.layout.activity_first_in_view_img;
    }

    @Override
    public void initData() {
        mViewPager = findViewById(R.id.vp_enter_first);
        tv_start_btn = findViewById(R.id.tv_start_btn);
        im_start_btn = findViewById(R.id.im_start_btn);

        tv_start_btn.setText("跳过");
        tv_start_btn.setOnClickListener(this);
        im_start_btn.setOnClickListener(this);
        setFirstlnData();
        setAdapter();

    }

    @Override
    protected void load() {

    }

    private void setAdapter() {
        ViewPagerFirstEnterAdapter adapter = new ViewPagerFirstEnterAdapter(mViewPager, list);
        mViewPager.setAdapter(adapter);
        adapter.setClickEventCallbackInterface(this);
        mViewPager.setOnPageChangeListener(this);
    }

    private void setFirstlnData() {
        LayoutInflater.from(context).inflate(R.layout.boot_page_one, null, false);
        list.add(LayoutInflater.from(context).inflate(R.layout.boot_page_one, null, false));
        list.add(LayoutInflater.from(context).inflate(R.layout.boot_page_two, null, false));
        list.add(LayoutInflater.from(context).inflate(R.layout.boot_page_three, null, false));
        list.add(LayoutInflater.from(context).inflate(R.layout.boot_page_for, null, false));


    }

    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public void onLongClickListener(View view, int position) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 3) {
            im_start_btn.setVisibility(View.VISIBLE);
            tv_start_btn.setVisibility(View.GONE);
        } else {
            im_start_btn.setVisibility(View.GONE);
            tv_start_btn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_btn:
            case R.id.im_start_btn:
                //更改第一次进入app的状态
                if (AccountUtils.getInstance(context).getFirstApp()) {
                    AccountUtils.getInstance(context).setFirstApp(false);
                }
                if (AccountUtils.getInstance(context).getIsLogin()) {
                    //进入主页
                    enterPage(MainActivity.class);
                } else {
                    //登录页
//                    enterPage(LoginActivity.class);
                }
                finish();
                break;
        }
    }
}
