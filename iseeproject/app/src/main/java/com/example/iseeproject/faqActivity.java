package com.example.iseeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class faqActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        //Get a reference to your WebView//
        webView = (WebView) findViewById(R.id.webView1);

//Specify the URL you want to display//
        webView.loadUrl("file:///android_asset/faq1.html");

    }

}
