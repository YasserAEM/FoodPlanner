package com.example.foodplanner.models;

public class ShoppingItem {
    private String name;
    private String quantity;
    private String detail; // e.g., "Utilisé 4x"

    public ShoppingItem(String name, String quantity) {
        this(name, quantity, "");
    }

    public ShoppingItem(String name, String quantity, String detail) {
        this.name = name;
        this.quantity = quantity;
        this.detail = detail;
    }

    public String getName() { return name; }
    public String getQuantity() { return quantity; }
    public String getDetail() { return detail; }
}
