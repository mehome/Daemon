package com.pf.daemon.keep_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/1/31
 * 通过 activity 来提高进程的权限
 */
public class KeepManager {

    private static final String TAG = "KeepManager";
    private static KeepManager instance;
    private KeepReceiver mKeepReceiver;
    private WeakReference<Activity> mWeakReference;

    private KeepManager() {
    }

    public static KeepManager getInstance() {
        if (null == instance) {
            synchronized (KeepManager.class) {
                if (null == instance) {
                    instance = new KeepManager();
                }
            }
        }
        return instance;
    }

    /**
     * 注册亮屏、息屏广播
     *
     * @param context
     */
    public void registerScreenReceiver(Context context) {
        if (null == context) {
            throw new NullPointerException("context cannot be null.");
        }
        IntentFilter filter = new IntentFilter();
        // 关屏
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 开屏
        filter.addAction(Intent.ACTION_SCREEN_ON);
        mKeepReceiver = new KeepReceiver();
        context.registerReceiver(mKeepReceiver, filter);
    }

    /**
     * 注销亮屏，息屏广播
     *
     * @param context
     */
    public void unRegisterScreenReceiver(Context context) {
        if (null == context) {
            throw new NullPointerException("context cannot be null.");
        }
        if (null != mKeepReceiver) {
            context.unregisterReceiver(mKeepReceiver);
        }
    }

    /**
     * 开启 keep activity
     *
     * @param context
     */
    public void startKeepActivity(Context context) {
        if (null == context) {
            throw new NullPointerException("context cannot be null.");
        }
        if (null != mWeakReference) {
            Log.e(TAG, "startKeepActivity: 已经开启过 KeepActivity 了");
            return;
        }
        Intent intent = new Intent(context, KeepActivity.class);
        context.startActivity(intent);
    }

    /**
     * 关闭 keep activity
     */
    public void finishKeepActivity() {
        if (null != mWeakReference) {
            Activity activity = mWeakReference.get();
            if (null != activity) {
                activity.finish();
                mWeakReference = null;
            }
        }
    }

    /**
     * 设置 keep activity
     *
     * @param activity
     */
    public void setKeepActivity(Activity activity) {
        if (null == activity) {
            throw new NullPointerException("activity cannot be null.");
        }
        mWeakReference = new WeakReference<Activity>(activity);
    }
}