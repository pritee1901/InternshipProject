package com.pawan.onlinevegetabledelivery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.*;

import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    Button btnLocation;
    TextView tvLocation;

    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        btnLocation = findViewById(R.id.btnLocation);
        tvLocation = findViewById(R.id.tvLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnLocation.setOnClickListener(v -> {

            tvLocation.setText("Getting location...");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }

            // 🔥 FORCE LOCATION REQUEST
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000);
            locationRequest.setNumUpdates(1);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            if (locationResult == null) return;

                            Location location = locationResult.getLastLocation();

                            double lat = location.getLatitude();
                            double lon = location.getLongitude();

                            try {

                                Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);

                                if (addresses != null && addresses.size() > 0) {

                                    String fullAddress = addresses.get(0).getAddressLine(0);
                                    String city = addresses.get(0).getLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String pincode = addresses.get(0).getPostalCode();

                                    tvLocation.setText("📍 " + fullAddress);

                                    // 🔥 PASS DATA TO ADDRESS SCREEN
                                    Intent intent = new Intent(LocationActivity.this, AddressActivity.class);
                                    intent.putExtra("address", fullAddress);
                                    intent.putExtra("city", city);
                                    intent.putExtra("state", state);
                                    intent.putExtra("pincode", pincode);

                                    startActivity(intent);
                                    finish();

                                } else {
                                    tvLocation.setText("Address not found ❌");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                tvLocation.setText("Error getting address ❌");
                            }
                        }
                    },
                    getMainLooper());
        });
    }
}