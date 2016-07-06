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

import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    Firebase firebaseRef;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
        Log.i("Result", "onCreate:1 " + sharedPreferences.getString("STATE", "DEFAULT MESSAGE"));

    }

    private void initFirebase(){
        firebaseRef = new Firebase("https://123vote.firebaseio.com/");
//        firebaseShops = firebaseRef.child("Shops");
    }

}
