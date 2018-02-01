package com.pf.process;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/2/1
 */
public class RemoteService extends Service {

    private static final int foregroundId = 10;

    private MyBinder myBinder;
    private MyServiceConnection myServiceConnection;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (null == myBinder) {
            myBinder = new MyBinder();
        }
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 绑定另一个服务
        if (null == myBinder) {
            myBinder = new MyBinder();
        }
        // 绑定回调
        myServiceConnection = new MyServiceConnection();
        // 让服务变成前台服务
        startForeground(foregroundId, new Notification());
        /**
         * API level < 18 ：参数2 设置 new Notification()，图标不会显示。
         * API level >= 18：在需要提优先级的service A启动一个InnerService。
         * 两个服务都startForeground，且绑定同样的 ID。Stop 掉InnerService ，通知栏图标被移除。
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startService(new Intent(this, LocalService.InnerService.class));
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
            startForeground(foregroundId, new Notification());
            // 销毁 innerservice
            stopSelf();
        }
    }

    class MyBinder extends IProcessAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }

    class MyServiceConnection implements ServiceConnection {

        private static final String TAG = "MyServiceConnection";

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: 主进程连接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: 主进程断开连接");
            startService(new Intent(RemoteService.this, LocalService.class));
            bindService(new Intent(RemoteService.this, LocalService.class), myServiceConnection, BIND_AUTO_CREATE);
        }
    }
}