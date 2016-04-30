package com.example.vote123;

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
    RelativeLayout origPage;
    LinearLayout resultPage;
    TextView questionTV;
    TextView costTV;
    TextView proconTV;
    TextView prop1title;
    TextView prop2title;
    TextView prop3title;
    TextView prop1answer;
    TextView prop2answer;
    TextView prop3answer;
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
    private String[] propTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_understanding_issues);

        userAnswers = new ArrayList<>();
        questions = getResources().getStringArray(R.array.questions);
        costs = getResources().getStringArray(R.array.costs);
        procons = getResources().getStringArray(R.array.procon);
        propTitles = getResources().getStringArray(R.array.proptitles);
        initializeViews();
        initializeListeners();
        nextQuestion();
    }

    private void initializeViews(){
        origPage = (RelativeLayout)findViewById(R.id.original_page_ID);
        resultPage = (LinearLayout)findViewById(R.id.result_page_ID);
        textLayout = (RelativeLayout)findViewById(R.id.textLayoutID);
        proconTV = (TextView)findViewById(R.id.procon_TV_ID);
        questionTV = (TextView)findViewById(R.id.question_TV_ID);
        costTV = (TextView)findViewById(R.id.cost_TV_ID);
        noButton = (Button)findViewById(R.id.NO_button_ID);
        yesButton = (Button)findViewById(R.id.YES_button_ID);
        idkButton = (Button)findViewById(R.id.IDK_button_ID);
        costButton = (Button)findViewById(R.id.cost_button_ID);
        proconButton = (Button)findViewById(R.id.procon_button_ID);
        prop1title = (TextView)findViewById(R.id.prop1NameID);
        prop2title = (TextView)findViewById(R.id.propName2ID);
        prop3title = (TextView)findViewById(R.id.prop3NameID);
        prop1answer = (TextView)findViewById(R.id.answer1ID);
        prop2answer = (TextView)findViewById(R.id.answer2ID);
        prop3answer = (TextView)findViewById(R.id.answer3ID);
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
        textLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTextVisibility(0);
                isQuestion = !isQuestion;
            }
        });
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
//
//        if (isQuestion){
//            questionTV.setVisibility(View.INVISIBLE);
//            costTV.setVisibility(View.VISIBLE);
//        }else{
//            questionTV.setVisibility(View.VISIBLE);
//            costTV.setVisibility(View.INVISIBLE);
//        }
    }
    private void nextQuestion(){
        questionNumber++;
        Log.i(TAG, "nextQuestion: "+questionNumber);
        if (questionNumber>=questions.length-1) {
            Toast.makeText(UnderstandingIssuesActivity.this,"Done",Toast.LENGTH_LONG).show();
            Log.i(TAG, "nextQuestion: end "+questionNumber );
        }else {
            Log.i(TAG, "nextQuestion: "+questionNumber);
            if (!isQuestion) toggleTextVisibility(0);
            questionTV.setText(questions[questionNumber]);
            costTV.setText(costs[questionNumber]);
            proconTV.setText(procons[questionNumber]);
        }

    }
    private void goToLastPage(){
        prop1title.setText(propTitles[0]);
        prop2title.setText(propTitles[1]);
        prop3title.setText(propTitles[2]);
        prop1answer.setText(getAnswer(userAnswers.get(0)));
        prop2answer.setText(getAnswer(userAnswers.get(1)));
        prop3answer.setText(getAnswer(userAnswers.get(2)));
        resultPage.setVisibility(View.VISIBLE);
        origPage.setVisibility(View.INVISIBLE);
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

}
