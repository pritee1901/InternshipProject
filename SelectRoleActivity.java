package com.pawan.onlinevegetabledelivery;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SelectRoleActivity extends AppCompatActivity {

    Button btnUser, btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);

        btnUser = findViewById(R.id.btnUser);
        btnAdmin = findViewById(R.id.btnAdmin);

        // 👤 USER FLOW
        btnUser.setOnClickListener(v -> {

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                // 👉 Already login → Main screen
                startActivity(new Intent(this, MainActivity.class));
            } else {
                // 👉 Not login → Login screen
                startActivity(new Intent(this, LoginActivity.class));
            }

            finish();
        });

        // 🛠 ADMIN FLOW
        btnAdmin.setOnClickListener(v -> {

            startActivity(new Intent(this, AdminLoginActivity.class));
            finish();
        });
    }
}