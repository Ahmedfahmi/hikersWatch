package com.ahmedfahmi.hikerswatch;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements LocationListener {
    private LocationManager locationManager;
    private String provider;
    private TextView altitudeTxt;
    private TextView latitudeTxt;
    private TextView speedTxt;
    private TextView bearingTxt;
    private TextView longitudeTxt;
    private TextView accuarcyTxt;
    private LinearLayout linearLayout;
    private TextView addressTxt;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiate();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        if (location != null) {
            location = locationManager.getLastKnownLocation(provider);
        } else {
            linearLayout.setVisibility(View.INVISIBLE);

        }


    }

    private void initiate() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), true);
        accuarcyTxt = (TextView) findViewById(R.id.accTextView);
        speedTxt = (TextView) findViewById(R.id.speedTextView);
        bearingTxt = (TextView) findViewById(R.id.BearingTextView);
        longitudeTxt = (TextView) findViewById(R.id.lonTextView);
        latitudeTxt = (TextView) findViewById(R.id.latTextView);
        altitudeTxt = (TextView) findViewById(R.id.altTextView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        addressTxt = (TextView) findViewById(R.id.addressTextView);
    }

    @Override
    public void onLocationChanged(Location location) {
        linearLayout.setVisibility(View.VISIBLE);
        Double altitude = location.getAltitude();
        Double longitude = location.getLongitude();
        Double latitude = location.getLatitude();
        Float bearing = location.getBearing();
        Float speed = location.getSpeed();
        Float accuracy = location.getAccuracy();
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                addressTxt.setText("Address: " + addresses.get(0).getAddressLine(0));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        altitudeTxt.setText("Altitude: " + altitude.toString() + " m");
        longitudeTxt.setText("Longitude: " + longitude.toString());
        accuarcyTxt.setText("Accuarcy: " + accuracy.toString() + "m");
        bearingTxt.setText("Bearing: " + bearing.toString());
        speedTxt.setText("Speed: " + speed.toString() + " m/s");
        latitudeTxt.setText("Latitude: " + latitude.toString());


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.removeUpdates(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
