package com.example.mdb_spring_bott.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groceryItems")
public class GroceryItem {

    @Id
    private String id;
    private String name;
    private int quantity;
    private String category;

    // Constructors
    public GroceryItem() {
    }

    public GroceryItem(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "GroceryItem [id=" + id + ", name=" + name + ", quantity=" + quantity + ", category=" + category + "]";
    }
}