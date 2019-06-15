package com.XHWJ.xiaoAiTongXue.base;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.XHWJ.xiaoAiTongXue.R;
import com.XHWJ.xiaoAiTongXue.constant.Constants;
import com.XHWJ.xiaoAiTongXue.utils.ExceptionUtil;
import com.XHWJ.xiaoAiTongXue.utils.LogUtils;
import com.XHWJ.xiaoAiTongXue.utils.StringUtils;
import com.XHWJ.xiaoAiTongXue.views.MarqueeView;


public class BaseBrowserActivity extends BaseActivity {

    //页面标签
    private static final String TAG = "BrowserActivity";
    //返回
    private ImageView btnLeft;
    //标题
    private MarqueeView tvTitle;
    //网页展示
    private WebView webview;
    //分享
    private ImageView im_h5_share;
    //加载的url
    private String url;


    //接收数据
    private String browserTitle;
    //是否显示分享按钮
    private boolean aBoolean = true;

    @Override
    public int getLayoutResource() {
        return R.layout.base_activity_browser;
    }

    @Override
    public void initData() {
        initView();
    }

    @Override
    protected void load() {

    }

    private void initView() {
        btnLeft = findViewById(R.id.btn_left);
        tvTitle = findViewById(R.id.tv_title);
        webview = findViewById(R.id.wv_browser_detail);
        im_h5_share = findViewById(R.id.im_h5_share);


        settingWebView();
        //设置传递过来数据
        initValues();
        //加载webview
        loadwebUrl();
        //页面监听
        addListeners();
    }


    private void settingWebView() {
        //设置webview
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        //不适用缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDatabaseEnabled(true);
        //支持缩放
        settings.setSupportZoom(true);// 支持放大缩小
        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        //自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }

    private void initValues() {
        //获取传递过来的参数
        Bundle bundle = getIntent().getBundleExtra(Constants.START_ACTIVITY_BUNDLE_KEY);
        if (bundle != null) {
            //接收标题
            if (bundle.containsKey("browsertitle")) {
                browserTitle = bundle.getString("browsertitle");
                LogUtils.i(TAG, "browserTitle-----------------" + browserTitle);
            }
            //设置标题
            if (StringUtils.isNotEmpty(browserTitle)) {
                tvTitle.setText(browserTitle);
            }
        }
    }

    private void loadwebUrl() {
        url = getIntent().getDataString();
        LogUtils.i(TAG, url);
        webview.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            webview.clearCache(true);
            webview.destroy();
        }
    }


    //注册监听器
    protected void addListeners() {
        try {
            //返回
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            //分享
            im_h5_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo 分享更换
//                    ShareUtils.showShare(false, null,
//                            getApplicationContext(), new Handler(), "村蜂小院", browserTitle,
//                            "http://image.cf1017.com/cunfenglogo.png",
//                            url);

                }
            });

        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }

}
