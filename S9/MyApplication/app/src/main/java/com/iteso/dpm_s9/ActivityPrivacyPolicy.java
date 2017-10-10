package com.iteso.dpm_s9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ActivityPrivacyPolicy extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        webView = (WebView)findViewById(R.id.activity_privacy_policy_web_view);
        webView.loadUrl("file//android_asset/PrivacyPolicy.html");
    }
}
