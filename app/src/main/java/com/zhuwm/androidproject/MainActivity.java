package com.zhuwm.androidproject;

import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private static String TAG="MainActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "=============main activity");
        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));

            }
        });

        findViewById(R.id.btnVideo).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoSettingActivity.class));

            }
        });

        findViewById(R.id.btnLocalVideo).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocalVideoActivity.class));

            }
        });


    }
}
