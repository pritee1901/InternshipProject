package com.pawan.onlinevegetabledelivery;

import android.os.Bundle;
import android.widget.*;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddressActivity extends AppCompatActivity {

    EditText etFlat, etArea, etCity, etState, etPincode;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        etFlat = findViewById(R.id.etFlat);
        etArea = findViewById(R.id.etArea);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etPincode = findViewById(R.id.etPincode);
        btnSave = findViewById(R.id.btnSaveAddress);

        btnSave.setOnClickListener(v -> {

            String flat = etFlat.getText().toString().trim();
            String area = etArea.getText().toString().trim();
            String city = etCity.getText().toString().trim();
            String state = etState.getText().toString().trim();
            String pincode = etPincode.getText().toString().trim();

            if (flat.isEmpty()) {
                Toast.makeText(this, "Enter Flat / House No", Toast.LENGTH_SHORT).show();
                return;
            }

            if (area.isEmpty()) {
                Toast.makeText(this, "Enter Area", Toast.LENGTH_SHORT).show();
                return;
            }

            if (city.isEmpty()) {
                Toast.makeText(this, "Enter City", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pincode.length() != 6) {
                Toast.makeText(this, "Enter valid Pincode", Toast.LENGTH_SHORT).show();
                return;
            }

            HashMap<String, String> map = new HashMap<>();
            map.put("flat", flat);
            map.put("area", area);
            map.put("city", city);
            map.put("state", state);
            map.put("pincode", pincode);

            FirebaseDatabase.getInstance()
                    .getReference("users_address")
                    .push()
                    .setValue(map)
                    .addOnSuccessListener(unused -> {

                        getSharedPreferences("app", MODE_PRIVATE)
                                .edit()
                                .putBoolean("address_saved", true)
                                .apply();

                        Toast.makeText(this, "Address Saved ✅", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AddressActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        finish(); // 🔥 IMPORTANT
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }
}