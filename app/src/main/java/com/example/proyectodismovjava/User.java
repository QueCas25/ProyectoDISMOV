package com.example.proyectodismovjava;

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
}

