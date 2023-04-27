package com.example.proyectodismovjava.activities;

public class User {
    public String username;
    public String email;

    public User() {
        // Constructor vacío requerido para Firebase
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return username;
    }
}


