package com.example.vote123;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView titleTextView;
    TextView subTitleTextView;
    Button registerButton;
    Button pollingButton;
    Button issuesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setButtons();
        SharedPreferences sharedPreferences = getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        Log.i("Main", "onCreate:1 "+sharedPreferences.getString("PROP1", "DEFAULT MESSAGE"));
        Log.i("Main", "onCreate:2 "+sharedPreferences.getString("PROP2", "DEFAULT MESSAGE"));
        Log.i("Main", "onCreate:3 "+sharedPreferences.getString("PROP3", "DEFAULT MESSAGE"));

        setFonts();

    }

    private void setFonts(){
        Typeface typeface = Typeface.createFromAsset(getAssets(), "DIN_Neuzeit_Grotesk_W01_Bold.ttf");
        subTitleTextView.setTypeface(typeface);
    }

    private void initializeViews(){
        titleTextView = (TextView) findViewById(R.id.main_title_textView_id);
        subTitleTextView = (TextView) findViewById(R.id.main_subTitle_textView_id);
        registerButton = (Button) findViewById(R.id.main_register_button_id);
        pollingButton = (Button) findViewById(R.id.main_polling_button_id);
        issuesButton = (Button) findViewById(R.id.main_issues_button_id);
    }

    private void setButtons(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        pollingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PollingActivity.class);
                startActivity(intent);
            }
        });

        issuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UnderstandingIssuesActivity.class);
                startActivity(intent);
            }
        });
    }



}
