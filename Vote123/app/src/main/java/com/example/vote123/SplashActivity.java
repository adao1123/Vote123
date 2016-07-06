package com.example.vote123;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by adao1 on 7/6/2016.
 */

public class SplashActivity extends Activity {
    private static boolean splashLoaded = false;
//    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!splashLoaded) {
            setContentView(R.layout.activity_splash);
            int secondsDelayed = 1;
//            setFont();

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, NaviActivity.class));
                    finish();
                }
            }, secondsDelayed * 1000);

            splashLoaded = true;
        }
        else {
            Intent goToFacebookActivity = new Intent(SplashActivity.this, NaviActivity.class);
            goToFacebookActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToFacebookActivity);
            finish();
        }
    }
//    private void setFont(){
//        textView = (TextView)findViewById(R.id.appName_TV_ID);
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "Amatic_Bold.ttf");
//        textView.setTypeface(typeface);
//    }

}
