package com.example.vote123;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class PollingActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PollPlaceFragmentTwo pollPlaceFragmentTwo;
    private FrameLayout fragContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);

        setViews();

        if(savedInstanceState == null){
            setupFragmentOnFirstLoad();
        }
    }


    private void setViews(){
        pollPlaceFragmentTwo = new PollPlaceFragmentTwo();
        fragContainer = (FrameLayout)findViewById(R.id.frag_container_ID);
        fragmentManager = getSupportFragmentManager();
    }

    private void setupFragmentOnFirstLoad(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frag_container_ID, pollPlaceFragmentTwo);
        fragmentTransaction.commit();
    }

}
