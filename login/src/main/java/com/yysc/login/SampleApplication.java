package com.yysc.login;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.yysc.login.Log.MyLogImp;
import com.yysc.login.util.TinkerManager;

/**
 * Created by kj00037 on 2018/1/11.
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.yysc.login.MyApp", flags = ShareConstants.TINKER_ENABLE_ALL,loadVerifyFlag = false)
public class SampleApplication extends DefaultApplicationLike{

    public SampleApplication(Application application,
                             int tinkerFlags,
                             boolean tinkerLoadVerifyFlag,
                             long applicationStartElapsedTime,
                             long applicationStartMillisTime,
                             Intent tinkerResultIntent)
    {
        super(application,
              tinkerFlags,
              tinkerLoadVerifyFlag,
              applicationStartElapsedTime,
              applicationStartMillisTime,
              tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);

        // Tinker管理类，保存当前对象
        TinkerManager.setTinkerApplicationLike(this);
        // 崩溃保护
        TinkerManager.initFastCrashProtect();
        // 是否重试
        TinkerManager.setUpgradeRetryEnable(true);

        //Log 实现，打印加载补丁的信息
        TinkerInstaller.setLogIml(new MyLogImp());

        // 运行Tinker ，通过Tinker添加一些基本配置
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }
}
