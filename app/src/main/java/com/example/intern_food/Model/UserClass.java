package com.example.intern_food.Model;

public class UserClass {
    private String idUserId;
    private String idLogin;
    private String name;
    private String rollNo;
    private String branch;
    private String hostel;
    private String roomNo;
    private String phoneNo;
    private String imageuri;

    public UserClass() {
    }

    public UserClass(String idUserId, String idLogin, String name, String rollNo, String branch, String hostel, String roomNo, String phoneNo, String imageuri) {
        this.idUserId = idUserId;
        this.idLogin = idLogin;
        this.name = name;
        this.rollNo = rollNo;
        this.branch = branch;
        this.hostel = hostel;
        this.roomNo = roomNo;
        this.phoneNo = phoneNo;
        this.imageuri = imageuri;
    }

    public String getIdUserId() {
        return idUserId;
    }

    public void setIdUserId(String idUserId) {
        this.idUserId = idUserId;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(String idLogin) {
        this.idLogin = idLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
