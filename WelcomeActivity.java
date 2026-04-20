package com.pawan.onlinevegetabledelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnStart = findViewById(R.id.btnStart);

        // 🔥 BUTTON CLICK
        btnStart.setOnClickListener(v -> {

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                // 👉 Already login → direct main
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));

            } else {

                // 👉 Not login → login screen
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }

            finish();
        });
    }
}