package com.example.foodplanner.backend;

public interface RequestListener <T> {
    void onError(String message);
    void onResponse(T response);
}


