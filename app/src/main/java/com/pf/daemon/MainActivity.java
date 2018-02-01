package com.pf.daemon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.pf.daemon.keep_activity.KeepManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * activity 方式提高进程权限
         */
        // KeepManager.getInstance().registerScreenReceiver(this);

        /**
         * service 方式提高进程权限
         */
        // startService(new Intent(this, ForgroundService.class));

        /**
         * 通过系统service方式拉活
         */
        // startService(new Intent(this, StickService.class));

        /**
         * 账户同步 拉活
         */
        // AccountHelper.addAccount(this, AccountContants.ACCOUNT_NAME, AccountContants.ACCOUNT_PASSWORD);
        // AccountHelper.autoSync(AccountContants.ACCOUNT_NAME);

        // JobScheduler 拉活
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // MyJobService.startJob(this);
        // }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeepManager.getInstance().unRegisterScreenReceiver(this);
    }
}