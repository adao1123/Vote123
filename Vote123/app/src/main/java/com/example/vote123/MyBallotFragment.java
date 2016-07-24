package com.example.vote123;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyBallotFragment extends Fragment {

    private static final String TAG = "MY BALLOT FRAGMENT";
    private ArrayList<String> savedAnswers;
    private ArrayList<String> stateQuestions;
    private ArrayList<String> propNames;
    private Firebase firebaseRef;
    private Firebase firebaseState;
    private Firebase firebaseQuestions;
    private Firebase firebaseQuestion;
    private String selectedState;
    private int numQuestions;
    private TextView name1TextView;
    private TextView name2TextView;
    private TextView name3TextView;
    private TextView question1TextView;
    private TextView question2TextView;
    private TextView question3TextView;
    private ImageView yes1ImageView;
    private ImageView yes2ImageView;
    private ImageView yes3ImageView;
    private ImageView no1ImageView;
    private ImageView no2ImageView;
    private ImageView no3ImageView;

    public MyBallotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_ballot, container, false);
        initializeViews(v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        savedAnswers = new ArrayList<>();
        stateQuestions = new ArrayList<>();
        propNames = new ArrayList<>();
        manageFirebase();
        getAnswersFromPref();

//        setNameTextViews();
//        setQTextViews();
        setTempInfo();
        setBubbles();
    }

    private void setTempInfo(){
        name1TextView.setText("California \"Fair Wage Act of 2016\" - Increases the minimum wage in California to $15 by 2021.");
        name2TextView.setText("Proposition 52 - Requires voter approval of changes to the hospital fee program");
//        name3TextView.setText("Proposition 58 - Repeals Prop 227 of 1998, thus allowing for bilingual education in public schools");
        question1TextView.setText("Do you think that the minimum wage should be increased to $15 by 2021?");
        question2TextView.setText("Do you think changes to the hospital fee program should require voter approval?");
//        question3TextView.setText("Do you think that we should allow bilingual education in public schools.");
    }

    private void initializeViews(View v){
        name1TextView = (TextView) v.findViewById(R.id.myBallot_name1_tVid);
        name2TextView = (TextView) v.findViewById(R.id.myBallot_name2_tVid);
        name3TextView = (TextView) v.findViewById(R.id.myBallot_name3_tVid);
        question1TextView = (TextView) v.findViewById(R.id.myBallot_question1_tVid);
        question2TextView = (TextView) v.findViewById(R.id.myBallot_question2_tVid);
        question3TextView = (TextView) v.findViewById(R.id.myBallot_question3_tVid);
        yes1ImageView = (ImageView) v.findViewById(R.id.myBallot_yes1_iVid);
        yes2ImageView = (ImageView) v.findViewById(R.id.myBallot_yes2_iVid);
        yes3ImageView = (ImageView) v.findViewById(R.id.myBallot_yes3_iVid);
        no1ImageView = (ImageView) v.findViewById(R.id.myBallot_no1_iVid);
        no2ImageView = (ImageView) v.findViewById(R.id.myBallot_no2_iVid);
        no3ImageView = (ImageView) v.findViewById(R.id.myBallot_no3_iVid);
    }

    private String getStateFromPref(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        Log.i("Result", "onCreate:1 " + sharedPreferences.getString("STATE", "DEFAULT MESSAGE"));
        if (sharedPreferences.getString("STATE","Default")!=null)
        selectedState = sharedPreferences.getString("STATE","DEFAULT");
        return selectedState;
    }

    private ArrayList<String> getAnswersFromPref(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        Log.i("Result", "onCreate:1 " + sharedPreferences.getString("STATE", "DEFAULT MESSAGE"));
        for (int i = 0; i<numQuestions; i++){
            savedAnswers.add(sharedPreferences.getString("ANSWER"+i,"DEFAULT"));
        }
//        if (sharedPreferences.getString("STATE","Default")!=null)
        Log.i(TAG, "getAnswersFromPref: "+savedAnswers.toString());
        return savedAnswers;
    }

    private void manageFirebase(){
        getStateFromPref();
        initFirebase();
        getNumQuestions();
    }

    private void initFirebase(){
        firebaseRef = new Firebase("https://123vote.firebaseio.com/");
        firebaseState = firebaseRef.child(getStateFromPref());
        firebaseQuestions = firebaseState.child("Question");
    }

    private void getNumQuestions(){
        firebaseQuestions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numQuestions = (int)dataSnapshot.getChildrenCount();
                Log.i(TAG, "onDataChange: "+numQuestions);
                getStateQuestions();
                getPropNames();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private ArrayList<String> getStateQuestions() {
        for (int i = 1; i <= numQuestions; i++) {
            firebaseQuestion = firebaseQuestions.child(i + "").child("Prompt");
            firebaseQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    stateQuestions.add((String) dataSnapshot.getValue());
                    Log.i(TAG, "stateQuestions: " + stateQuestions);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.e(TAG, "onCancelled: " +firebaseError.getMessage());
                }
            });
        }
        return stateQuestions;
    }
    private ArrayList<String> getPropNames() {
        for (int i = 1; i <= numQuestions; i++) {
            firebaseQuestion = firebaseQuestions.child(i + "").child("Prop");
            firebaseQuestion.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    propNames.add((String) dataSnapshot.getValue());
                    Log.i(TAG, "propNames: " + propNames);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.e(TAG, "onCancelled: " +firebaseError.getMessage());

                }
            });
        }
        return propNames;
    }

    private void setNameTextViews(){
        Log.i(TAG, "setViews: propNames array has a size of" + propNames.size());
        if(propNames.size() == 0){
            return;
        } else{
            name1TextView.setText(propNames.get(0));
            name2TextView.setText(propNames.get(1));
            name3TextView.setText(propNames.get(2));
        }
    }

    private void setQTextViews(){
        if(stateQuestions.size() == 0){
            return;
        } else{
            question1TextView.setText(stateQuestions.get(0));
            question2TextView.setText(stateQuestions.get(1));
            question3TextView.setText(stateQuestions.get(2));
        }
    }

    private void setBubbles(){
        if(savedAnswers.size() == 0){
            return;
        } else {
            bubble1(savedAnswers.get(0));
            bubble2(savedAnswers.get(1));
            bubble3(savedAnswers.get(2));
        }
    }

    private void bubble1(String answer){
        if(answer == null){
            return;
        }
        switch(answer){
            case "Yes":
                yes1ImageView.setImageResource(R.drawable.fill_icon);
                break;
            case "No":
                no1ImageView.setImageResource(R.drawable.fill_icon);
                break;
            default:
                break;
        }
    }

    private void bubble2(String answer){
        if(answer == null){
            return;
        }
        switch(answer){
            case "Yes":
                yes2ImageView.setImageResource(R.drawable.fill_icon);
                break;
            case "No":
                no2ImageView.setImageResource(R.drawable.fill_icon);
                break;
            default:
                break;
        }
    }

    private void bubble3(String answer){
        if(answer == null){
            return;
        }
        switch(answer){
            case "Yes":
                yes3ImageView.setImageResource(R.drawable.fill_icon);
                break;
            case "No":
                no3ImageView.setImageResource(R.drawable.fill_icon);
                break;
            default:
                break;
        }
    }


}
