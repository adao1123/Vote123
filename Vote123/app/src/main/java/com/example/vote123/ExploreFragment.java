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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    private static final String TAG = "EXPLORE";
    private Firebase firebaseRef;
    private Firebase firebaseState;
    private Firebase firebaseQuestions;
    private Firebase firebaseQuestion;
    private String selectedState;
    private int numQuestions;
    private ArrayList<String> questionArray;
    private ArrayList<String> costArray;
    private ArrayList<String> proconArray;
    private TextView questionTV;
    private TextView titleTV;
    private ImageButton proconButton;
    private ImageButton costButton;
    private ImageButton questionButton;
    private ImageButton yesButton;
    private ImageButton maybeButton;
    private ImageButton noButton;
    private int currentQuestionNum;
    private GoToMyBallotListener goToMyBallotListener;
    private String[] savedAnswers;


    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_explore, container, false);
        questionTV = (TextView)view.findViewById(R.id.explore_question_TV_id);
        titleTV = (TextView)view.findViewById(R.id.explore_title);
        proconButton = (ImageButton)view.findViewById(R.id.explore_procon_button_id);
        questionButton = (ImageButton)view.findViewById(R.id.explore_question_button_id);
        costButton = (ImageButton)view.findViewById(R.id.explore_cost_button_id);
        yesButton = (ImageButton)view.findViewById(R.id.explore_yes_button_id);
        noButton = (ImageButton)view.findViewById(R.id.explore_no_button_id);
        maybeButton = (ImageButton)view.findViewById(R.id.explore_maybe_button_id);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentQuestionNum = 1;
        getSelectedState();
        manageFirebase();
        setInitQuestion();
        setButtonListeners();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        goToMyBallotListener = (GoToMyBallotListener)getActivity();
    }

    private String getSelectedState(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        Log.i("Result", "onCreate:1 " + sharedPreferences.getString("STATE", "DEFAULT MESSAGE"));
//        if (sharedPreferences.getString("STATE","Default")!=null)
        selectedState = sharedPreferences.getString("STATE","DEFAULT");
        return selectedState;
    }

    private void manageFirebase(){
        initFirebase();
        getNumQuestions();
//        addQuestion();
    }

    private void initFirebase(){
        firebaseRef = new Firebase("https://123vote.firebaseio.com/");
        firebaseState = firebaseRef.child(selectedState);
        firebaseQuestions = firebaseState.child("Question");
    }

    private void setInitQuestion(){
        displayContent(1,'?');
    }

    private void setButtonListeners(){
        proconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedAnswers==null)return;
                displayContent(currentQuestionNum,'+');
                titleTV.setText("Pros / Cons");
            }
        });
        costButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedAnswers==null)return;
                displayContent(currentQuestionNum,'$');
                titleTV.setText("Costs");
            }
        });
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedAnswers==null)return;
                displayContent(currentQuestionNum,'?');
                titleTV.setText("Question");
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedAnswers==null)return;
                savedAnswers[currentQuestionNum-1] = "No";
                currentQuestionNum++;
                if (currentQuestionNum>numQuestions){
                    saveAnsersToPref();
                    goToMyBallotListener.goToMyBallot();
                }
                else displayContent(currentQuestionNum,'?');
            }
        });
        maybeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedAnswers==null)return;
                savedAnswers[currentQuestionNum-1] = "Maybe";
                currentQuestionNum++;
                if (currentQuestionNum>numQuestions) {
                    saveAnsersToPref();
                    goToMyBallotListener.goToMyBallot();
                }
                else displayContent(currentQuestionNum,'?');
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedAnswers==null)return;
                savedAnswers[currentQuestionNum-1] = "Yes";
                currentQuestionNum++;
                if (currentQuestionNum>numQuestions) {
                    saveAnsersToPref();
                    goToMyBallotListener.goToMyBallot();
                }
                else displayContent(currentQuestionNum,'?');
            }
        });
    }

    private void displayContent(int questionNumber,char prompt){
        firebaseQuestion = firebaseQuestions.child(questionNumber+"");
        Firebase firebaseTV;
        switch (prompt){
            case '$':
                firebaseTV = firebaseQuestion.child("Cost");
                break;
            case '+':
                firebaseTV = firebaseQuestion.child("Procon");
                break;
            case '?':
                firebaseTV = firebaseQuestion.child("Prompt");
                break;
            default:
                firebaseTV = firebaseQuestion.child("Prompt");
                break;
        }
        firebaseTV.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = (String) dataSnapshot.getValue();
                questionTV.setText(text);
                Log.i(TAG, "onDataChange: "+text);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getNumQuestions(){
        firebaseQuestions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numQuestions = (int)dataSnapshot.getChildrenCount();
                savedAnswers = new String[numQuestions];
                Log.i(TAG, "onDataChange: "+numQuestions);
//                makeStringLists();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void saveAnsersToPref(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i<numQuestions; i++){
            editor.putString("ANSWER"+i,savedAnswers[i]);
        }
        editor.commit();

    }

    public interface GoToMyBallotListener{
        void goToMyBallot();
    }
//
//    private void addQuestion(){
//        firebaseQuestion = firebaseQuestions.child("1");
//        firebaseQuestion.setValue("Question1");
//        firebaseQuestion.child("Cost").setValue("cost");
//        firebaseQuestion.child("Procon").setValue("procon");
//    }
//
//    private void makeStringLists(){
//        questionArray = new ArrayList<>();
//        costArray = new ArrayList<>();
//        proconArray = new ArrayList<>();
//        for (int i = 1; i<=numQuestions; i++){
//            firebaseQuestions.child(i+"").child("Prompt").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    questionArray.add((String)dataSnapshot.getValue());
//                    Log.i(TAG, "Question 1: " + questionArray.get(0));
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//
//                }
//            });
//            firebaseQuestions.child(i+"").child("Cost").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    costArray.add((String)dataSnapshot.getValue());
//                    Log.i(TAG, "Cost 1: " + costArray.get(0));
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//
//                }
//            });
//            firebaseQuestions.child(i+"").child("Procon").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    proconArray.add((String)dataSnapshot.getValue());
//                    Log.i(TAG, "Procon 1: " + proconArray.get(0));
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//
//                }
//            });
//
//        }
//        Log.i(TAG, "makeStringLists: ");
//    }
}
