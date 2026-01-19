package org.example;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String brand;
    private double price;
    private String description;
    private int numberInStock;

    public Product(long id, String name, String brand, double price, String description, int numberInStock) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.numberInStock = numberInStock;
    }

    // Getters and Setters
    public long getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Brand: %s | Price: %.2f | Stock: %d",
                id, name, brand, price, numberInStock);
    }
}
