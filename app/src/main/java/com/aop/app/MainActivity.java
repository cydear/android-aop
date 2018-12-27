package com.aop.app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aop.app.aspect.CheckLoginAnnotation;

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
    }

    @CheckLoginAnnotation
    public void sayHello(Activity activity) {
        Log.d("===>", "===>Hello Everyone");
    }
}
