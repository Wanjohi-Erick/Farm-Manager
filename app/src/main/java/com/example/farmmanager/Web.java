package com.example.farmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Web extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");

        webView = findViewById(R.id.webView);
        webView.loadUrl(url);
        //todo Modify webview to increase functionality
    }
}