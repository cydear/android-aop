package com.aop.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * [类功能说明]
 *
 * @author lary.huang
 * @version v 1.4.8 2018/12/27 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class AopApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        mContext = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mContext = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public static Context getTopActivity() {
        return mContext;
    }
}
