package com.anfly.finalproject.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.anfly.finalproject.R;

/**
 * 如果使用androidX WebView会崩溃，webkit
 * 在gradle添加依赖
 * configurations.all {
 *    resolutionStrategy.force 'androidx.appcompat:appcompat:1.1.0-beta01'
 *  }
 */
public class WebViewActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        webview = (WebView) findViewById(R.id.webview);
        String link = getIntent().getStringExtra("link");
        webview.loadUrl(link);
        webview.setWebViewClient(new WebViewClient());
    }
}
