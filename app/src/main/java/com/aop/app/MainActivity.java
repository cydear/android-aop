package com.aop.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aop.app.annotation.CheckLoginAnnotation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_sayhello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHello(MainActivity.this);
            }
        });

        findViewById(R.id.btn_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startToAuthActivity();
            }
        });
    }

    private void startToAuthActivity() {
        startActivity(new Intent(this, AuthActivity.class));
    }

    @CheckLoginAnnotation
    public void sayHello(Activity parent) {
        Log.d("===>", "===>Hello Everyone");
    }
}
