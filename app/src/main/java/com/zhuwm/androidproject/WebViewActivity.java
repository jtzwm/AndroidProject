package com.zhuwm.androidproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhuwm.androidproject.webclient.DefaultViewClient;
import com.zhuwm.androidproject.webclient.DefaultWebChromeClient;
import com.zhuwm.androidproject.webclient.MyJSInterface;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    Handler handler = new Handler();

    private WebView webView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webView);
        Log.d(TAG,"+++++++在onCreate方法中");
        initWebView();
        //showProgressDialog("正在加载数据，请稍后...");
        webView.loadUrl("http://192.168.3.71:8080/h5test/index.do");

    }


    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        //webView=new WebView(this);
        //setContentView(webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");
        Log.d(TAG, "+++++++加载完JavaScriptInterface了");

        webView.setWebChromeClient(new DefaultWebChromeClient());
        webView.setWebViewClient(new DefaultViewClient(this));

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        Log.i(TAG, "+++++++ onKey==KEYCODE_BACK...");

                        webView.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });




    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {}

        @JavascriptInterface
        public void openVideo() {
            System.out.println("======终于进来鸟");
            handler.post(new Runnable() {
                public void run() {
                    Log.d(TAG,"+++++++终于进来了");
                    //mWebView.loadUrl("javascript:wave()");
                }
            });

        }
    }

    public ProgressDialog progressDialog;

    /**
     * 显示自定义信息进度条
     *
     * @param message
     *            要显示的信息内容
     */
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            createProgressDialog();
        }
        progressDialog.setMessage(message);
        if (!this.isFinishing() && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * 创建进度条
     */
    protected void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

}
