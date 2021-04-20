package com.kalu.webviewplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import lib.kalu.webviewplus.WebViewPlus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebViewPlus webViewPlus = findViewById(R.id.webview);
        webViewPlus.loadUrl("http://umspos.caitc.cn/cajinfu-wx-web/#/index");
    }
}