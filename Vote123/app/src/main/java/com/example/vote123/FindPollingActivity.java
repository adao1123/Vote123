package com.example.vote123;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

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
    private Button calendarButton;
    private Button shareButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_polling);

        shareButton = (Button)findViewById(R.id.registered_shareButton);
        calendarButton = (Button)findViewById(R.id.add_election_date);
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
                        if (response.body().getPollingLocations()[0]==null) return;
                        AddressData.PollingPlace pollingPlace = response.body().getPollingLocations()[0];
                        AddressData.PollingPlace.Address pollingAddress = pollingPlace.getPollingPlaceAddress();
                        returnAddress = pollingAddress.getAddressStreet() + " " + pollingAddress.getAddressCity() + " " + pollingAddress.getAddressState() + " " + pollingAddress.getAddressZip();
                        inputedAddress = inputedAddress.toUpperCase();
                        returnAddress = returnAddress.toUpperCase();
                        Log.i(TAG, "Input Address: " + inputedAddress);
                        Log.i(TAG, "Return Address: " + returnAddress);
                        textView.setText(returnAddress);
                        SharedPreferences sharedPreferences = getSharedPreferences("SHARE_KEY", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("POLLING_ADDRESS", returnAddress);
                        editor.commit();

                    }

                    @Override
                    public void onFailure(Call<AddressData> call, Throwable t) {

                    }
                });
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertEventInCalendar("Election Day", "Don't forget to Vote today!", returnAddress);
                //addReminderInCalendar();

            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "I just registered to vote using Vote 1-2-3! Vote 1-2-3 makes voting easy!";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Sharing"));
            }
        });

    }


    public void insertEventInCalendar(String title, String description, String location) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2016, Calendar.NOVEMBER, 8, 10, 0);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(2016, Calendar.NOVEMBER, 8, 23, 0);
        long endMillis = endTime.getTimeInMillis();


        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);


    }


    /** Adds Events and Reminders in Calendar.
     * Currently not using atm.. keeping for future revision
     * */
    private void addReminderInCalendar() {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2016, Calendar.NOVEMBER, 8, 10, 0);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(2016, Calendar.NOVEMBER, 8, 23, 0);
        long endMillis = endTime.getTimeInMillis();


        Calendar cal = Calendar.getInstance();
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        /** Inserting an event in calendar. */
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 2);
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, "Election Day");
        values.put(CalendarContract.Events.DESCRIPTION, "Remember to Vote Today!");
        values.put(CalendarContract.Events.ALL_DAY, 0);
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        Uri event = cr.insert(EVENTS_URI, values);

        // Display event id.
        Toast.makeText(getApplicationContext(), "Event added", Toast.LENGTH_SHORT).show();

        /** Adding reminder for event added. */
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, 60);
        cr.insert(REMINDERS_URI, values);

    }

    /** Returns Calendar Base URI, supports both new and old OS. */
    private String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (android.os.Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }

}
