package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            Intent intent = new Intent(GetStartedActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_start);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.start), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnGetStarted = findViewById(R.id.btnGetStarted);
        btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(GetStartedActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
