package com.pawan.onlinevegetabledelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class OrderPlacedActivity extends AppCompatActivity {

    TextView tvOrderId;
    Button btnTrack, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        tvOrderId = findViewById(R.id.tvOrderId);
        btnTrack = findViewById(R.id.btnTrack);
        btnHome = findViewById(R.id.btnHome);

        // 🔥 RANDOM ORDER ID
        int orderId = new Random().nextInt(90000) + 10000;
        tvOrderId.setText("Order ID: " + orderId);

        btnTrack.setOnClickListener(v -> {
            startActivity(new Intent(this, DeliveryStatusActivity.class));
        });

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}