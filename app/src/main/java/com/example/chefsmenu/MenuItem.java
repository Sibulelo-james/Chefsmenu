package com.example.chefsmenu;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class MenuItem implements Serializable {
    private String id;
    private String dishName;
    private String description;
    private String course;
    private double price;

    public MenuItem(String id, String dishName, String description, String course, double price) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.dishName = dishName;
        this.description = description;
        this.course = course;
        this.price = price;
    }

    public MenuItem(String dishName, String description, String course, double price) {
        this(UUID.randomUUID().toString(), dishName, description, course, price);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(id, menuItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}