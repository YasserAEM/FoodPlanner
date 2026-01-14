package com.example.foodplanner;

import com.example.foodplanner.models.MealModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCache {
    public static String username = "";
    public static String email = "";
    public static String password = "";
    public static String gender = "";
    public static int age = 0;
    public static double weight = 0;
    public static int height = 0;

    public static List<String> restrictions = new ArrayList<>();
    public static List<String> preferences = new ArrayList<>();
    
    // Store the selected meals for each day
    public static Map<String, List<MealModel>> weeklyPlan = new HashMap<>();
    
    static {
        // Initialize the plan for each day
        String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        for (String day : days) {
            weeklyPlan.put(day, new ArrayList<>());
        }
    }
}