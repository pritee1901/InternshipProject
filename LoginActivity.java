package com.pawan.onlinevegetabledelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView goRegister;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);
        goRegister = findViewById(R.id.tvRegister);

        auth = FirebaseAuth.getInstance();

        // 🔥 LOGIN
        login.setOnClickListener(v -> {

            String userEmail = email.getText().toString().trim();
            String userPass = password.getText().toString().trim();

            if (userEmail.isEmpty() || userPass.isEmpty()) {
                Toast.makeText(this, "Enter Email & Password", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Toast.makeText(this, "Login Success ✅", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(this,
                                    "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // 🔥 GO TO REGISTER
        goRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }
}