package com.pawan.onlinevegetabledelivery;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

public class DeliveryStatusActivity extends AppCompatActivity {

    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status);

        tvStatus = findViewById(R.id.tvStatus);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders");

        // 🔥 GET LATEST ORDER (REAL TIME)
        ref.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    tvStatus.setText("No order found ❌");
                    return;
                }

                for (DataSnapshot data : snapshot.getChildren()) {

                    String status = data.child("delivery_status").getValue(String.class);

                    if (status != null) {
                        tvStatus.setText("🚚 Status: " + status);
                    } else {
                        tvStatus.setText("Status not available ❌");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                tvStatus.setText("Error loading status ❌");
            }
        });
    }
}