package com.pf.daemon.keep_service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/2/1
 * 通过前台服务来提高进程权限
 */
public class ForgroundService extends Service {

    // notification id
    private static int id = 10;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 让服务变成前台服务
        startForeground(id, new Notification());
        /**
         * API level < 18 ：参数2 设置 new Notification()，图标不会显示。
         * API level >= 18：在需要提优先级的service A启动一个InnerService。
         * 两个服务都startForeground，且绑定同样的 ID。Stop 掉InnerService ，通知栏图标被移除。
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startService(new Intent(this, InnerService.class));
        }
    }

    public static class InnerService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            // 让服务变成前台服务
            startForeground(id, new Notification());
            // 销毁 innerservice
            stopSelf();
        }
    }
}