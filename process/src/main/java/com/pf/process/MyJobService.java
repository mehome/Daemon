package com.pf.process;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/2/1
 */
@SuppressLint("NewApi")
public class MyJobService extends JobService {

    private static int JOB_ID = 10;
    private static final String TAG = "MyJobService";

    /**
     * 开启轮循
     *
     * @param context
     */
    public static void startJob(Context context) {
        if (null == context) {
            throw new NullPointerException("context cannot be null.");
        }
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // setPersisted 在设备重启后依然执行
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,
                new ComponentName(context.getPackageName(), MyJobService.class.getName()))
                .setPersisted(true);
        // 小于7.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            // 每隔一秒执行一次
            builder.setPeriodic(1_000);
        } else {
            // 延迟执行任务
            builder.setMinimumLatency(1_000);
        }
        jobScheduler.schedule(builder.build());
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "onStartJob: 开启轮循");
        // 如果7.0以上，开启轮循
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            startJob(this);
        }
        boolean isLocalServiceRunning = Utils.isRunningService(this, LocalService.class.getName());
        boolean isRemoteServiceRunning = Utils.isRunningService(this, RemoteService.class.getName());
        if (!isLocalServiceRunning) {
            startService(new Intent(this, LocalService.class));
        }
        if (!isRemoteServiceRunning) {
            startService(new Intent(this, RemoteService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}