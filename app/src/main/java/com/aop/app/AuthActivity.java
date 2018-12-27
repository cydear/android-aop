package com.aop.app;

import android.Manifest;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aop.app.annotation.AndroidPermission;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        findViewById(R.id.btn_one).setOnClickListener(view -> authOne());

        findViewById(R.id.btn_aop_auth).setOnClickListener(view -> readPhoneState());
    }

    @AndroidPermission(permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
    })
    private void readPhoneState() {
        Log.d("===>", "read phone: read phone state complete");
    }

    private void authOne() {
        AndPermission.with(AopApplication.getTopActivity())
                .runtime()
                .permission(Manifest.permission.READ_PHONE_STATE)
                .rationale(((context, permissions, executor) -> {
                    //拒绝权限后，执行
                    executor.execute();
                }))
                .onGranted(permissions -> {
                    //授权通过
                    Log.d("===>", "授权通过");
                })
                .onDenied(permissions -> {
                    //授权不通过
                    Log.d("===>", "授权不通过");
                    if (AndPermission.hasAlwaysDeniedPermission(AopApplication.getTopActivity(), permissions)) {
                        AndPermission.with(AopApplication.getTopActivity())
                                .runtime()
                                .setting()
                                .start();
                    }
                })
                .start();
    }
}
