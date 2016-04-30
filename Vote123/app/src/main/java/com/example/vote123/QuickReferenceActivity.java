package com.example.vote123;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vote123.google.PermissionUtils;


public class QuickReferenceActivity extends AppCompatActivity{

    private boolean mPermissionDenied = false;
    //private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quick_reference);


//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//            .findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
            }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//            mMap = googleMap;
//
//
//            LatLng sydney = new LatLng(-34, 151);
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//
//            }

    }
