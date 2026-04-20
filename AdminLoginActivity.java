package com.pawan.onlinevegetabledelivery;

import android.os.Bundle;
import android.widget.*;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    EditText etUser, etPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {

            String user = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if(user.equals("admin") && pass.equals("1234")){
                Toast.makeText(this,"Login Success ✅",Toast.LENGTH_SHORT).show();

                // 🔥 OPEN ADMIN PANEL
                startActivity(new Intent(this, AdminActivity.class));
                finish();

            } else {
                Toast.makeText(this,"Access Denied ❌",Toast.LENGTH_SHORT).show();
            }
        });
    }
}