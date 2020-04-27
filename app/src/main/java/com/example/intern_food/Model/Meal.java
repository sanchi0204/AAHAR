package com.example.intern_food.Model;

public class Meal {
    String name,time;

    public Meal(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public Meal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
