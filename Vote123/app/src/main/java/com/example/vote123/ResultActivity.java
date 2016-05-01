package com.example.vote123;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    ImageView header;

    TextView prop1title;
    TextView prop2title;
    TextView prop3title;
    TextView prop4title;
    TextView prop5title;
    TextView prop6title;
    TextView prop7title;
    TextView prop8title;
    TextView prop1answer;
    TextView prop2answer;
    TextView prop3answer;
    TextView pollingAddresTV;
    private String[] propTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        Log.i("Result", "onCreate:1 " + sharedPreferences.getString("PROP1", "DEFAULT MESSAGE"));
        Log.i("Result", "onCreate:2 " + sharedPreferences.getString("PROP2", "DEFAULT MESSAGE"));
        Log.i("Result", "onCreate:3 " + sharedPreferences.getString("PROP3", "DEFAULT MESSAGE"));
        Log.i("Result", "onCreate:Polling " + sharedPreferences.getString("POLLING_ADDRESS", "Go Find your Polling Place"));


        initializeViews();
        header = (ImageView) findViewById(R.id.resultsHeader_id);
        propTitles = getResources().getStringArray(R.array.proptitles);

        prop1title.setText(propTitles[0]);
        prop2title.setText(propTitles[1]);
        prop3title.setText(propTitles[2]);
        prop4title.setText(propTitles[3]);
        prop5title.setText(propTitles[4]);
        prop6title.setText(propTitles[5]);
        prop7title.setText(propTitles[6]);
        prop8title.setText(propTitles[7]);
        prop1answer.setText(sharedPreferences.getString("PROP1", "DEFAULT MESSAGE"));
        prop2answer.setText(sharedPreferences.getString("PROP2", "DEFAULT MESSAGE"));
        prop3answer.setText(sharedPreferences.getString("PROP3", "DEFAULT MESSAGE"));
        pollingAddresTV.setText(sharedPreferences.getString("POLLING_ADDRESS","Use our Polling Place Finder to know where to vote!"));
    }

    private void initializeViews(){
        prop1title = (TextView)findViewById(R.id.prop1NameID);
        prop2title = (TextView)findViewById(R.id.propName2ID);
        prop3title = (TextView)findViewById(R.id.prop3NameID);
        prop4title = (TextView)findViewById(R.id.prop4NameID);
        prop5title = (TextView)findViewById(R.id.prop5NameID);
        prop6title = (TextView)findViewById(R.id.prop6NameID);
        prop7title = (TextView)findViewById(R.id.prop7NameID);
        prop8title = (TextView)findViewById(R.id.prop8NameID);
        prop1answer = (TextView)findViewById(R.id.answer1ID);
        prop2answer = (TextView)findViewById(R.id.answer2ID);
        prop3answer = (TextView)findViewById(R.id.answer3ID);
        pollingAddresTV = (TextView)findViewById(R.id.pollingAddressID);
    }
}
