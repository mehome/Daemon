package com.pf.daemon.system_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/2/1
 * 系统Service机制拉活
 */
public class StickService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 这里返回来的默认就是 START_STICKY
        return super.onStartCommand(intent, flags, startId);
    }
}