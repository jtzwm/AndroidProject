package com.zhuwm.androidproject.webclient;

import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by zhuweiming on 16/3/29.
 */
public class MyJSInterface {


    //private final WebView webView;
    Handler handler = new Handler();

    private static String TAG="MyJSInterface";

    public MyJSInterface(){

    }

//    public MyJSInterface(WebView webView){
//        this.webView=webView;
//    }

    public void openVideo(){
        handler.post(new Runnable() {
            public void run() {
                //webView.loadUrl("javascript:wave()");
                Log.d(TAG,"++++由javascript调用");

            }
        });
    }
}
