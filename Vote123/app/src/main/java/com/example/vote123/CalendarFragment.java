package com.example.vote123;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private Button addToCalButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        setView(v);
        setButtonClick();


        return v;
    }

    private String getPollAdress(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return sharedPreferences.getString(PollingFragment.POLL_ADDRESS_ID, "");
    }

    private void setView(View v){
        addToCalButton = (Button)v.findViewById(R.id.add_button_calender_id);
    }

    private void setButtonClick(){
        addToCalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertEventInCalendar("Election Day", "Don't forget to Vote today!");
            }
        });
    }

    private void insertEventInCalendar(String title, String description) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2016, Calendar.NOVEMBER, 8, 10, 0);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(2016, Calendar.NOVEMBER, 8, 20, 0);
        long endMillis = endTime.getTimeInMillis();

        String pollAddress = getPollAdress();


        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description);
        if (pollAddress != null) {
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, pollAddress);
        }
            intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
            startActivity(intent);
    }

}
