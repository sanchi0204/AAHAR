package com.example.intern_food.Model;

public class Hostel {
    private String HostelId;
    private String name;

    public Hostel(String hostelId, String name) {
        HostelId = hostelId;
        this.name = name;
    }

    public Hostel() {
    }

    public String getHostelId() {
        return HostelId;
    }

    public void setHostelId(String hostelId) {
        HostelId = hostelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
