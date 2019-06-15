package com.XHWJ.xiaoAiTongXue;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.XHWJ.xiaoAiTongXue.utils.AccountUtils;
import com.XHWJ.xiaoAiTongXue.utils.AppUtil;
import com.XHWJ.xiaoAiTongXue.utils.LogUtils;
import com.XHWJ.xiaoAiTongXue.utils.ResourceUtil;
import com.XHWJ.xiaoAiTongXue.utils.StringUtils;
import com.XHWJ.xiaoAiTongXue.utils.ToastUtils;
import com.example.network.RequestHelper;
import com.example.network.exception.ResultException;
import com.example.network.handler.GlobalErrorListener;
import com.example.network.handler.GlobalHttpConfig;
import com.example.network.handler.GlobalHttpHandler;
import com.example.network.intercept.RequestIntercept;
import com.example.network.model.BaseResponse;

import java.util.logging.Level;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static MyApplication myApplication;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mContext = getApplicationContext();
//        OKGO初始化 配置
        initRequest();
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public static Context getContext() {
        return mContext;
    }

    private void initRequest() {
        RequestIntercept requestIntercept = new RequestIntercept("peiqin");
        //log打印级别，决定了log显示的详细程度
        requestIntercept.setPrintLevel(BuildConfig.DEBUG ? RequestIntercept.Level.BODY : RequestIntercept.Level.NONE);
        //log颜色级别，决定了log在控制台显示的颜色
        requestIntercept.setColorLevel(Level.INFO);
        requestIntercept.setHandler(new GlobalHttpHandler() {
            @Override
            public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                return null;
            }

            @Override
            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                String token = AccountUtils.getInstance(getContext()).getToken();
                Request.Builder requestBuilder = request.newBuilder();

                if (TextUtils.isEmpty(token)) {
                    requestBuilder.removeHeader("XToken");
                } else {
                    requestBuilder.addHeader("XToken", token);
                }

                String XDevice = AppUtil.getDeviceId(getContext());
                if (!TextUtils.isEmpty(XDevice)) {
                    requestBuilder.addHeader("XDevice", XDevice);
                }

                requestBuilder.addHeader("XRole", "1");//1 老师    2：家长
                Request build = requestBuilder.build();
                return build;
            }
        });

        String concat = StringUtils.concat("Android/", AppUtil.getVersionName(this), ".", AppUtil.getVersionCode(this),
                " (", AppUtil.getOSVersion(), ") ");
        GlobalHttpConfig config = GlobalHttpConfig.buidler()
                .addInterceptor(requestIntercept)
//                .addInterceptor(new OkHttpMockInterceptor(this,1))
                .responseErroListener(new GlobalHandler())
                .addCommonHeader("XUser-Agent", concat)
                .build();

        RequestHelper.getInstance().init(this, config);
    }

    class GlobalHandler implements GlobalErrorListener {

        @Override
        public void handleResponseError(Throwable e) {
            //所有异常在这里处理
            if (e instanceof ResultException) {
                String resultCode = ((ResultException) e).getStatus();
                String message = ((ResultException) e).getErrorMessage();

                LogUtils.e(TAG, "status:" + resultCode + "   msg:" + message);

                if (TextUtils.equals(BaseResponse.TOKEN_INVALID, resultCode) || TextUtils.equals(BaseResponse.TOKEN_EXPIRED, resultCode)) {
                    //不合法的TOKEN  过期了  推出登陆的操作等
                    LogUtils.i(TAG, "====TOKEN过期=====");
                    //  ToastUtil.showToastS("TOKEN过期");
//                    AccountUtils.getInstance(getContext()).clearUserInfo();
//                    AccountUtils.getInstance(getContext()).clearLoginFlag();
//                    EMClient.getInstance().logout(true);
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    CommonAction.getInstance().OutSign();

               /*     Intent intent = new Intent(getContext(), GlobalDialogActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(GlobalDialogActivity.INTENT, GlobalDialogActivity.INVALIDATE_TOKEN);
                    if (!TextUtils.isEmpty(message)) {
                        intent.putExtra(BaseResponse.ERROR_MESSAGE, message);
                    }
                    getContext().startActivity(intent);*/
                } else if (TextUtils.equals(BaseResponse.FORCE_UPGRADE, resultCode)) {
                    //强制更新 发一个EventBus通知主页和登陆页面，保证有一方会接收到通知。
                    //  EventBus.getDefault().post(new CheckUpgradeEvent());
                } else {//除上面情况意外的才吐司一下提示
                    if (!TextUtils.isEmpty(message)) {
                        ToastUtils.showShort(message);
                    }
                }
            } else {
                LogUtils.e(TAG, e.getMessage());
                String message = e.getMessage();
                if (!TextUtils.isEmpty(message) && message.contains("Unable to resolve host")) {
                    ToastUtils.showShort(ResourceUtil.getString(R.string.net_error));
                } else {
                    ToastUtils.showShort(ResourceUtil.getString(R.string.net_connection_error));
                }
            }
        }
    }

}
