package com.example.vote123;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Billy on 7/3/16.
 */
public class PollPlaceFragmentTwo extends Fragment {

    private TextView infoText;
    private TextView addressText;
    private EditText streetEdit;
    private EditText cityEdit;
    private EditText stateEdit;
    private EditText zipEdit;
    private Button submitButton;
    private CivicInfoApiService service;
    private String inputedAddress;
    private String returnAddress;
    private static final String API_KEY = "AIzaSyBAy7FDJ0OB6tB1TjKkubsVr24qk9qxxUQ";
    private static final String ELECTION_ID = "2000";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.poll_fragment_two, container, false);
        setViews(v);
        setRetainInstance(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/civicinfo/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(CivicInfoApiService.class);

        buttonListener();

        return v;
    }

    private void setViews(View v){
        addressText = (TextView)v.findViewById(R.id.pollTwo_addressStreet_ID);
        infoText = (TextView)v.findViewById(R.id.pollTwo_text_ID);
        streetEdit = (EditText)v.findViewById(R.id.pollTwo_streetEdit_id);
        cityEdit = (EditText)v.findViewById(R.id.pollTwo_cityEdit_id);
        stateEdit = (EditText)v.findViewById(R.id.pollTwo_stateEdit_id);
        zipEdit = (EditText)v.findViewById(R.id.pollTwo_zipEdit_id);
        submitButton = (Button)v.findViewById(R.id.pollTwo_submitButton_ID);
    }

    private void buttonListener() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputedAddress = streetEdit.getText().toString() + " " + cityEdit.getText() + " " + stateEdit.getText() + " " + zipEdit.getText();
                Call<AddressData> call = service.getAddressInfo(inputedAddress, API_KEY, ELECTION_ID);
                call.enqueue(new Callback<AddressData>() {
                    @Override
                    public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                        if (response.body().getPollingLocations()[0] == null) return;
                        AddressData.PollingPlace pollingPlace = response.body().getPollingLocations()[0];
                        AddressData.PollingPlace.Address pollingAddress = pollingPlace.getPollingPlaceAddress();
                        returnAddress = pollingAddress.getAddressStreet() + "\n " + pollingAddress.getAddressCity() + " " + pollingAddress.getAddressState() + " " + pollingAddress.getAddressZip();
                        inputedAddress = inputedAddress.toUpperCase();
                        returnAddress = returnAddress.toUpperCase();
                        addressText.setText(returnAddress);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("POLLING_ADDY", returnAddress);
                        editor.commit();

                    }

                    @Override
                    public void onFailure(Call<AddressData> call, Throwable t) {

                    }
                });
            }
        });
    }

}
