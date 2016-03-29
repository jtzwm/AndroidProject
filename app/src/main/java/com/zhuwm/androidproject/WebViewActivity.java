package com.zhuwm.androidproject;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zhuwm.androidproject.webclient.MyJSInterface;
import com.zhuwm.androidproject.webclient.MyWebViewClient;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    Handler handler = new Handler();

    private WebView webView;


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.setWebChromeClient(new MyWebViewClient());
        webView.addJavascriptInterface(new DemoJavaScriptInterface(), "J2J");
        webView.loadUrl("http://192.168.3.71:8080/h5test/index.do");

/*        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });*/

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    final class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        public void openVideo() {
            handler.post(new Runnable() {
                public void run() {
                    Log.d(TAG,"+++++++终于进来了");
                    //mWebView.loadUrl("javascript:wave()");
                }
            });

        }
    }

}
