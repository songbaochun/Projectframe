package com.XHWJ.xiaoAiTongXue.base;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import com.XHWJ.xiaoAiTongXue.MyApplication;
import com.XHWJ.xiaoAiTongXue.R;
import com.XHWJ.xiaoAiTongXue.constant.Constants;
import com.XHWJ.xiaoAiTongXue.utils.StringUtils;


/**
 * Created by 宋宝春 on 2018/11/6.
 */
public abstract class BaseFragment extends BasePermissionsFragment {
    //页面标签
    private static String TAG = "BaseFragment";
    private View view;
    public static final String KEY_TITLE = "title";
    //上下文对象
    protected Context context;
    //共享 公用数据
    //监听手机网络状态广播
    private NetWorkStatusReceiver networkChangeReceiver;
    /**
     * 判断当前的Fragment是否可见(相对于其他的Fragment)
     */
    protected boolean mIsVisible;

    /**
     * 标志位，标志已经初始化完成
     */
    protected boolean mIsprepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    protected boolean mHasLoadedOnce;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutResource(), null, false);
        }
        context = getActivity();
//        registerReceiver();
        mIsprepared = true;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!mIsprepared || !mIsVisible || mHasLoadedOnce) {
            return;
        }
        mHasLoadedOnce = true;
        initData();
    }

    /**
     * 获取设置的布局
     *
     * @return
     */
    protected View getContentView() {
        return view;
    }

    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(int id) {
        if (getContentView() != null) {
            return (T) getContentView().findViewById(id);
        } else {
            return (T) getActivity().findViewById(id);
        }
    }

    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int getLayoutResource();

    /**
     * 页面初始完成
     */
    protected abstract void initData();

    /**
     * 延迟加载
     * 子类必须重写此方法
     * 无网络或恢复网络时调用此方法
     */
    protected abstract void load();

    /**
     * 取值所用
     */
    public BaseFragment() {
        setArguments(new Bundle());
    }

    public BaseFragment setTitle(String title) {
        getArguments().putString(KEY_TITLE, title);
        return this;
    }

    public String getTitle() {
        return getArguments().getString(KEY_TITLE, "None");
    }

    public void transfer(Bundle bundle) {
        setArguments(bundle);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //设置Fragment的可见状态
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//getUserVisibleHint获取Fragment可见状态
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }

        if (isResumed()) {
            onVisibilityChangedToUser(isVisibleToUser);
        }
    }

    /**
     * 当Fragment对用户的可见性发生了改变的时候就会回调此方法
     *
     * @param isVisibleToUser true：用户能看见当前Fragment；false：用户看不见当前Fragment
     */
    public void onVisibilityChangedToUser(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            visibleToUser();
        } else {
            inVisibleToUser();
        }
    }

    /**
     * 用户可见
     */
    protected void visibleToUser() {
        registerReceiver();
    }

    /**
     * 用户不可见
     */
    protected void inVisibleToUser() {
        if (networkChangeReceiver != null) {
            context.unregisterReceiver(networkChangeReceiver);
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        load();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
        stopLoad();
    }

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }

    /**
     * 注册广播
     */
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetWorkStatusReceiver();
        context.registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (networkChangeReceiver != null) {
//            context.unregisterReceiver(networkChangeReceiver);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onVisibilityChangedToUser(false);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            onVisibilityChangedToUser(true);
        }
    }

    class NetWorkStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Context context1 = MyApplication.getContext();
            ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                //Toast.makeText(context, "当前网络可用", Toast.LENGTH_SHORT).show();
                load();
            } else {
                //Toast.makeText(context, "当前网络不可用", Toast.LENGTH_SHORT).show();
                load();
            }
        }
    }


    /**
     * 进入页面
     *
     * @param cla 启动的页面
     */
    public void enterPage(Class<?> cla) {
        if (cla == null) {
            return;
        }
        enterPage(cla, null);
    }


    /**
     * 进入页面
     *
     * @param cla    要启动的页面
     * @param bundle 要传递的参数
     */
    public void enterPage(Class<?> cla, Bundle bundle) {
        if (cla == null) {
            return;
        }
        enterPageForResult(cla, bundle, 0);
    }

    /**
     * 进入页面
     *
     * @param cla
     * @param requestCode 请求码
     */
    public void enterPageForResult(Class<?> cla, int requestCode) {
        if (cla == null) {
            return;
        }
        enterPageForResult(cla, null, requestCode);
    }

    /**
     * 进入页面
     *
     * @param cla
     * @param bundle
     * @param requestCode 请求码
     */
    public void enterPageForResult(Class<?> cla, Bundle bundle, int requestCode) {
        if (cla == null) {
            return;
        }
        Intent intent = new Intent(context, cla);
        if (bundle != null) {
            intent.putExtra(Constants.START_ACTIVITY_BUNDLE_KEY, bundle);
        }
        if (requestCode > 0) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 进入内部浏览器页面
     */
    public void enterBrowserPage(String uri) {
        enterBrowserPage(null, uri);
    }

    /**
     * 进入内部浏览器页面
     */
    public void enterBrowserPage(Bundle bundle, String uri) {
        enterBrowserPage(bundle, uri, 0);
    }

    /**
     * 进入内部浏览器页面
     *
     * @param requestCode 请求码
     */
    public void enterBrowserPage(Bundle bundle, String uri, int requestCode) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtra(Constants.START_ACTIVITY_BUNDLE_KEY, bundle);
        }
        if (StringUtils.isNotEmpty(uri)) {
            intent.setData(Uri.parse(uri));
        }
        if (requestCode > 0) {
            startActivityForResult(intent, requestCode);
        } else {
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException a) {
                a.getMessage();
            }
        }
        getActivity().overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }


    /**
     * 显示软键盘
     */
    public void openkeybord() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /**
     * 隐藏软键盘
     */
    public void hiddenKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    //快速点击的开始时间
    private long startClickTime;

    //是否疯狂快速点击
    public boolean isSoFastClick() {
        long curruntClickTime = SystemClock.uptimeMillis();
        if (startClickTime <= 0) {
            startClickTime = SystemClock.uptimeMillis();
            return false;
        } else {
            if (curruntClickTime - startClickTime < 1000) {
                startClickTime = 0L;
                return true;
            } else {
                return false;
            }
        }
    }


}
