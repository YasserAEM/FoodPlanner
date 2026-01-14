package com.example.foodplanner;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PreferencesActivity extends AppCompatActivity {

    private ChipGroup chipGroup;
    private Button btnFinish;

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

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btnFinish.setOnClickListener(v -> {
            fAuth.createUserWithEmailAndPassword(UserCache.email, UserCache.password).addOnCompleteListener(
                (@NonNull Task<AuthResult> task) -> {
                    if (task.isSuccessful()) {
                        String userId = fAuth.getCurrentUser().getUid();
                        
                        // Create user object for Firestore
                        Map<String, Object> user = new HashMap<>();
                        user.put("username", UserCache.username);
                        user.put("email", UserCache.email);
                        user.put("gender", UserCache.gender);
                        user.put("age", UserCache.age);
                        user.put("weight", UserCache.weight);
                        user.put("height", UserCache.height);
                        user.put("restrictions", UserCache.restrictions);
                        user.put("preferences", UserCache.preferences);

                        db.collection("users").document(userId).set(user)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(PreferencesActivity.this, "Compte créé avec succès !", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PreferencesActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(PreferencesActivity.this, "Erreur sauvegarde profil: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                    } else {
                        Toast.makeText(PreferencesActivity.this, "Erreur: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        });
    }

    private void addChip(String text) {
        Chip chip = new Chip(this);
        chip.setText(text);
        chip.setCheckable(true);
        chip.setClickable(true);
        chip.setTextSize(16f);
        chip.setChipMinHeight(50.0f);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked },
                new int[] { -android.R.attr.state_checked }
        };
        int[] colors = new int[] { Color.parseColor("#1B5E20"), Color.WHITE };
        chip.setChipBackgroundColor(new ColorStateList(states, colors));
        int[] textColors = new int[] { Color.WHITE, Color.BLACK };
        chip.setTextColor(new ColorStateList(states, textColors));

        chip.setCloseIconVisible(true);
        chip.setCloseIconResource(R.drawable.ic_add_small);

        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                UserCache.preferences.add(text);
                chip.setCloseIconResource(R.drawable.ic_close_small);
            } else {
                UserCache.preferences.remove(text);
                chip.setCloseIconResource(R.drawable.ic_add_small);
            }
        });

        chipGroup.addView(chip);
    }
}
