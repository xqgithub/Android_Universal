package com.universal.aifun.baselibrary;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.universal.aifun.baselibrary.database.DBHelperModule;
import com.universal.aifun.baselibrary.help.MyActivityLifecycleCallbacks;
import com.universal.aifun.baselibrary.help.MySPUtilsUser;
import com.universal.aifun.baselibrary.tinker.util.TinkerManager;
import com.universal.aifun.baselibrary.utils.AppUtils;
import com.universal.aifun.baselibrary.utils.CrashHandler;
import com.universal.aifun.baselibrary.utils.LogUtils;
import com.universal.aifun.baselibrary.utils.ScreenTools;


/**
 * Date:2021/5/10
 * Time:18:33
 * author:joker
 * 自定义Application
 */
public class TKBaseApplication extends DefaultApplicationLike {

    public static Application myApplication;

    TKBaseApplication mCustomTinkerLike;

    //Activity的计数
//    private int mActivityCount = 0;

    public static MyActivityLifecycleCallbacks lifecycleCallbacks = new MyActivityLifecycleCallbacks();

    public TKBaseApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = getApplication();
        initConfig();
        //这句话是为了防止 webview页面打开的时候，造成语言混乱
        //new WebView(this).destroy();

//        initKV();
        boolean isAgree = MySPUtilsUser.getInstance().getUserPrivacyAgreementStatus();
        if (isAgree) {
//            initCoreLib();
//            initBugly();
//            initJpush();
        }
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        //1.初始化AutoSize
        ScreenTools.getInstance().initAutoSize(myApplication, myApplication);

        //2.日志类加载初始化
        new LogUtils.Builder()
                .setLogSwitch(AppUtils.isAppDebug(myApplication))//设置log总开关，默认开
                //.setGlobalTag("CMJ")// 设置log全局标签，默认为空
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(false)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
        //3.加载全部异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(myApplication);
        //4.初始化Stetho出正式包的时候，建议屏蔽掉
        if (AppUtils.isAppDebug(myApplication)) {
            Stetho.initializeWithDefaults(myApplication);
        }
        //5.注册Activity生命周期监听回调，此部分一定加上，因为有些版本不加的话多语言切换不回来
        initActivityLifecycle();
        //6.初始化数据库
        DBHelperModule.getInstance().initDbHelp();

    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        //系统语言等设置发生改变时会调用此方法，需要要重置app语言
//        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(base));
////        super.attachBaseContext(base);
//    }


    /**
     * 初始化ActivityLifecycle
     */
    private void initActivityLifecycle() {
        myApplication.registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }


    public void initBugly() {
        // a6bb433246（公司的）  5b29dde48c（自己的） //正式需改
        String buglyKey = "5b29dde48c";
        if ("release".equals(BuildConfig.host)) {
            buglyKey = "a6bb433246";
        } else {
            buglyKey = "5b29dde48c";
        }
        CrashReport.initCrashReport(myApplication, buglyKey, false);
    }

    public void initKV() {
        MMKV.initialize(myApplication);
    }


    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //必须使用multiDex
        MultiDex.install(base);

        mCustomTinkerLike = this;
        TinkerManager.setTinkerApplicationLike(this);

        //在 installed 之前设置
        TinkerManager.setUpgradeRetryEnable(true);

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }



}
