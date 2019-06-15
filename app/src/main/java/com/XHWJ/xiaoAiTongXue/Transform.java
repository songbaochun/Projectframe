package com.XHWJ.xiaoAiTongXue;

import android.content.Context;

public class Transform {
    public static float dip2px(Context context, int dp){
        return (float) (context.getResources().getDisplayMetrics().density * dp + 0.5);
    }

}
