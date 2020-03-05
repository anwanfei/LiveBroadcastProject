package com.anfly.wanandroid;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewAcivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_acivity);
        initView();
    }

    private void initView() {
        String link = getIntent().getStringExtra("link");
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());

    }
}
