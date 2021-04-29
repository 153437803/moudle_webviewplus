package com.kalu.webviewplus;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import lib.kalu.webviewplus.WebViewLayout;
import lib.kalu.webviewplus.WebViewPlus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        WebViewPlus webViewPlus = findViewById(R.id.webview);
////        webViewPlus.loadUrl("http://umspos.caitc.cn/cajinfu-wx-web/#/index");
//         webViewPlus.loadUrl("https://etrust.caitc.cn/etrust/hybrid/#/index");

        WebViewLayout webViewLayout = findViewById(R.id.webview);
        webViewLayout.loadUrl("https://etrust.caitc.cn/etrust/hybrid/#/index");
        webViewLayout.setOnWebStatusChangeListener(new WebViewPlus.OnWebStatusChangeListener() {
            @Override
            public void onPageStarted(@NonNull WebView view, @NonNull String url) {
                Log.d("MainActivity", "onPageStarted => url = " + url);
            }

            @Override
            public void onPageFinished(@NonNull WebView view, @NonNull String url) {
                Log.d("MainActivity", "onPageFinished => url = " + url);
            }

            @Override
            public void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, int newProgress) {
                Log.d("MainActivity", "onProgressChanged => targetUrl = " + targetUrl + ", newProgress = " + newProgress);
            }

            @Override
            public void onJsTimeout() {

            }

            @Override
            public void onJsBeforeUnload(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {

            }

            @Override
            public void onJsAlert(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {

            }

            @Override
            public void onJsConfirm(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {

            }

            @Override
            public void onJsPrompt(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull String defaultValue, @NonNull JsPromptResult result) {

            }
        });
    }
}