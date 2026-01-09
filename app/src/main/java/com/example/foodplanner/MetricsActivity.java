package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MetricsActivity extends AppCompatActivity {

    private EditText etAge, etWeight, etHeight;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnNext = findViewById(R.id.btnNextMetrics);

        btnNext.setOnClickListener(v -> {
            try {
                String ageStr = etAge.getText().toString();
                String weightStr = etWeight.getText().toString();
                String heightStr = etHeight.getText().toString();

                if (ageStr.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty()) {
                    Toast.makeText(MetricsActivity.this, "Remplissez tout", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserCache.age = Integer.parseInt(ageStr);
                UserCache.weight = Double.parseDouble(weightStr);
                UserCache.height = Integer.parseInt(heightStr);

                Intent intent = new Intent(MetricsActivity.this, RestrictionsActivity.class);
                startActivity(intent);

            } catch (NumberFormatException e) {
                Toast.makeText(MetricsActivity.this, "Chiffres valides uniquement", Toast.LENGTH_SHORT).show();
            }
        });
    }
}