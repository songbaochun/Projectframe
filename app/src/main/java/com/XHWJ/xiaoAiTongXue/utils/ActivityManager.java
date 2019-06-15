package com.XHWJ.xiaoAiTongXue.utils;

import android.app.Activity;


import java.util.Stack;

public class ActivityManager {
        private static ActivityManager instance;
        private Stack<Activity> activityStack;// activity栈

        // 单例模式
        public static ActivityManager getInstance() {
            if (instance == null) {
                instance = new ActivityManager();
            }
            return instance;
        }

        // 把一个activity压入栈中
        public void addStackActivity(Activity actvity) {
            if (activityStack == null) {
                activityStack = new Stack<Activity>();
            }
            activityStack.add(actvity);
        }

        // 获取栈顶的activity，先进后出原则
        public Activity getLastActivity() {
            return activityStack.lastElement();
        }

        // 移除一个activity
        public void removeStackActivity(Activity activity) {
            if (activityStack != null && activityStack.size() > 0) {
                if (activity != null) {
                    activity.finish();
                    activityStack.remove(activity);
                    activity = null;
                }

            }
        }

        // 退出所有activity
        public void finishAllActivity() {
            if (activityStack != null) {
                while (activityStack.size() > 0) {
                    Activity activity = getLastActivity();
                    if (activity == null)
                        break;
                    removeStackActivity(activity);
                }
            }

        }
    /**
     * 结束除了登录外的其他所有activity
     */
    public void finishOtherActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
//                if (activityStack.get(i) instanceof LoginActivity == false) {
//                    activityStack.get(i).finish();
//                }
            }
        }
        activityStack.clear();
    }
}
