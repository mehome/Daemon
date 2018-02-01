package com.pf.process;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/2/1
 */

public class Utils {

    /**
     * 判断服务是否开启
     *
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isRunningService(Context context, String serviceName) {
        if (null == context || TextUtils.isEmpty(serviceName)) {
            throw new NullPointerException("context or serviceName cannot be null.");
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : runningServices) {
            if (TextUtils.equals(serviceName, info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}