package com.yysc.commomlibary.base;

import android.app.Application;
import android.content.Context;

import static okhttp3.internal.Internal.instance;

/**
 * Created by kj00037 on 2017/12/7.
 */

public class BaseApplication extends Application {

    private volatile static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}

