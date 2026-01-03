package com.example.foodplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    // Déclaration des vues
    private EditText etUsername, etEmail, etPassword, etAge, etWeight, etHeight;
    private RadioGroup rgGender;
    private Spinner spinnerPreference, spinnerAllergies;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPassword);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);

        rgGender = findViewById(R.id.rgGender);

        spinnerPreference = findViewById(R.id.spinnerPreference);
        spinnerAllergies = findViewById(R.id.spinnerAllergies);

        btnRegister = findViewById(R.id.btnRegister);

        // 2. Configuration des Spinners
        setupSpinners();

        // 3. Gestion du clic sur le bouton S'inscrire
        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void setupSpinners() {
        // --- Liste des Préférences Alimentaires  ---
        String[] preferences = {"Aucune", "Beef", "Breakfast", "Chicken", "Dessert", "Goat", "Lamb", "Miscellaneous", "Pasta",
        "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian"};

        ArrayAdapter<String> adapterPref = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, preferences);
        adapterPref.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPreference.setAdapter(adapterPref);

        // --- Liste des Allergies ---
        String[] allergies = {"Aucune", "Gluten", "Lactose", "Fruits de mer", "Soja", "Oeufs"};

        ArrayAdapter<String> adapterAllergy = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allergies);
        adapterAllergy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAllergies.setAdapter(adapterAllergy);
    }

    private void registerUser() {
        // Récupération des valeurs
        String username = etUsername.getText().toString();
        String selectedPreference = spinnerPreference.getSelectedItem().toString();
        String selectedAllergy = spinnerAllergies.getSelectedItem().toString();

        // Récupérer le Sexe depuis les RadioButtons
        String gender = "";
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == R.id.rbMale) {
            gender = "Homme";
        } else if (selectedId == R.id.rbFemale) {
            gender = "Femme";
        }

        // TODO: Ajouter ici la validation (vérifier si les champs sont vides)
        // TODO: Envoyer ces données à votre base de données (MongoDB via API)

        // Test visuel
        String message = "Bienvenue " + username + " (" + gender + ") ! \nPref: " + selectedPreference;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}