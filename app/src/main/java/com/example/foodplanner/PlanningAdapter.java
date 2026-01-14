package com.example.foodplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.backend.MealService;
import com.example.foodplanner.backend.RequestListener;
import com.example.foodplanner.models.MealModel;
import com.example.foodplanner.models.PlanningDay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanningAdapter extends RecyclerView.Adapter<PlanningAdapter.ViewHolder> {

    private List<PlanningDay> planningDays;
    private MealService mealService;
    private FirebaseFirestore db;

    public PlanningAdapter(List<PlanningDay> planningDays) {
        this.planningDays = planningDays;
        this.db = FirebaseFirestore.getInstance();
        loadSavedPlanning();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planning, parent, false);
        mealService = new MealService(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanningDay day = planningDays.get(position);
        holder.tvPlanningDay.setText(day.getDay());

        holder.btnExpand.setOnClickListener(v -> {
            if (holder.layoutMeals.getVisibility() == View.GONE) {
                holder.layoutMeals.setVisibility(View.VISIBLE);
                holder.btnExpand.setRotation(180);
                populateMeals(holder.layoutMeals, day.getDay());
            } else {
                holder.layoutMeals.setVisibility(View.GONE);
                holder.btnExpand.setRotation(0);
            }
        });

        holder.btnAddMeal.setOnClickListener(v -> showMealSelectionDialog(v, day.getDay()));
        
        if (holder.layoutMeals.getVisibility() == View.VISIBLE) {
            populateMeals(holder.layoutMeals, day.getDay());
        }
    }

    private void loadSavedPlanning() {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) return;

        db.collection("users").document(userId).collection("planning")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                    String day = doc.getString("day");
                    String mealId = doc.getString("mealId");
                    
                    // Re-fetch meal details if not in cache (simplified here for brevity)
                    if (day != null && mealId != null) {
                        mealService.getMealById(mealId, new RequestListener<MealModel>() {
                            @Override
                            public void onResponse(MealModel meal) {
                                if (!UserCache.weeklyPlan.get(day).contains(meal)) {
                                    UserCache.weeklyPlan.get(day).add(meal);
                                    notifyDataSetChanged();
                                }
                            }
                            @Override
                            public void onError(String message) {}
                        });
                    }
                }
            });
    }

    private void showMealSelectionDialog(View view, String dayName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_add_meal_selection, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        ((TextView) dialogView.findViewById(R.id.tvDialogTitle)).setText("Ajouter un repas - " + dayName);
        dialogView.findViewById(R.id.ivCloseDialog).setOnClickListener(v -> dialog.dismiss());

        View.OnClickListener mealClickListener = v -> {
            dialog.dismiss();
            String mealType = ((Button) v).getText().toString();
            fetchAndShowMealList(view, mealType, dayName);
        };

        dialogView.findViewById(R.id.btnPetitDejeuner).setOnClickListener(mealClickListener);
        dialogView.findViewById(R.id.btnDejeuner).setOnClickListener(mealClickListener);
        dialogView.findViewById(R.id.btnDiner).setOnClickListener(mealClickListener);

        dialog.show();
    }

    private void fetchAndShowMealList(View view, String mealType, String dayName) {
        mealService.getMeals(new RequestListener<ArrayList<MealModel>>() {
            @Override
            public void onResponse(ArrayList<MealModel> response) {
                List<MealModel> filteredMeals = new ArrayList<>();
                for (MealModel meal : response) {
                    // Restriction filtering
                    boolean hasRestriction = false;
                    for (String restriction : UserCache.restrictions) {
                        if (meal.getIngredients().containsKey(restriction) || meal.getName().toLowerCase().contains(restriction.toLowerCase())) {
                            hasRestriction = true;
                            break;
                        }
                    }
                    if (hasRestriction) continue;

                    // Type filtering
                    if (mealType.contains("Petit")) {
                        if (meal.getCategory().equalsIgnoreCase("Breakfast")) filteredMeals.add(meal);
                    } else {
                        if (!meal.getCategory().equalsIgnoreCase("Breakfast") && !meal.getCategory().equalsIgnoreCase("Dessert")) filteredMeals.add(meal);
                    }
                }
                showMealListDialog(view, mealType, dayName, filteredMeals);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(view.getContext(), "Erreur: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMealListDialog(View view, String mealType, String dayName, List<MealModel> meals) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_meal_list, null);
        builder.setView(dialogView);
        AlertDialog listDialog = builder.create();
        if (listDialog.getWindow() != null) {
            listDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        ((TextView) dialogView.findViewById(R.id.tvListTitle)).setText(mealType + " - " + dayName);
        RecyclerView rv = dialogView.findViewById(R.id.rvMealSelection);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        
        MealSelectionAdapter adapter = new MealSelectionAdapter(meals, meal -> {
            listDialog.dismiss();
            showGeneratedMealDialog(view, mealType, dayName, meal);
        });
        rv.setAdapter(adapter);

        // Add Search functionality
        EditText etSearch = dialogView.findViewById(R.id.etSearchMeal);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        dialogView.findViewById(R.id.ivCloseList).setOnClickListener(v -> listDialog.dismiss());
        listDialog.show();
    }

    private void showGeneratedMealDialog(View view, String mealType, String dayName, MealModel meal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_meal_result, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        ((TextView) dialogView.findViewById(R.id.tvResultTitle)).setText(mealType + " - " + dayName);
        ((TextView) dialogView.findViewById(R.id.tvGeneratedMealName)).setText(meal.getName());
        ImageView ivImage = dialogView.findViewById(R.id.ivGeneratedMealImage);
        Picasso.get().load(meal.getPhoto()).into(ivImage);

        dialogView.findViewById(R.id.ivCloseResult).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.layoutDetailsResult).setOnClickListener(v -> showFullMealDetails(view, mealType, meal));

        dialogView.findViewById(R.id.btnValidateMeal).setOnClickListener(v -> {
            saveMealToPlan(view, dayName, meal);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void saveMealToPlan(View view, String dayName, MealModel meal) {
        UserCache.weeklyPlan.get(dayName).add(meal);
        notifyDataSetChanged();

        String userId = FirebaseAuth.getInstance().getUid();
        if (userId != null) {
            Map<String, Object> mealData = new HashMap<>();
            mealData.put("mealId", meal.getId());
            mealData.put("day", dayName);

            db.collection("users").document(userId)
                .collection("planning").document(meal.getId() + "_" + dayName).set(mealData)
                .addOnSuccessListener(aVoid -> Toast.makeText(view.getContext(), "Sauvegardé !", Toast.LENGTH_SHORT).show());
        }
    }

    private void showFullMealDetails(View view, String mealType, MealModel meal) {
        Dialog dialog = new Dialog(view.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_meal_details, null);
        dialog.setContentView(dialogView);

        ((TextView) dialogView.findViewById(R.id.tvDetailMealType)).setText(mealType.toUpperCase());
        ((TextView) dialogView.findViewById(R.id.tvDetailMealName)).setText(meal.getName());
        ((TextView) dialogView.findViewById(R.id.tvDetailCuisine)).setText(meal.getArea());
        ((TextView) dialogView.findViewById(R.id.tvDetailCalories)).setText(String.valueOf(meal.getCalories()));
        ((TextView) dialogView.findViewById(R.id.tvDetailProteins)).setText(meal.getProteins() + "g");
        ((TextView) dialogView.findViewById(R.id.tvDetailCarbs)).setText(meal.getCarbs() + "g");
        ((TextView) dialogView.findViewById(R.id.tvDetailFats)).setText(meal.getFats() + "g");
        ((TextView) dialogView.findViewById(R.id.tvInstructions)).setText(meal.getInstructions());

        StringBuilder sb = new StringBuilder();
        meal.getIngredients().forEach((ing, meas) -> sb.append("• ").append(ing).append(": ").append(meas).append("\n"));
        ((TextView) dialogView.findViewById(R.id.tvIngredients)).setText(sb.toString());

        Picasso.get().load(meal.getPhoto()).into((ImageView) dialogView.findViewById(R.id.ivDetailImage));

        dialogView.findViewById(R.id.btnYoutube).setOnClickListener(v -> {
            String url = meal.getYtVideo();
            if (url != null && !url.isEmpty()) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        dialogView.findViewById(R.id.ivCloseDetails).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void populateMeals(LinearLayout container, String dayName) {
        container.removeAllViews();
        List<MealModel> meals = UserCache.weeklyPlan.get(dayName);
        if (meals == null) return;

        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        for (MealModel meal : meals) {
            View mealItem = inflater.inflate(R.layout.item_meal_plan, container, false);
            ((TextView) mealItem.findViewById(R.id.tvMealName)).setText(meal.getName());
            ((TextView) mealItem.findViewById(R.id.tvMealType)).setText(meal.getCategory().toUpperCase());
            Picasso.get().load(meal.getPhoto()).into((ImageView) mealItem.findViewById(R.id.ivMealImage));
            mealItem.setOnClickListener(v -> showFullMealDetails(container, meal.getCategory(), meal));
            container.addView(mealItem);
        }
    }

    @Override
    public int getItemCount() {
        return planningDays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlanningDay;
        ImageView btnAddMeal, btnExpand;
        LinearLayout layoutMeals;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlanningDay = itemView.findViewById(R.id.tvPlanningDay);
            btnAddMeal = itemView.findViewById(R.id.btnAddMeal);
            btnExpand = itemView.findViewById(R.id.btnExpand);
            layoutMeals = itemView.findViewById(R.id.layoutMeals);
        }
    }
}
