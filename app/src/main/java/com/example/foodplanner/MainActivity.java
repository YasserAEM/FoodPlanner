package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.models.NutritionDay;
import com.example.foodplanner.models.PlanningDay;
import com.example.foodplanner.models.ShoppingItem;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvContent;
    private View profileLayout;
    private TextView tvSectionTitle;
    private LinearLayout navPlanning, navNutrition, navCourses, navProfil;
    
    private List<PlanningDay> planningList;
    private List<NutritionDay> nutritionList;
    private List<ShoppingItem> shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        rvContent = findViewById(R.id.rvContent);
//        profileLayout = findViewById(R.id.profileLayout);
//        tvSectionTitle = findViewById(R.id.tvSectionTitle);
//        navPlanning = findViewById(R.id.navPlanning);
//        navNutrition = findViewById(R.id.navNutrition);
//        navCourses = findViewById(R.id.navCourses);
//        navProfil = findViewById(R.id.navProfil);
//
//        rvContent.setLayoutManager(new LinearLayoutManager(this));
//
//        // Setup Logout
//        findViewById(R.id.btnLogout).setOnClickListener(v -> {
//            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            finish();
//        });
//
//        // Initialize Data
//        preparePlanningData();
//        prepareNutritionData();
//        prepareShoppingData();
//        loadProfileData();
//
//        // Default View: Planning (1er affichage après connexion)
//        showPlanning();
//
//        // Navigation Clicks
//        navPlanning.setOnClickListener(v -> showPlanning());
//        navNutrition.setOnClickListener(v -> showNutrition());
//        navCourses.setOnClickListener(v -> showCourses());
//        navProfil.setOnClickListener(v -> showProfile());
//
//        // Save Profile Button
//        findViewById(R.id.btnSaveProfile).setOnClickListener(v -> {
//            Toast.makeText(this, "Modifications enregistrées", Toast.LENGTH_SHORT).show();
//        });
//    }
//
//    private void showPlanning() {
//        tvSectionTitle.setText("Menu Hebdomadaire");
//        rvContent.setVisibility(View.VISIBLE);
//        profileLayout.setVisibility(View.GONE);
//        rvContent.setAdapter(new PlanningAdapter(planningList));
//        updateNavUI(navPlanning);
//    }
//
//    private void showNutrition() {
//        tvSectionTitle.setText("Nutrition Hebdomadaire");
//        rvContent.setVisibility(View.VISIBLE);
//        profileLayout.setVisibility(View.GONE);
//        rvContent.setAdapter(new NutritionAdapter(nutritionList));
//        updateNavUI(navNutrition);
//    }
//
//    private void showCourses() {
//        tvSectionTitle.setText("Liste de Courses");
//        rvContent.setVisibility(View.VISIBLE);
//        profileLayout.setVisibility(View.GONE);
//        rvContent.setAdapter(new ShoppingAdapter(shoppingList));
//        updateNavUI(navCourses);
//    }
//
//    private void showProfile() {
//        tvSectionTitle.setText("Profil");
//        rvContent.setVisibility(View.GONE);
//        profileLayout.setVisibility(View.VISIBLE);
//        updateNavUI(navProfil);
//    }
//
//    private void updateNavUI(View selectedNav) {
//        // Reset all to unselected
//        navPlanning.setBackgroundResource(R.drawable.bg_nav_item);
//        navNutrition.setBackgroundResource(R.drawable.bg_nav_item);
//        navCourses.setBackgroundResource(R.drawable.bg_nav_item);
//        navProfil.setBackgroundResource(R.drawable.bg_nav_item);
//
//        // Set selected
//        selectedNav.setBackgroundResource(R.drawable.bg_nav_item_selected);
//    }
//
//    private void loadProfileData() {
//        EditText etUsername = findViewById(R.id.etProfileUsername);
//        EditText etEmail = findViewById(R.id.etProfileEmail);
//        EditText etWeight = findViewById(R.id.etProfileWeight);
//        EditText etHeight = findViewById(R.id.etProfileHeight);
//
//        // Loading from Cache
//        etUsername.setText(UserCache.username);
//        etEmail.setText(FirebaseAuth.getInstance().getCurrentUser() != null ?
//            FirebaseAuth.getInstance().getCurrentUser().getEmail() : "Dija@gmail.com");
//        etWeight.setText(String.valueOf(UserCache.weight > 0 ? UserCache.weight : 70));
//        etHeight.setText(String.valueOf(UserCache.height > 0 ? UserCache.height : 175));
//    }
//
//    private void preparePlanningData() {
//        planningList = new ArrayList<>();
//        planningList.add(new PlanningDay("Lundi"));
//        planningList.add(new PlanningDay("Mardi"));
//        planningList.add(new PlanningDay("Mercredi"));
//        planningList.add(new PlanningDay("Jeudi"));
//        planningList.add(new PlanningDay("Vendredi"));
//        planningList.add(new PlanningDay("Samedi"));
//        planningList.add(new PlanningDay("Dimanche"));
//    }
//
//    private void prepareNutritionData() {
//        nutritionList = new ArrayList<>();
//        nutritionList.add(new NutritionDay("Lundi", 1350, 84, 142, 57));
//        nutritionList.add(new NutritionDay("Mardi", 1420, 118, 132, 60));
//        nutritionList.add(new NutritionDay("Mercredi", 1550, 107, 162, 55));
//        nutritionList.add(new NutritionDay("Jeudi", 1480, 95, 150, 58));
//        nutritionList.add(new NutritionDay("Vendredi", 1600, 110, 170, 52));
//        nutritionList.add(new NutritionDay("Samedi", 1750, 90, 190, 65));
//        nutritionList.add(new NutritionDay("Dimanche", 1400, 80, 140, 50));
//    }
//
//    private void prepareShoppingData() {
//        shoppingList = new ArrayList<>();
//        shoppingList.add(new ShoppingItem("Pâtes", "100g"));
//        shoppingList.add(new ShoppingItem("Courgettes", "1"));
//        shoppingList.add(new ShoppingItem("Pesto", "2"));
//        shoppingList.add(new ShoppingItem("Parmesan", "30g", "Utilisé 4x"));
    }
}
