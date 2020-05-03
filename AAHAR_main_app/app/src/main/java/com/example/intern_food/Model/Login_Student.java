package com.example.intern_food.Model;

public class Login_Student {
    private String idLogin;
    private String username;
    private String password;
    private String lastLogin;

    public Login_Student(String idLogin, String username, String password, String lastLogin) {
        this.idLogin = idLogin;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    public Login_Student() {
    }

    public String getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(String idLogin) {
        this.idLogin = idLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
