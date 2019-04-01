package com.example.busbookingapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DeveloperHandler extends AppCompatActivity {

    private static String url="";
    private static WebView browser;
    MediaPlayer start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_handler);
        browser=(WebView) findViewById(R.id.webview);
        Intent getintent=getIntent();
        url=getintent.getStringExtra("url");
        start= MediaPlayer.create(DeveloperHandler.this,R.raw.start);
        if(start.isPlaying())
            start.stop();
        start.start();
        openUrl();
    }
    public void openUrl()
    {
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.loadUrl(url);
        browser.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed()
    {
        if(browser.canGoBack())
            browser.goBack();
        else
            super.onBackPressed();
//        (browser.canGoBack())?(browser.goBack()):(super.onBackPressed());
    }
}
