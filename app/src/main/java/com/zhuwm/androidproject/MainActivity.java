package com.zhuwm.androidproject;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WebViewActivity.class));

            }
        });
    }
}
