package com.universal.aifun;


import android.app.Application;
import android.content.Intent;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.universal.aifun.baselibrary.TKBaseApplication;
import com.universal.aifun.baselibrary.TKExtManage;

/**
 * 自定义Application
 */
@DefaultLifeCycle(application = "com.universal.aifun.SampleApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class MyApplication extends TKBaseApplication {

    public MyApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TKExtManage extManage = TKExtManage.getInstance();
        // 设置第三方
        extManage.setTinker_base_id(BuildConfig.tinker_base_id);
        extManage.setTinker_id(BuildConfig.tinker_id);
        extManage.setTinker_message(BuildConfig.tinker_message);
    }
}
