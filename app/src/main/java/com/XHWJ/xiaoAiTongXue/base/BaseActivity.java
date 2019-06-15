package com.XHWJ.xiaoAiTongXue.base;

import android.app.Activity;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.XHWJ.xiaoAiTongXue.MyApplication;
import com.XHWJ.xiaoAiTongXue.R;
import com.XHWJ.xiaoAiTongXue.constant.Constants;
import com.XHWJ.xiaoAiTongXue.utils.ActivityManager;
import com.XHWJ.xiaoAiTongXue.utils.LogUtils;
import com.XHWJ.xiaoAiTongXue.utils.StringUtils;
import com.example.network.utils.UploadPromptDialoge;


/**
 * Created by 宋宝春 on 2018/11/6.
 */
public abstract class BaseActivity extends BasePermissionsActivity {
    //页面标签
    private static String TAG = "BaseActivity";
    //上下文对象
    protected Context context;
    //网络的异常的广播
    private NetWorkStatusReceiver networkChangeReceiver;
    //快速点击的开始时间
    private long startClickTime;
    private UploadPromptDialoge uploadPromptDialoge;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
//        todo 支付宝的沙箱环境支付 正式上线是需要删掉
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //填充布局
        View inflate = LayoutInflater.from(this).inflate(getLayoutResource(), null, false);
        setContentView(inflate);
        //初始化数据
        context = this;
        //初始化
        initData();
        //监听手机网络状态
        netWorkState();
        ActivityManager.getInstance().addStackActivity(this);
    }

    private void netWorkState() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetWorkStatusReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    //加载布局
    public abstract int getLayoutResource();

    //初始化数据
    public abstract void initData();

    //加载数据
    protected abstract void load();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }


    class NetWorkStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
    public void enterBrowserPage(String uri, int requestCode) {
        enterBrowserPage(null, uri, 0);
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
        Intent intent = new Intent(context, BaseBrowserActivity.class);
        if (bundle != null) {
            intent.putExtra(Constants.START_ACTIVITY_BUNDLE_KEY, bundle);
        }
        if (StringUtils.isNotEmpty(uri)) {
            intent.setData(Uri.parse(uri));
            LogUtils.i(TAG, uri);
        }
        if (requestCode > 0) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //是否疯狂快速点击
    public boolean isSoFastClick() {
        long curruntClickTime = SystemClock.uptimeMillis();
        if (startClickTime <= 0) {
            startClickTime = SystemClock.uptimeMillis();
            return false;
        } else {
            if (curruntClickTime - startClickTime < 500) {
                startClickTime = 0L;
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * 显示软键盘
     */
    public void openkeybord() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public void hiddenKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showLoading() {
        try {
            if (uploadPromptDialoge != null && uploadPromptDialoge.isShowing())
                return;
            if (uploadPromptDialoge == null)
                uploadPromptDialoge = new UploadPromptDialoge(this, R.style.CustomDialog);
            uploadPromptDialoge.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideLoading() {
        try {
            if (uploadPromptDialoge != null && uploadPromptDialoge.isShowing()) {
                uploadPromptDialoge.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
