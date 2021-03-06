package com.example.vote123;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    ImageView header;
    WebView webView;
    CustomWebViewClient customWebViewClient;
    Toolbar toolbar;
    Button pollingButton;
    Button shareButton;

    private final String TAG = getClass().getName();
    private final String URL = "http://registertovote.ca.gov";
    private final String doneRegisteringURL = "https://covr.sos.ca.gov/Home/Confirmation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeViews();
        setClickListeners();
        customWebViewClient = new CustomWebViewClient();
        webView.setWebViewClient(customWebViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);
        customWebViewClient.onPageFinished(webView, doneRegisteringURL);
//        if (webView.getUrl()==doneRegisteringURL){
//            customWebViewClient.onPageFinished(webView, doneRegisteringURL);
//        }
    }

    private void initializeViews(){
        header = (ImageView) findViewById(R.id.register_header_imageView_id);
        webView = (WebView) findViewById(R.id.register_webView_id);
        toolbar = (Toolbar) findViewById(R.id.register_toolbar_id);
        pollingButton = (Button) toolbar.findViewById(R.id.register_pollingStep_button_id);
        shareButton = (Button) toolbar.findViewById(R.id.register_share_button_id);
    }

    private void setClickListeners(){
        pollingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, FindPollingActivity.class);
                startActivity(intent);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri imageURI = Uri.parse("http://imgur.com/hXVOBNe"); //was  "http://i.imgur.com/hXVOBNe.png"
//                Uri imageURI = Uri.parse("http://i.imgur.com/hXVOBNe"); //was "android.resource://" + getPackageName() + "/drawable/" + "iamregisteredareyou.png"
                String message = "I just registered to vote using Vote123! It was super easy to use and I'm excited to vote in the upcoming election.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("*/*");//share.setType("text/plain").setType("image/*")
                share.putExtra(Intent.EXTRA_TEXT, message);
//                share.putExtra(Intent.EXTRA_STREAM, R.drawable.iamregisteredareyou);
                startActivity(Intent.createChooser(share, "Spread the word so others can get out & vote on November 8, 2016!"));
            }
        });
    }

    private class CustomWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url); //try return false;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
//            if (view.getUrl()==url){ //was webview.getUrl()==doneRegisteringURL
//                toolbar.setVisibility(View.VISIBLE);
//                Toast.makeText(RegistrationActivity.this, "Congrats! You're officially registered to vote in the state of California.", Toast.LENGTH_LONG).show();
//            }
            super.onPageFinished(view, url);

        }
    }
}
