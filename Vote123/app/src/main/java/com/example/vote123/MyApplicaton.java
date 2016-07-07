package com.example.vote123;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by adao1 on 7/6/2016.
 */
public class MyApplicaton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
