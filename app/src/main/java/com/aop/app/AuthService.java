package com.aop.app;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.yanzhenjie.permission.AndPermission;

public class AuthService extends Service {
    private static Handler mHandler = new Handler();

    public AuthService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAuthPermission();
            }
        }, 1000);
        return START_STICKY;
    }

    /**
     * 申请权限
     */
    private void startAuthPermission() {
        Log.d("===>", "AuthService....activity:" + AopApplication.getTopActivity());
        AndPermission.with(getApplicationContext())
                .runtime()
                .permission(Manifest.permission.READ_PHONE_STATE)
                .rationale((context, data, executor) -> {
                    executor.execute();
                })
                .onGranted(permissions -> {
                    Log.d("===>", "AuthService....授权成功");
                })
                .onDenied(permissions -> {
                    Log.d("===>", "AuthService....授权失败");
                    if (AndPermission.hasAlwaysDeniedPermission(getApplicationContext(), permissions)) {
                        AndPermission.with(getApplicationContext())
                                .runtime()
                                .setting()
                                .start();
                    }
                })
                .start();
    }
}
