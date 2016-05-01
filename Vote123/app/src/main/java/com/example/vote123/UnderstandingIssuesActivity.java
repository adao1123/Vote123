package com.example.vote123;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class UnderstandingIssuesActivity extends AppCompatActivity {

    private static final String TAG = "UNDERSTANDING ISSUES";
    RelativeLayout textLayout;
//    RelativeLayout origPage;
//    LinearLayout resultPage;
    TextView questionTV;
    TextView costTV;
    TextView proconTV;
    Button noButton;
    Button yesButton;
    Button idkButton;
    Button costButton;
    Button proconButton;
    private boolean isQuestion = true;
    private int questionNumber = -1;
    private ArrayList<Integer> userAnswers;
    private String[] questions;
    private String[] costs;
    private String[] procons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_understanding_issues);

        userAnswers = new ArrayList<>();
        questions = getResources().getStringArray(R.array.questions);
        costs = getResources().getStringArray(R.array.costs);
        procons = getResources().getStringArray(R.array.procon);
        initializeViews();
        initializeListeners();
        nextQuestion();
    }

    private void initializeViews(){
//        origPage = (RelativeLayout)findViewById(R.id.original_page_ID);
//        resultPage = (LinearLayout)findViewById(R.id.result_page_ID);
        textLayout = (RelativeLayout)findViewById(R.id.textLayoutID);
        proconTV = (TextView)findViewById(R.id.procon_TV_ID);
        questionTV = (TextView)findViewById(R.id.question_TV_ID);
        costTV = (TextView)findViewById(R.id.cost_TV_ID);
        noButton = (Button)findViewById(R.id.NO_button_ID);
        yesButton = (Button)findViewById(R.id.YES_button_ID);
        idkButton = (Button)findViewById(R.id.IDK_button_ID);
        costButton = (Button)findViewById(R.id.cost_button_ID);
        proconButton = (Button)findViewById(R.id.procon_button_ID);
    }
    private void initializeListeners(){
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswers.add(0);
                nextQuestion();
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswers.add(1);
                nextQuestion();
            }
        });
        idkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswers.add(-1);
                nextQuestion();
            }
        });
//        textLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleTextVisibility(0);
//                isQuestion = !isQuestion;
//            }
//        });
        costButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTextVisibility(1);
                isQuestion = !isQuestion;
            }
        });
        proconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTextVisibility(2);
                isQuestion = !isQuestion;
            }
        });
    }
    private void toggleTextVisibility(int n){
        switch (n){
            case 0 :
                questionTV.setVisibility(View.VISIBLE);
                costTV.setVisibility(View.INVISIBLE);
                proconTV.setVisibility(View.INVISIBLE);
                break;
            case 1 :
                questionTV.setVisibility(View.INVISIBLE);
                costTV.setVisibility(View.VISIBLE);
                proconTV.setVisibility(View.INVISIBLE);
                break;
            case 2 :
                questionTV.setVisibility(View.INVISIBLE);
                costTV.setVisibility(View.INVISIBLE);
                proconTV.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
    private void nextQuestion(){
        questionNumber++;
        Log.i(TAG, "nextQuestion: "+questionNumber);
        if (questionNumber>=questions.length-1) {
            Toast.makeText(UnderstandingIssuesActivity.this,"Done",Toast.LENGTH_LONG).show();
            Log.i(TAG, "nextQuestion: end "+questionNumber );
            goToLastPage();
        }else {
            Log.i(TAG, "nextQuestion: "+questionNumber);
            if (!isQuestion) toggleTextVisibility(0);
            questionTV.setText(questions[questionNumber]);
            costTV.setText(costs[questionNumber]);
            proconTV.setText(procons[questionNumber]);
        }
    }
    private void goToLastPage(){
        saveSharedPreferences();
        Intent intent = new Intent(UnderstandingIssuesActivity.this,ResultActivity.class);
        startActivity(intent);
//        resultPage.setVisibility(View.VISIBLE);
//        origPage.setVisibility(View.INVISIBLE);
    }
    private String getAnswer(int num){
        switch (num){
            case 0 :
                return "No";
            case 1 :
                return "Yes";
            case -1:
                return "n/a";
            default:
                return "?";
        }
    }
    private void saveSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PROP1", getAnswer(userAnswers.get(0)));
        editor.putString("PROP2", getAnswer(userAnswers.get(1)));
        editor.putString("PROP3", getAnswer(userAnswers.get(2)));
        editor.commit();
    }
}
