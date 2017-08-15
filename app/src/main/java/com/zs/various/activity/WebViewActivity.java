package com.zs.various.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zs.various.R;
import com.zs.various.util.DensityUtil;

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
        webView = (WebView) findViewById(R.id.web_view);

        // 设置WebView的客户端
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;// 返回false
            }
        });

        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
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
        // 设置默认字体大小
        webSettings.setDefaultFontSize(DensityUtil.dip2px(this,15));

//        webView.loadUrl("http://www.lanou3g.com/");

        webSettings.setDefaultTextEncodingName("UTF-8");
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
}
