package com.example.vote123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UnderstandingIssuesActivity extends AppCompatActivity {

    TextView questionTV;
    TextView costTV;
    Button noButton;
    Button yesButton;
    Button idkButton;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_understanding_issues);
        initializeViews();

    }

    private void initializeViews(){
        questionTV = (TextView)findViewById(R.id.question_TV_ID);
        costTV = (TextView)findViewById(R.id.cost_TV_ID);
        noButton = (Button)findViewById(R.id.NO_button_ID);
        yesButton = (Button)findViewById(R.id.YES_button_ID);
        idkButton = (Button)findViewById(R.id.IDK_button_ID);
    }
}
