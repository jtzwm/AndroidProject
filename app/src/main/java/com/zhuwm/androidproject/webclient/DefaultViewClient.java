package com.zhuwm.androidproject.webclient;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhuwm.androidproject.MainActivity;
import com.zhuwm.androidproject.WebViewActivity;

/**
 * Created by zhuweiming on 2016/3/30.
 */
public class DefaultViewClient extends WebViewClient {

    private Activity context;

    public DefaultViewClient(Activity webViewActivity) {
        this.context=webViewActivity;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

}
