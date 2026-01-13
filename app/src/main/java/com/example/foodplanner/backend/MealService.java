package com.example.foodplanner.backend;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.foodplanner.models.MealModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealService {

    public static final String BASE_URL_ID = "https://foodplanner-hvbpgzexdmbjexha.switzerlandnorth-01.azurewebsites.net/api/meals/id/";
    public static final String BASE_URL_SEARCH = "https://foodplanner-hvbpgzexdmbjexha.switzerlandnorth-01.azurewebsites.net/api/meals?q=";
    public static final String BASE_URL_ALL = "https://foodplanner-hvbpgzexdmbjexha.switzerlandnorth-01.azurewebsites.net/api/meals";

    private Context context;

    public MealService(Context context) {
        this.context = context;
    }

    public void getMealById(String id, RequestListener<MealModel> listener) {
        String url = BASE_URL_ID + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            (JSONObject response) -> {
                try {
                    listener.onResponse(MealService.toMeal(response));
                } catch (JSONException e) {
                    Log.e("Volley", "Error while parsing response: " + response);
                    throw new RuntimeException(e);
                }
            },
            (VolleyError error) -> {
                listener.onError("Error while requesting meal by ID");
                Log.e("Volley", "That didn't work!");
            });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getMeals(RequestListener<ArrayList<MealModel>> listener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, BASE_URL_ALL, null,
            (JSONArray response) -> {
                try {
                    listener.onResponse(MealService.toMeals(response));
                } catch (JSONException e) {
                    Log.e("Volley", "Error while parsing response: " + response);
                    throw new RuntimeException(e);
                }
            },
            (VolleyError error) -> {
                listener.onError("Error while requesting meals");
                Log.e("Volley", "That didn't work!");
            });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getMealsBySearch(String searchQuery, RequestListener<ArrayList<MealModel>> listener) {
        String url = BASE_URL_SEARCH + searchQuery;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
            (JSONArray response) -> {
                try {
                    listener.onResponse(MealService.toMeals(response));
                } catch (JSONException e) {
                    Log.e("Volley", "Error while parsing response: " + response);
                    throw new RuntimeException(e);
                }
            },
            (VolleyError error) -> {
                listener.onError("Error while requesting meals");
                Log.e("Volley", "That didn't work!");
            });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    private static MealModel toMeal(JSONObject response) throws JSONException {
        ArrayList<String> tags = new ArrayList<>();
        for (int i = 0; i < response.getJSONArray("tags").length(); i++) {
            tags.add(response.getJSONArray("tags").getString(i));
        }

        HashMap<String, String> ingredients = new HashMap<>();
        for (int i = 0; i < response.getJSONArray("ingredients").length(); i++) {
            JSONObject ingredient = response.getJSONArray("ingredients").getJSONObject(i);
            ingredients.put(ingredient.getString("ingredient"), ingredient.getString("measure"));
        }

        return new MealModel(response.getString("_id"), response.getString("name"), response.getString("category"), response.getString("area"), tags, response.getString("instructions"), ingredients, response.getInt("calories"), response.getInt("fats"), response.getInt("carbs"), response.getInt("proteins"), response.getString("photo"), response.getString("ytVideo"));
    }

    private static ArrayList<MealModel> toMeals(JSONArray response) throws JSONException {
        ArrayList<MealModel> meals = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            meals.add(toMeal(response.getJSONObject(i)));
        }
        return meals;
    }
}
