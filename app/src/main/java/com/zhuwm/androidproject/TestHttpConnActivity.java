package com.zhuwm.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TestHttpConnActivity extends Activity {
    private static String TAG="TestHttpConnActivity";

    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http_conn);

        findViewById(R.id.btnConnectServer).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connServer();
            }
        });
    }

    private void connServer() {
        tvResult = (TextView) findViewById(R.id.tvHttpResult);

       // RequestQueue mQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest("http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                        tvResult.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
