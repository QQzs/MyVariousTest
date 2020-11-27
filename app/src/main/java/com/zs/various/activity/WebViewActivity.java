package com.zs.various.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.zs.various.R;
import com.zs.various.util.JS2AndroidUtil;
import com.zs.various.util.LogUtil;

/**
 * Created by zs
 * Date：2017年 05月 25日
 * Time：19:13
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class WebViewActivity extends Activity {

    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        webView = findViewById(R.id.web_view);

        // 辅助WebView处理JavaScript的对话框，网站图标，网站title，加载进度等
        webView.setWebChromeClient(new WebChromeClient());

        // 使用WebView打开 而不是跳转到浏览器打开
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 打开 https链接
                handler.proceed();
//                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.Companion.logShow("url = " + url);
                view.loadUrl(url);
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                LogUtil.Companion.logShow("shouldOverrideUrlLoading ======  LOLLIPOP");
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
            }


        });

        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);

        // 允许所有SSL证书
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        loadHtml();
        loadUrl();

    }

    private void loadUrl(){
//        webView.loadUrl("http://www.taobao.com");
        webView.loadUrl("http://playtest.uxin.com//Viewsh5//hotpotlive");
//        webView.loadUrl("https://tests.ibaodian.com/web/live/invitationcard1?liveuid=c8a4e4c3-39e2-49e2-8b75-f65364faf081&userid=US170317000000000001&v=3.3.0");
    }

    private void loadHtml(){

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        // 参数1：JS调用android中方法对象
        // 参数2：映射到js中的test对象
        webView.addJavascriptInterface(new JS2AndroidUtil(this) , "javaMethod");
        webView.loadUrl("file:////android_asset/index.html");

    }

    /**
     * android 进行操作
     * @param msg
     */
    public void show(String msg){
        Toast.makeText(this , msg , Toast.LENGTH_SHORT).show();
    }

    /**
     * android 调用 JS方法
     */
    public void loadJSMethod(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 函数传的参数要用 '' 单引号包起来
                webView.evaluateJavascript("javascript:callJS('Android调用JS中有参方法')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        // 用不到可以传null
                    }
                });
            }
        });
    }

    private void loadTextImage(){
//        String body = "123445555";
//        webView.loadData(body, "text/html; charset=UTF-8", null);
//        String data = "<HTML>在模拟器 2.1 上测试,这是<IMG src=\"APK'>file:///android_asset/igg.jpg\"/>APK里的图片";
//        String image = "http://imgs.ibaodian.com/ios/q//0/1492052493.png";
        String image= "<img src=\"http://imgs.ibaodian.com/ios/q//0/1492052493.png\"/>";
        String css = "<style type=\"text/css\"> img {" +
                "text-align:center;" +
                "width:100%;" +
                "height:400px;" +
                "}" +
                "body {" +
//                "margin-right:15px;" +
//                "margin-left:15px;" +
//                "margin-top:15px;" +
//                "font-size:45px;" +
                "}" +
                "</style>";
        image = "<html><header>" + css + "</header><body>" + image + "</body></html>";

        String txtcss = "<style type=\"text/css\">" +
                "body {" +
                "margin-left:40px;" +
                "margin-right:40px;" +
                "margin-top:60px;" +
                "margin-bottom:60px;" +
                "}" +
                "</style>";

        String text1 = "<html><header>" + txtcss + "</header><body>" + "图文混排显示图文混排显示图文混排显示混排显示图文混<br>" + "</body></html>";
        String text2 = "<html><header>" + txtcss + "</header><body>" + "<br>第二段文字显示第二段文混排显示图文混字显示第二段文字显示" + "</body></html>";

        String data = text1 + image + text2;
        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);

    }

    // 在 Actvity 中监听返回键按钮
    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

}
