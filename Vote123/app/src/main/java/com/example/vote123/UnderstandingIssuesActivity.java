package com.example.vote123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class UnderstandingIssuesActivity extends AppCompatActivity {

    RelativeLayout textLayout;
    TextView questionTV;
    TextView costTV;
    TextView proconTV;
    Button noButton;
    Button yesButton;
    Button idkButton;
    Button costButton;
    Button proconButton;
    private boolean isQuestion = true;
    private int questionNumber = 0;
    private ArrayList<Integer> userAnswers;
    private String[] questions;
    private String[] costs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_understanding_issues);

        userAnswers = new ArrayList<>();
        questions = getResources().getStringArray(R.array.questions);
        costs = getResources().getStringArray(R.array.costs);
        initializeViews();
        initializeListeners();
        nextQuestion();
    }

    private void initializeViews(){
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
                questionTV.setVisibility(View.VISIBLE);
                costTV.setVisibility(View.INVISIBLE);
                proconTV.setVisibility(View.VISIBLE);
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
        if (!isQuestion)toggleTextVisibility(0);
        questionTV.setText(questions[questionNumber]);
        costTV.setText(costs[questionNumber]);
        if (questionNumber<questions.length)questionNumber++;
        else Toast.makeText(UnderstandingIssuesActivity.this,"Done",Toast.LENGTH_LONG).show();

    }
}
