package com.example.busbookingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Website extends AppCompatActivity {
    private static WebView mywebView;
    private static String url="https://www.iitbhilai.ac.in";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        mywebView=(WebView)findViewById(R.id.webview);
        WebSettings webSettings=mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebView.loadUrl(url);
        mywebView.setWebViewClient(new WebViewClient());
    }
    @Override
    public void onBackPressed()
    {
        if(mywebView.canGoBack())
            mywebView.goBack();
        else
            super.onBackPressed();
    }
}
