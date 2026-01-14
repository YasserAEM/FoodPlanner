package com.example.foodplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.models.NutritionDay;
import java.util.List;

public class NutritionAdapter extends RecyclerView.Adapter<NutritionAdapter.ViewHolder> {

    private List<NutritionDay> nutritionDays;

    public NutritionAdapter(List<NutritionDay> nutritionDays) {
        this.nutritionDays = nutritionDays;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nutrition_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NutritionDay day = nutritionDays.get(position);
        holder.tvDayName.setText(day.getDay());
        holder.tvCalories.setText(String.valueOf(day.getCalories()));
        holder.tvProteins.setText(day.getProteins() + "g");
        holder.tvCarbs.setText(day.getCarbs() + "g");
        holder.tvFats.setText(day.getFats() + "g");
    }

    @Override
    public int getItemCount() {
        return nutritionDays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayName, tvCalories, tvProteins, tvCarbs, tvFats;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.tvDayName);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            tvProteins = itemView.findViewById(R.id.tvProteins);
            tvCarbs = itemView.findViewById(R.id.tvCarbs);
            tvFats = itemView.findViewById(R.id.tvFats);
        }
    }
}
