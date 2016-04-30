package com.example.vote123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class UnderstandingIssuesActivity extends AppCompatActivity {

    RelativeLayout textLayout;
    TextView questionTV;
    TextView costTV;
    Button noButton;
    Button yesButton;
    Button idkButton;
    private boolean isQuestion = true;
    private int questionNumber = 0;
    private ArrayList<Integer> userAnswers;
    private ArrayList<String> questions;
    private ArrayList<String> costs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userAnswers = new ArrayList<>();
        questions = new ArrayList<>();
        costs = new ArrayList<>();
        initializeViews();
        initializeListeners();
        nextQuestion();
    }

    private void initializeViews(){
        textLayout = (RelativeLayout)findViewById(R.id.textLayoutID);
        questionTV = (TextView)findViewById(R.id.question_TV_ID);
        costTV = (TextView)findViewById(R.id.cost_TV_ID);
        noButton = (Button)findViewById(R.id.NO_button_ID);
        yesButton = (Button)findViewById(R.id.YES_button_ID);
        idkButton = (Button)findViewById(R.id.IDK_button_ID);
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
                toggleTextVisibility();
                isQuestion = !isQuestion;
            }
        });
    }
    private void toggleTextVisibility(){
        if (isQuestion){
            questionTV.setVisibility(View.INVISIBLE);
            costTV.setVisibility(View.VISIBLE);
        }else{
            questionTV.setVisibility(View.VISIBLE);
            costTV.setVisibility(View.INVISIBLE);
        }
    }
    private void nextQuestion(){
        if (!isQuestion)toggleTextVisibility();
        questionTV.setText(questions.get(questionNumber));
        costTV.setText(costs.get(questionNumber));
        questionNumber++;

    }
}
