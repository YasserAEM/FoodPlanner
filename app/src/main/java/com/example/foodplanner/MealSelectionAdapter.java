package com.example.foodplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.models.MealModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MealSelectionAdapter extends RecyclerView.Adapter<MealSelectionAdapter.ViewHolder> {

    private List<MealModel> meals;
    private List<MealModel> mealsFull;
    private OnMealSelectedListener listener;

    public interface OnMealSelectedListener {
        void onMealSelected(MealModel meal);
    }

    public MealSelectionAdapter(List<MealModel> meals, OnMealSelectedListener listener) {
        this.meals = meals;
        this.mealsFull = new ArrayList<>(meals);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealModel meal = meals.get(position);
        holder.tvName.setText(meal.getName());
        holder.tvCategory.setText(meal.getCategory());
        Picasso.get().load(meal.getPhoto()).into(holder.ivImage);

        holder.btnSelect.setOnClickListener(v -> listener.onMealSelected(meal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void filter(String text) {
        meals.clear();
        if (text.isEmpty()) {
            meals.addAll(mealsFull);
        } else {
            text = text.toLowerCase();
            for (MealModel item : mealsFull) {
                if (item.getName().toLowerCase().contains(text) || item.getCategory().toLowerCase().contains(text)) {
                    meals.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvCategory;
        Button btnSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivSelectMealImage);
            tvName = itemView.findViewById(R.id.tvSelectMealName);
            tvCategory = itemView.findViewById(R.id.tvSelectMealCategory);
            btnSelect = itemView.findViewById(R.id.btnSelectMeal);
        }
    }
}
