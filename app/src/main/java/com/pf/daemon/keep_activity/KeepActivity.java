package com.pf.daemon.keep_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.pf.daemon.R;

/**
 * 一个像素的页面，在息屏后打开页面，开屏后关闭
 */
public class KeepActivity extends AppCompatActivity {

    public static final String TAG = "KeepActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep);
        // 获取窗口
        Window window = getWindow();
        // 放在左上角
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attributes = window.getAttributes();
        // 宽高设置为1
        attributes.width = 1;
        attributes.height = 1;
        // 起始坐标从0开始
        attributes.x = 0;
        attributes.y = 0;
        // 重新设置
        window.setAttributes(attributes);
        // 设置activity
        KeepManager.getInstance().setKeepActivity(this);

        Log.e(TAG, "onCreate: keep activity 开启");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: keep activity 销毁");
    }
}