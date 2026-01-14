package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnNext = findViewById(R.id.btnNextAccount);

        btnNext.setOnClickListener(v -> {
            String user = etUsername.getText().toString().trim();
            String mail = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (user.isEmpty() || mail.isEmpty() || pass.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Veuillez tout remplir", Toast.LENGTH_SHORT).show();

            } else if (pass.length() < 6) {
                etPassword.setError("Le mot de passe doit faire au moins 6 caractères");
                etPassword.requestFocus(); // Remet le focus sur la case mot de passe

            } else {
                UserCache.username = user;
                UserCache.email = mail;
                UserCache.password = pass;

                Intent intent = new Intent(SignUpActivity.this, GenderActivity.class);
                startActivity(intent);
            }
        });
    }
}