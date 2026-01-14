package com.example.foodplanner.models;

public class NutritionDay {
    private String day;
    private int calories;
    private int proteins;
    private int carbs;
    private int fats;

    public NutritionDay(String day, int calories, int proteins, int carbs, int fats) {
        this.day = day;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
    }

    public String getDay() { return day; }
    public int getCalories() { return calories; }
    public int getProteins() { return proteins; }
    public int getCarbs() { return carbs; }
    public int getFats() { return fats; }
}
