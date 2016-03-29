package com.zhuwm.androidproject.webclient;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.zhuwm.androidproject.WebViewActivity;

public class MyWebViewClient extends WebChromeClient {

    public static final String TAG = "MyWebViewClient";

    @Override
    public boolean onConsoleMessage(ConsoleMessage cm) {
        Log.d("test", cm.message() + " -- From line "
                + cm.lineNumber() + " of "
                + cm.sourceId() );
        return true;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.d(TAG, message);
        result.confirm();
        return true;
    }
}
