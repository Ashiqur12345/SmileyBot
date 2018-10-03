package com.betabots.team.smileybot;

import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SmileyBot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smiley_bot);

        final WebView webView = findViewById(R.id.webview);

//        webView.setWebChromeClient(new WebChromeClient());
//        webView.getSettings().getJavaScriptEnabled();
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);


        webView.loadUrl("http://www.ashman.somee.com/");
    }
}
