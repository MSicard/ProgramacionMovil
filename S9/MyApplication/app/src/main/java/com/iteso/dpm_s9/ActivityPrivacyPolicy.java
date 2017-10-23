package com.iteso.dpm_s9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.iteso.dpm_s9.utils.WebAppInterface;

public class ActivityPrivacyPolicy extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        WebView mWebView = null;
        mWebView = (WebView) findViewById(R.id.activity_privacy_policy_web_view);

        mWebView.loadUrl("file:///android_asset/PrivacyPolicy.html");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");


    }
}
