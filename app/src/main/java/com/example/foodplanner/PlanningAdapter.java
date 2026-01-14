package com.example.foodplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.models.PlanningDay;
import java.util.List;

public class PlanningAdapter extends RecyclerView.Adapter<PlanningAdapter.ViewHolder> {

    private List<PlanningDay> planningDays;

    public PlanningAdapter(List<PlanningDay> planningDays) {
        this.planningDays = planningDays;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planning, parent, false);
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
                populateMeals(holder.layoutMeals);
            } else {
                holder.layoutMeals.setVisibility(View.GONE);
                holder.btnExpand.setRotation(0);
            }
        });

        holder.btnAddMeal.setOnClickListener(v -> showMealSelectionDialog(v, day.getDay()));
    }

    private void showMealSelectionDialog(View view, String dayName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_add_meal_selection, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ((TextView) dialogView.findViewById(R.id.tvDialogTitle)).setText("Ajouter un repas - " + dayName);
        dialogView.findViewById(R.id.ivCloseDialog).setOnClickListener(v -> dialog.dismiss());

        View.OnClickListener mealClickListener = v -> {
            dialog.dismiss();
            String mealType = ((Button) v).getText().toString();
            showGeneratedMealDialog(view, mealType, dayName);
        };

        dialogView.findViewById(R.id.btnPetitDejeuner).setOnClickListener(mealClickListener);
        dialogView.findViewById(R.id.btnDejeuner).setOnClickListener(mealClickListener);
        dialogView.findViewById(R.id.btnDiner).setOnClickListener(mealClickListener);

        dialog.show();
    }

    private void showGeneratedMealDialog(View view, String mealType, String dayName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_meal_result, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ((TextView) dialogView.findViewById(R.id.tvResultTitle)).setText(mealType + " - " + dayName);
        dialogView.findViewById(R.id.ivCloseResult).setOnClickListener(v -> dialog.dismiss());
        
        // Fix: "Details" click on the "Details" layout instead of just the image
        dialogView.findViewById(R.id.layoutDetailsResult).setOnClickListener(v -> showFullMealDetails(view, mealType));

        // Fix: Add Validate button listener
        dialogView.findViewById(R.id.btnValidateMeal).setOnClickListener(v -> {
            Toast.makeText(view.getContext(), "Repas validé pour le " + dayName, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showFullMealDetails(View view, String mealType) {
        Dialog dialog = new Dialog(view.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_meal_details, null);
        dialog.setContentView(dialogView);

        // Fix: Set correct meal type in details
        ((TextView) dialogView.findViewById(R.id.tvDetailMealType)).setText(mealType.toUpperCase());

        dialogView.findViewById(R.id.ivCloseDetails).setOnClickListener(v -> dialog.dismiss());
        
        dialog.show();
    }

    private void populateMeals(LinearLayout container) {
        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        
        // Example meal
        View mealItem = inflater.inflate(R.layout.item_meal_plan, container, false);
        TextView tvType = mealItem.findViewById(R.id.tvMealType);
        tvType.setText("PETIT-DÉJEUNER");
        ((TextView) mealItem.findViewById(R.id.tvMealName)).setText("Omelette aux Champignons");
        
        // Fix: Click on "Details" section in the main planning list
        mealItem.setOnClickListener(v -> showFullMealDetails(container, tvType.getText().toString()));
        
        container.addView(mealItem);
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
