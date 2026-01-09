package com.example.foodplanner;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class PreferencesActivity extends AppCompatActivity {

    private ChipGroup chipGroup;
    private Button btnFinish;

    // Notre liste de preferences
    private final String[] preferencesList = {
            "Aucune", "Beef", "Breakfast", "Chicken", "Dessert", "Goat", "Lamb",
            "Miscellaneous", "Pasta", "Pork", "Seafood", "Side", "Starter",
            "Vegan", "Vegetarian"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        chipGroup = findViewById(R.id.chipGroupPreferences);
        btnFinish = findViewById(R.id.btnFinish);

        UserCache.preferences.clear();

        for (String pref : preferencesList) {
            addChip(pref);
        }

        btnFinish.setOnClickListener(v -> {
            Toast.makeText(this, "Compte créé avec succès !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PreferencesActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void addChip(String text) {
        Chip chip = new Chip(this);
        chip.setText(text);

        chip.setCheckable(true);
        chip.setClickable(true);
        chip.setTextSize(16f);

        chip.setChipMinHeight(50.0f);
        chip.setTextStartPadding(20f);
        chip.setTextEndPadding(10f);


        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked },
                new int[] { -android.R.attr.state_checked }
        };

        int[] colors = new int[] {
                Color.parseColor("#1B5E20"),
                Color.WHITE
        };
        chip.setChipBackgroundColor(new ColorStateList(states, colors));

        int[] textColors = new int[] {
                Color.WHITE,
                Color.BLACK
        };
        chip.setTextColor(new ColorStateList(states, textColors));


        chip.setCloseIconVisible(true);

        chip.setCloseIconResource(R.drawable.ic_add_small);
        chip.setCloseIconTint(ColorStateList.valueOf(Color.BLACK));

        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // DEVIENT SÉLECTIONNÉ
                UserCache.preferences.add(text);
                chip.setCloseIconResource(R.drawable.ic_close_small); // Devient une croix
                chip.setCloseIconTint(ColorStateList.valueOf(Color.WHITE)); // Croix blanche
            } else {
                // DEVIENT DÉSÉLECTIONNÉ
                UserCache.preferences.remove(text);
                chip.setCloseIconResource(R.drawable.ic_add_small); // Redevient un plus
                chip.setCloseIconTint(ColorStateList.valueOf(Color.BLACK)); // Plus noir
            }
        });

        chipGroup.addView(chip);
    }
}