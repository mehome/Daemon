package com.pf.daemon.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ 1308108803
 * @date 2018/2/1
 */
public class AccountHelper {

    private static final String TAG = "AccountHelper";

    /**
     * 添加一个账户
     *
     * @param context
     * @param name     用户名
     * @param password 密码
     */
    public static void addAccount(Context context, String name, String password) {
        if (null == context) {
            throw new NullPointerException("context cannot be null.");
        }
        AccountManager am = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        // 获得此类型的账户
        Account[] accounts = am.getAccountsByType(AccountContants.ACCOUNT_TYPE);
        if (accounts.length > 0) {
            Log.e(TAG, "addAccount: 账户已经存在");
            return;
        }
        // 添加一个账户
        Account account = new Account(name, AccountContants.ACCOUNT_TYPE);
        am.addAccountExplicitly(account, password, new Bundle());
    }

    /**
     * 设置自动同步账户
     *
     * @param name 用户名
     */
    public static void autoSync(String name) {
        Account account = new Account(name, AccountContants.ACCOUNT_TYPE);
        // 设置同步
        ContentResolver.setIsSyncable(account, AccountContants.PROVIDER_AUTHORITY, 1);
        // 自动同步
        ContentResolver.setSyncAutomatically(account, AccountContants.PROVIDER_AUTHORITY, true);
        // 设置同步周期
        // 为了达到进程保活的效果，可以开启自动同步。
        // 时间间隔虽然设置了1s，但是Android本身为了考虑同步所带来的消耗和减少唤醒设备的次数，1s只是一个参考时间
        ContentResolver.addPeriodicSync(account, AccountContants.PROVIDER_AUTHORITY, new Bundle(), 1);
    }
}