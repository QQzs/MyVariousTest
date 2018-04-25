package com.zs.various.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.zs.various.R;
import com.zs.various.util.TagUtils;

/**
 * Created by zs
 * Date：2018年 04月 24日
 * Time：14:21
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class FullVideoActivity extends Activity {

    private WebView mWebView;
    private FrameLayout mVideoContainer;
    private WebChromeClient.CustomViewCallback mCallBack;

    private String URL1 = "http://m.tv.sohu.com/20140508/n399272261.shtml";
    private String URL2 = "https://testm.ibaodian.com/inforweb.do?i=IN160708000000000005&u=US170804000000000001&v=2.9.0&p=android&isapp=Y";
    private String URL3 = "http://v.qq.com/iframe/player.html?vid=o0318tp1ddw&tiny=0&auto=0";
    private String URL4 = "https://v.qq.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_webview);

        mWebView = findViewById(R.id.webView);
        mVideoContainer = findViewById(R.id.videoContainer);

        initWebView();

        mWebView.loadUrl(URL4);
        mWebView.addJavascriptInterface(new JsObject(),"onClick");

    }

    private void initWebView(){
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new CustomWebViewChromeClient());
        mWebView.setWebViewClient(new CustomWebClient());

        mWebView.addJavascriptInterface(new JsObject(),"onClick");
    }

    private class JsObject{

        @JavascriptInterface
        public void fullscreen(){
            //监听到用户点击全屏按钮
            fullScreen();
            Log.d("My_Log","JsObject");
        }
    }

    private class CustomWebViewChromeClient extends WebChromeClient{

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            fullScreen();
            mWebView.setVisibility(View.GONE);
            mVideoContainer.setVisibility(View.VISIBLE);
            mVideoContainer.addView(view);
            mCallBack=callback;
            Log.d("My_Log","onShowCustomView");
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            fullScreen();
            if (mCallBack!=null){
                mCallBack.onCustomViewHidden();
            }
            mWebView.setVisibility(View.VISIBLE);
            mVideoContainer.removeAllViews();
            mVideoContainer.setVisibility(View.GONE);
            Log.d("My_Log","onHideCustomView");
            super.onHideCustomView();
        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private class CustomWebClient extends WebViewClient{

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String js= TagUtils.getJs(url);
            view.loadUrl(js);
            Log.d("My_Log","loadUrl");
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

}
