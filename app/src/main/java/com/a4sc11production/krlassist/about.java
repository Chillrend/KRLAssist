package com.a4sc11production.krlassist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("file:///android_asset/index.html");
    }
}
