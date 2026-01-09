package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GenderActivity extends AppCompatActivity {

    private LinearLayout cardMale, cardFemale;
    private Button btnNext;
    private String selectedGender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        cardMale = findViewById(R.id.cardMale);
        cardFemale = findViewById(R.id.cardFemale);
        btnNext = findViewById(R.id.btnNextGender);

        cardMale.setOnClickListener(v -> {
            cardMale.setSelected(true);
            cardFemale.setSelected(false);
            selectedGender = "Homme";
        });

        cardFemale.setOnClickListener(v -> {
            cardFemale.setSelected(true);
            cardMale.setSelected(false);
            selectedGender = "Femme";
        });

        btnNext.setOnClickListener(v -> {
            if (selectedGender.isEmpty()) {
                Toast.makeText(GenderActivity.this, "Sélectionnez un genre", Toast.LENGTH_SHORT).show();
            } else {
                UserCache.gender = selectedGender;
                Intent intent = new Intent(GenderActivity.this, MetricsActivity.class);
                startActivity(intent);
            }
        });
    }
}