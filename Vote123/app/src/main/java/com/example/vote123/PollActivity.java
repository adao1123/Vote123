package com.example.vote123;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

/**
 * Created by Billy on 7/5/16.
 */
public class PollActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PollPlaceFragmentTwo pollPlaceFragmentTwo;
    private FrameLayout fragContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setViews();
//
//        if(savedInstanceState == null){
//            setupFragmentOnFirstLoad();
//        }

    }
//
//    private void setViews(){
//        fragmentManager = getSupportFragmentManager();
//        fragContainer = (FrameLayout)findViewById(R.id.frag_container_ID);
//
//    }
//
//    private void setupFragmentOnFirstLoad(){
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.replace(R.id.frag_container_ID, pollPlaceFragmentTwo);
//        fragmentTransaction.commit();
//    }
}
