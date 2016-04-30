package com.example.vote123;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindPollingActivity extends AppCompatActivity {

    private static final String TAG = "FindPollingActivity";
    private static final String API_KEY = "AIzaSyBAy7FDJ0OB6tB1TjKkubsVr24qk9qxxUQ";
    private static final String ELECTION_ID = "2000";
    public static final String INPUT_ADDRESS_KEY = "INPUTKEY";
    public static final String POLLING_ADDRESS_KEY = "POLLINGKEY";
    EditText editStreet;
    EditText editCity;
    EditText editState;
    EditText editZip;
    Button button;
    TextView textView;
    String inputedAddress;
    String returnAddress;
    CivicInfoApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_polling);

        editStreet = (EditText)findViewById(R.id.editTextStreetName);
        editCity = (EditText)findViewById(R.id.editTextCity);
        editState = (EditText)findViewById(R.id.editTextState);
        editZip = (EditText)findViewById(R.id.editTextZip);
        button = (Button)findViewById(R.id.buttonID);
        textView = (TextView)findViewById(R.id.pollingAddress);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/civicinfo/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(CivicInfoApiService.class);
        buttonListener();
    }

    private void buttonListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputedAddress = editStreet.getText().toString() + " " + editCity.getText() + " " + editState.getText() + " " + editZip.getText();
                Call<AddressData> call = service.getAddressInfo(inputedAddress,API_KEY,ELECTION_ID);
                call.enqueue(new Callback<AddressData>() {
                    @Override
                    public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                        AddressData.PollingPlace pollingPlace = response.body().getPollingLocations()[0];
                        AddressData.PollingPlace.Address pollingAddress = pollingPlace.getPollingPlaceAddress();
                        returnAddress = pollingAddress.getAddressStreet() + " " + pollingAddress.getAddressCity() + " " + pollingAddress.getAddressState() + " " + pollingAddress.getAddressZip();
                        inputedAddress = inputedAddress.toUpperCase();
                        returnAddress = returnAddress.toUpperCase();
                        Log.i(TAG, "Input Address: " + inputedAddress);
                        Log.i(TAG, "Return Address: " + returnAddress);
                        textView.setText(returnAddress);
                    }

                    @Override
                    public void onFailure(Call<AddressData> call, Throwable t) {

                    }
                });
            }
        });
    }
}
