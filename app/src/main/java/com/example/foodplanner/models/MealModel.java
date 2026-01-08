package com.example.foodplanner.models;

import java.util.ArrayList;
import java.util.HashMap;

public class MealModel {
    private final String id;
    private final String name;
    private final String category;
    private final String area;
    private final ArrayList<String> tags;
    private final String instructions;
    private final HashMap<String, String> ingredients;
    private final int calories;
    private final int fats;
    private final int carbs;
    private final int proteins;
    private final String photo;
    private final String ytVideo;


    public MealModel(String id, String name, String category, String area, ArrayList<String> tags, String instructions, HashMap<String, String> ingredients, int calories, int fats, int carbs, int proteins, String photo, String ytvideo) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.tags = tags;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.calories = calories;
        this.fats = fats;
        this.carbs = carbs;
        this.proteins = proteins;
        this.photo = photo;
        this.ytVideo = ytvideo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getInstructions() {
        return instructions;
    }

    public HashMap<String, String> getIngredients() {
        return ingredients;
    }

    public int getCalories() {
        return calories;
    }

    public int getFats() {
        return fats;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getProteins() {
        return proteins;
    }

    public String getPhoto() {
        return photo;
    }

    public String getYtVideo() {
        return ytVideo;
    }

    @Override
    public String toString() {
        return "MealModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", area='" + area + '\'' +
                ", tags=" + tags +
                ", instructions='" + instructions + '\'' +
                ", ingredients=" + ingredients +
                ", calories=" + calories +
                ", fats=" + fats +
                ", carbs=" + carbs +
                ", proteins=" + proteins +
                ", photo='" + photo + '\'' +
                ", ytvideo='" + ytVideo + '\'' +
                '}';
    }
}
