package com.example.vote123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegistrationActivity extends AppCompatActivity {
    WebView webView;
    CustomWebViewClient customWebViewClient;

    private final String TAG = getClass().getName();
    private final String URL = "http://registertovote.ca.gov";

    //https://covr.sos.ca.gov/Home/Confirmation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        webView = (WebView) findViewById(R.id.register_webView_id);
        customWebViewClient = new CustomWebViewClient();
        webView.setWebViewClient(customWebViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);

    }

    private class CustomWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //put stuff here and change return
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);


            //put stuff here
        }
    }
}
