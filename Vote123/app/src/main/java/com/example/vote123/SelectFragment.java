package com.example.vote123;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "SELECT FRAGMENT";
    private Spinner spinner;
    private String selectedState;
    private Firebase firebase;
    private ImageView flagIV;
    private ImageView stateIV;
    private LinearLayout imageBorder;
    private LinearLayout flagBorder;
    OnDialogAnswerListener onDialogAnswerListener;

//    private String stateImageUri, flagUri;

    public SelectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select, container, false);
        spinner = (Spinner)view.findViewById(R.id.select_state_spinner_id);
        flagIV = (ImageView)view.findViewById(R.id.select_state_flag);
        stateIV = (ImageView)view.findViewById(R.id.select_state_image);
        imageBorder = (LinearLayout)view.findViewById(R.id.select_border1_id);
        flagBorder = (LinearLayout)view.findViewById(R.id.select_border2_id);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpSpinner();
//        setImageUrlsToFB();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onDialogAnswerListener = (OnDialogAnswerListener)getActivity();
    }

    private void setUpSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.states_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void manageFirebase(){
        firebase = new Firebase("https://123vote.firebaseio.com/");
    }

    private void showImages(String uri, ImageView IV){
        Picasso.with(getContext()).load(uri).into(IV);
//        Picasso.with(getContext()).load(flagUri).into(flagIV);
        Log.i(TAG, "showImages: ");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedState = (String)parent.getItemAtPosition(position);
        if (selectedState.equals("Pick your State")){
            imageBorder.setVisibility(View.INVISIBLE);
            flagBorder.setVisibility(View.INVISIBLE);
            return;
        }
        imageBorder.setVisibility(View.VISIBLE);
        flagBorder.setVisibility(View.VISIBLE);
        Log.i(TAG, "onItemSelected: "+ selectedState);
        manageFirebase();
        firebase.child(selectedState).child("StatesImage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String flagUri = (String)dataSnapshot.getValue();
                showImages(flagUri,stateIV);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        firebase.child(selectedState).child("StateFlag").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String stateImageUri = (String) dataSnapshot.getValue();
                showImages(stateImageUri,flagIV);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        if (isFirstTime())initDialog();
        saveStateToPref();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(TAG, "onNothingSelected: ");
    }

    private void saveStateToPref(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("STATE",selectedState);
        editor.putString("FIRST","HERE");
        editor.commit();

    }

    private boolean isFirstTime(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("FIRST","default").equals("default"))return true;
        if (!sharedPreferences.getString("FIRST","default").equals("HERE"))return true;
        else return false;
    }

    private void initDialog(){
        AlertDialog.Builder dialogBuidler = new AlertDialog.Builder(getContext());
        dialogBuidler.setMessage("Are you registered to vote?");
        dialogBuidler.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogAnswerListener.OnDialogAnswer("YES");
            }
        });
        dialogBuidler.setNegativeButton("Not Yet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogAnswerListener.OnDialogAnswer("NO");
            }
        });
        AlertDialog alertDialog = dialogBuidler.create();
        alertDialog.show();
//        alertDialog.setContentView();
    }


    private void setImageUrlsToFB(){
        manageFirebase();
        String[] stateNames = getResources().getStringArray(R.array.states_array);
        stateNames = Arrays.copyOfRange(stateNames,1,stateNames.length);
        String[] stateAbs = {"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
        Log.i(TAG, "setImageUrlsToFB: "+stateNames);
        Log.i(TAG, "setImageUrlsToFB: "+stateNames.length);
        Log.i(TAG, "setImageUrlsToFB: "+stateAbs);
        Log.i(TAG, "setImageUrlsToFB: "+stateAbs.length);
        for (int i = 0; i < stateNames.length; i++){
            Firebase stateFB = firebase.child(stateNames[i]);
            stateFB.child("StateFlag").setValue("http://www.50states.com/images/redesign/flags/"+stateAbs[i].toLowerCase()+"-largeflag.png");
            stateFB.child("StatesImage").setValue("http://www.50states.com/images/redesign/maps/"+stateAbs[i].toLowerCase()+"-largemap.png");
        }
    }

    public interface OnDialogAnswerListener{
        public void OnDialogAnswer(String answer);
    }
}
