package com.example.foodplanner;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RestrictionsActivity extends AppCompatActivity {

    private GridLayout gridCommon;
    private AutoCompleteTextView actvIngredient;
    private Button btnNext;

    // Nos allergies principales
    private final String[] commonAllergies = {"Lait", "Gluten", "Oeufs", "Soy", "Peanuts", "Poisson"};

    private final int[] allergyIcons = {
            R.drawable.ic_dairy, // Lait
            R.drawable.ic_gluten, // Gluten
            R.drawable.ic_egg, // Eggs
            R.drawable.ic_soy, // Soy
            R.drawable.ic_peanut, // Peanuts
            R.drawable.ic_poisson // Poisson
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrictions);

        gridCommon = findViewById(R.id.gridCommonAllergies);
        actvIngredient = findViewById(R.id.actvIngredient);
        btnNext = findViewById(R.id.btnNextRestrictions);

        setupGrid();

        String[] allIngredients = getResources().getStringArray(R.array.ingredients_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, allIngredients);
        actvIngredient.setAdapter(adapter);

        actvIngredient.setOnItemClickListener((parent, view, position, id) -> {
            String selection = (String) parent.getItemAtPosition(position);
            UserCache.restrictions.add(selection);
            actvIngredient.setText("");
        });

        btnNext.setOnClickListener(v -> {
            startActivity(new Intent(RestrictionsActivity.this, PreferencesActivity.class));
        });
    }

    private void setupGrid() {
        for (int i = 0; i < commonAllergies.length; i++) {
            String name = commonAllergies[i];
            int iconRes = allergyIcons[i];

            LinearLayout container = new LinearLayout(this);
            container.setOrientation(LinearLayout.VERTICAL);
            container.setGravity(Gravity.CENTER);
            container.setPadding(16, 24, 16, 24);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.width = 0;
            container.setLayoutParams(params);

            ImageView iv = new ImageView(this);
            iv.setImageResource(iconRes);
            iv.setLayoutParams(new LinearLayout.LayoutParams(120, 120));

            iv.setImageTintList(ContextCompat.getColorStateList(this, R.color.selector_toggle_color));

            container.addView(iv);

            TextView tv = new TextView(this);
            tv.setText(name);
            tv.setTextSize(14f);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(0, 16, 0, 0);

            tv.setTextColor(ContextCompat.getColorStateList(this, R.color.selector_toggle_color));

            container.addView(tv);

            container.setOnClickListener(v -> {
                boolean newState = !container.isSelected();
                container.setSelected(newState);

                if (newState) UserCache.restrictions.add(name);
                else UserCache.restrictions.remove(name);
            });

            gridCommon.addView(container);
        }
    }
}