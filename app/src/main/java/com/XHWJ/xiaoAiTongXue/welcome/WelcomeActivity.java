package com.XHWJ.xiaoAiTongXue.welcome;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.XHWJ.xiaoAiTongXue.MainActivity;
import com.XHWJ.xiaoAiTongXue.R;
import com.XHWJ.xiaoAiTongXue.base.BaseActivity;
import com.XHWJ.xiaoAiTongXue.utils.AccountUtils;
import com.XHWJ.xiaoAiTongXue.utils.ExceptionUtil;


/**
 * Created by 宋宝春 on 2018/11/7.
 * 欢迎页
 */
public class WelcomeActivity extends BaseActivity {

    //页面标签
    private static final String TAG = "WelcomeActivity";
    //图片
    private ImageView welcome_iv_bg;
    //权限的回调
    private static final int BAIDU_READ_PHONE_STATE = 100;
    //进入引导页
    private static final int MSG_INTENT_FIRST = 1;
    //进入主页
    private static final int MSG_INTENT_MAIN = 2;
    //进入登录页
    private static final int MSG_INTENT_LOGIN = 4;
    //开始时间
    private long startTime;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            try {
                switch (message.what) {
                    //导航页
                    case MSG_INTENT_FIRST:
                        enterPage(FirstInViewImgActivity.class);
                        break;
                    //主页
                    case MSG_INTENT_MAIN:
                        enterPage(MainActivity.class);
                        break;
                    //登录页
                    case MSG_INTENT_LOGIN:
                        //进入登录之后登录成功改变本地的登录状态
//                        enterPage(LoginActivity.class);
                        AccountUtils.getInstance(context).setIsLogin(true);
                        break;
                }
                finish();
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
            return false;
        }
    });

    @Override
    public int getLayoutResource() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        welcome_iv_bg = findViewById(R.id.welcome_iv_bg);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .asBitmap()
                .load(R.mipmap.startup_page)
                .apply(options)
                .into(welcome_iv_bg);

        startTime = System.currentTimeMillis();
        isFirstLoad();
    }

    @Override
    protected void load() {

    }

    private void isFirstLoad() {
        if (AccountUtils.getInstance(context).getFirstApp()) {
            //进入引导页
            enterActivity(MSG_INTENT_FIRST);
        } else {
            //进入主页
            if (AccountUtils.getInstance(context).getIsLogin()) {
                enterActivity(MSG_INTENT_MAIN);
            } else {
                enterActivity(MSG_INTENT_LOGIN);
            }

        }
    }

    //进入界面
    private void enterActivity(int flag) {
        long time = 1000 - System.currentTimeMillis() + startTime;
        if (time > 0) {
            handler.sendEmptyMessageDelayed(flag, time);
        } else {
            handler.sendEmptyMessage(flag);
        }
    }

}
