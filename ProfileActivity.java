package com.pawan.onlinevegetabledelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvMobile;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 🔥 CONNECT VIEWS
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobile = findViewById(R.id.tvMobile);
        btnLogout = findViewById(R.id.btnLogout);

        // 🔥 GET EMAIL FROM FIREBASE
        String email = "";
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }

        // 🔥 GET SAVED DATA
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        String name = sp.getString("name", "Not set");
        String mobile = sp.getString("mobile", "Not set");

        // 🔥 SET DATA
        tvName.setText("Name: " + name);
        tvEmail.setText("Email: " + email);
        tvMobile.setText("Mobile: " + mobile);

        // 🔥 LOGOUT
        btnLogout.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(ProfileActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }
}