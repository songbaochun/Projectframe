package com.XHWJ.xiaoAiTongXue.utils;

import android.content.Context;

import com.XHWJ.xiaoAiTongXue.cache.ACache;
import com.XHWJ.xiaoAiTongXue.cache.ICache;
import com.XHWJ.xiaoAiTongXue.constant.Constants;


/**
 * Created by zhang on 2018/11/19.
 */

public class AccountUtils {
    private static final String TAG = "AccountUtils";
    private static ICache mCacheUtil;
//    private static IndexBannerEntity indexBannerEntity;
    private final Context mContext;

    private AccountUtils(Context context) {
        this.mContext = context;
        mCacheUtil = ACache.get(mContext);
    }

    private static AccountUtils single = null;

    //静态工厂方法
    public static AccountUtils getInstance(Context context) {
        if (single == null) {
            single = new AccountUtils(context);
        }
        return single;
    }

    //设置用户登录状态
    public void setIsLogin(boolean isLogin) {
        mCacheUtil.put(Constants.LOGIN_STATUS, isLogin + "");
    }

    //获取用户登录状态
    public boolean getIsLogin() {
        String asString = mCacheUtil.getAsString(Constants.LOGIN_STATUS);
        if (StringUtils.isNotEmpty(asString) && asString.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    //设置APP第一次进入
    public void setFirstApp(boolean firstApp) {
        mCacheUtil.put(Constants.FIRST_APP, firstApp + "");
    }

    //获取是否第一次进入APP
    public boolean getFirstApp() {
        String asString = mCacheUtil.getAsString(Constants.FIRST_APP);
        if (StringUtils.isNotEmpty(asString) && asString.equals("false")) {
            return false;
        } else {
            return true;
        }
    }

    public void setToken(String b) {
        mCacheUtil.put(Constants.TOKEN, b);
    }

    public static String getToken() {
        return mCacheUtil.getAsString(Constants.TOKEN);
    }

//    public void saveIndexBanner(IndexBannerEntity data) {
//        indexBannerEntity = getIndexBanner();
//        indexBannerEntity = data;
//
//        String result = GsonUtil.bean2json(indexBannerEntity);
//
//        mCacheUtil.put(Constants.INDEX_BANNER, result);
//    }
//
//    public static IndexBannerEntity getIndexBanner() {
//        if (indexBannerEntity != null)
//            return indexBannerEntity;
//
//        String index_banner = mCacheUtil.getAsString(Constants.INDEX_BANNER);
//        if (!TextUtils.isEmpty(index_banner)) {
//            indexBannerEntity = GsonUtil.parseJsonToBean(index_banner, IndexBannerEntity.class);
//        }
//        return indexBannerEntity;
//    }

}
