package com.example.vote123;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Billy on 7/3/16.
 */
public class PollPlaceFragment extends Fragment {

    private TextView infoText;
    private EditText streetEdit;
    private EditText cityEdit;
    private EditText zipEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.poll_fragment_one, container, false);
        setRetainInstance(true);
        setViews(v);

        return v;
    }

    private void setViews(View v){
        infoText = (TextView)v.findViewById(R.id.pollOne_text_ID);
        streetEdit = (EditText)v.findViewById(R.id.pollOne_streetEdit_id);
        cityEdit = (EditText)v.findViewById(R.id.pollOne_cityEdit_id);
        zipEdit = (EditText)v.findViewById(R.id.pollOne_zipEdit_id);
    }
}
