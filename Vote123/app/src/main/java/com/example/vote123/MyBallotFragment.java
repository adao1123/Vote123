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
    private Firebase firebaseRef;
    private Firebase firebaseState;
    private Firebase firebaseQuestions;
    private Firebase firebaseQuestion;
    private String selectedState;
    private int numQuestions;

    public MyBallotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_ballot, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        savedAnswers = new ArrayList<>();
        stateQuestions = new ArrayList<>();
        manageFirebase();
        getAnswersFromPref();
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
        getStateQuestions();
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
                    savedAnswers.add((String) dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        return savedAnswers;
    }
}
