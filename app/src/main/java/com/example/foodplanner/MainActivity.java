package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.models.MealModel;
import com.example.foodplanner.models.NutritionDay;
import com.example.foodplanner.models.PlanningDay;
import com.example.foodplanner.models.ShoppingItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvContent;
    private View profileLayout;
    private TextView tvSectionTitle;
    private LinearLayout navPlanning, navNutrition, navCourses, navProfil;
    private FirebaseFirestore db;
    private FirebaseAuth fAuth;
    
    private List<PlanningDay> planningList;
    private List<NutritionDay> nutritionList;
    private List<ShoppingItem> shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        
        rvContent = findViewById(R.id.rvContent);
        profileLayout = findViewById(R.id.profileLayout);
        tvSectionTitle = findViewById(R.id.tvSectionTitle);
        navPlanning = findViewById(R.id.navPlanning);
        navNutrition = findViewById(R.id.navNutrition);
        navCourses = findViewById(R.id.navCourses);
        navProfil = findViewById(R.id.navProfil);
        
        rvContent.setLayoutManager(new LinearLayoutManager(this));

        // Setup Logout
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            fAuth.signOut();
            UserCache.weeklyPlan = new HashMap<>();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        // Initialize Data
        preparePlanningData();
        loadProfileFromDB();

        // Default View: Planning
        showPlanning();

        // Navigation Clicks
        navPlanning.setOnClickListener(v -> showPlanning());
        navNutrition.setOnClickListener(v -> showNutrition());
        navCourses.setOnClickListener(v -> showCourses());
        navProfil.setOnClickListener(v -> showProfile());

        // Save Profile Button
        findViewById(R.id.btnSaveProfile).setOnClickListener(v -> saveProfileToDB());
    }

    private void showPlanning() {
        tvSectionTitle.setText("Menu Hebdomadaire");
        rvContent.setVisibility(View.VISIBLE);
        profileLayout.setVisibility(View.GONE);
        rvContent.setAdapter(new PlanningAdapter(planningList));
        updateNavUI(navPlanning);
    }

    private void showNutrition() {
        tvSectionTitle.setText("Nutrition Hebdomadaire");
        rvContent.setVisibility(View.VISIBLE);
        profileLayout.setVisibility(View.GONE);
        calculateNutrition();
        rvContent.setAdapter(new NutritionAdapter(nutritionList));
        updateNavUI(navNutrition);
    }

    private void calculateNutrition() {
        nutritionList = new ArrayList<>();
        String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (String day : days) {
            int cal = 0, prot = 0, carbs = 0, fats = 0;
            List<MealModel> dayMeals = UserCache.weeklyPlan.get(day);
            if (dayMeals != null) {
                for (MealModel m : dayMeals) {
                    cal += m.getCalories(); prot += m.getProteins(); carbs += m.getCarbs(); fats += m.getFats();
                }
            }
            nutritionList.add(new NutritionDay(day, cal, prot, carbs, fats));
        }
    }

    private void showCourses() {
        tvSectionTitle.setText("Liste de Courses");
        rvContent.setVisibility(View.VISIBLE);
        profileLayout.setVisibility(View.GONE);
        generateShoppingList();
        rvContent.setAdapter(new ShoppingAdapter(shoppingList));
        updateNavUI(navCourses);
    }

    private void generateShoppingList() {
        shoppingList = new ArrayList<>();
        Map<String, String> ingredients = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        for (List<MealModel> dayMeals : UserCache.weeklyPlan.values()) {
            for (MealModel m : dayMeals) {
                m.getIngredients().forEach((ing, meas) -> {
                    ingredients.put(ing, meas);
                    counts.put(ing, counts.getOrDefault(ing, 0) + 1);
                });
            }
        }
        ingredients.forEach((ing, meas) -> {
            String detail = counts.get(ing) > 1 ? "Utilisé " + counts.get(ing) + "x" : "";
            shoppingList.add(new ShoppingItem(ing, meas, detail));
        });
        if (shoppingList.isEmpty()) shoppingList.add(new ShoppingItem("Aucun ingrédient", "Ajoutez des repas"));
    }

    private void showProfile() {
        tvSectionTitle.setText("Profil");
        rvContent.setVisibility(View.GONE);
        profileLayout.setVisibility(View.VISIBLE);
        updateNavUI(navProfil);
    }

    private void updateNavUI(View selectedNav) {
        navPlanning.setBackgroundResource(R.drawable.bg_nav_item);
        navNutrition.setBackgroundResource(R.drawable.bg_nav_item);
        navCourses.setBackgroundResource(R.drawable.bg_nav_item);
        navProfil.setBackgroundResource(R.drawable.bg_nav_item);
        selectedNav.setBackgroundResource(R.drawable.bg_nav_item_selected);
    }

    private void loadProfileFromDB() {
        FirebaseUser user = fAuth.getCurrentUser();
        if (user == null) return;

        db.collection("users").document(user.getUid()).get().addOnSuccessListener(doc -> {
            if (doc.exists()) {
                UserCache.username = doc.getString("username") != null ? doc.getString("username") : "";
                UserCache.email = doc.getString("email") != null ? doc.getString("email") : user.getEmail();
                UserCache.weight = doc.getDouble("weight") != null ? doc.getDouble("weight") : 0.0;
                UserCache.height = doc.getLong("height") != null ? doc.getLong("height").intValue() : 0;
                UserCache.restrictions = (List<String>) doc.get("restrictions");
                if (UserCache.restrictions == null) UserCache.restrictions = new ArrayList<>();
                updateProfileUI();
            } else {
                UserCache.email = user.getEmail();
                updateProfileUI();
            }
        });
    }

    private void updateProfileUI() {
        EditText etUsername = findViewById(R.id.etProfileUsername);
        EditText etEmail = findViewById(R.id.etProfileEmail);
        EditText etWeight = findViewById(R.id.etProfileWeight);
        EditText etHeight = findViewById(R.id.etProfileHeight);

        if (etUsername != null) etUsername.setText(UserCache.username);
        if (etEmail != null) etEmail.setText(UserCache.email);
        if (etWeight != null) etWeight.setText(String.valueOf(UserCache.weight));
        if (etHeight != null) etHeight.setText(String.valueOf(UserCache.height));
        
        CheckBox[] cbs = {findViewById(R.id.cbLait), findViewById(R.id.cbGluten), findViewById(R.id.cbOeufs), 
                         findViewById(R.id.cbSoy), findViewById(R.id.cbPeanuts), findViewById(R.id.cbPoisson)};
        String[] types = {"Lait", "Gluten", "Oeufs", "Soy", "Peanuts", "Poisson"};
        for(int i=0; i<cbs.length; i++) {
            if (cbs[i] != null) cbs[i].setChecked(UserCache.restrictions.contains(types[i]));
        }
    }

    private void saveProfileToDB() {
        FirebaseUser user = fAuth.getCurrentUser();
        if (user == null) return;

        String newEmail = ((EditText)findViewById(R.id.etProfileEmail)).getText().toString().trim();
        String currentEmail = user.getEmail();

        if (newEmail.isEmpty()) {
            Toast.makeText(this, "L'email ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Update Authentication Email if changed
        if (!newEmail.equalsIgnoreCase(currentEmail)) {
            user.updateEmail(newEmail).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Email d'authentification mis à jour", Toast.LENGTH_SHORT).show();
                    updateFirestoreAndCache(user.getUid(), newEmail);
                } else {
                    Toast.makeText(this, "Erreur Auth (Recent login required?): " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    // Even if Auth fail, we update Firestore with other changes but keep current email or new?
                    // Better to keep Auth and Firestore in sync.
                }
            });
        } else {
            updateFirestoreAndCache(user.getUid(), currentEmail);
        }
    }

    private void updateFirestoreAndCache(String uid, String email) {
        String username = ((EditText)findViewById(R.id.etProfileUsername)).getText().toString().trim();
        String weightStr = ((EditText)findViewById(R.id.etProfileWeight)).getText().toString().trim();
        String heightStr = ((EditText)findViewById(R.id.etProfileHeight)).getText().toString().trim();

        double weight = 0;
        int height = 0;
        try {
            if (!weightStr.isEmpty()) weight = Double.parseDouble(weightStr);
            if (!heightStr.isEmpty()) height = Integer.parseInt(heightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Veuillez entrer des nombres valides", Toast.LENGTH_SHORT).show();
            return;
        }

        UserCache.username = username;
        UserCache.email = email;
        UserCache.weight = weight;
        UserCache.height = height;

        List<String> selectedRestrictions = new ArrayList<>();
        CheckBox[] cbs = {findViewById(R.id.cbLait), findViewById(R.id.cbGluten), findViewById(R.id.cbOeufs), 
                         findViewById(R.id.cbSoy), findViewById(R.id.cbPeanuts), findViewById(R.id.cbPoisson)};
        String[] types = {"Lait", "Gluten", "Oeufs", "Soy", "Peanuts", "Poisson"};
        for(int i=0; i<cbs.length; i++) {
            if (cbs[i] != null && cbs[i].isChecked()) selectedRestrictions.add(types[i]);
        }
        UserCache.restrictions = selectedRestrictions;

        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("weight", weight);
        userData.put("height", height);
        userData.put("restrictions", selectedRestrictions);

        db.collection("users").document(uid).set(userData)
            .addOnSuccessListener(aVoid -> Toast.makeText(this, "Profil enregistré avec succès", Toast.LENGTH_SHORT).show())
            .addOnFailureListener(e -> Toast.makeText(this, "Erreur Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void preparePlanningData() {
        planningList = new ArrayList<>();
        String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (String d : days) planningList.add(new PlanningDay(d));
    }
}
