package com.pawan.onlinevegetabledelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password, mobile;
    Button register;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        mobile = findViewById(R.id.etMobile);
        register = findViewById(R.id.btnRegister);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(v -> {

            String userName = name.getText().toString().trim();
            String userEmail = email.getText().toString().trim();
            String userPass = password.getText().toString().trim();
            String userMobile = mobile.getText().toString().trim();

            // 🔴 VALIDATION
            if (userName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty() || userMobile.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Email format check (IMPORTANT FIX 🔥)
            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Password check
            if (userPass.length() < 6) {
                Toast.makeText(this, "Password must be 6+ characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔥 FIREBASE REGISTER
            auth.createUserWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            // ✅ SAVE NAME + MOBILE
                            getSharedPreferences("user", MODE_PRIVATE)
                                    .edit()
                                    .putString("name", userName)
                                    .putString("mobile", userMobile)
                                    .apply();

                            Toast.makeText(this, "Register Success ✅", Toast.LENGTH_SHORT).show();

                            // 👉 LOGIN SCREEN
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();

                        } else {

                            Toast.makeText(RegisterActivity.this,
                                    "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}