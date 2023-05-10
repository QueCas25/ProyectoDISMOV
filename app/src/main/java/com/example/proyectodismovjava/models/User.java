package com.example.proyectodismovjava.models;

import com.example.proyectodismovjava.UsersActivity;

public class User {
    private String name;
    private String email;
    private UsersActivity context;
    public UsersActivity getContext() {
        return context;
    }

    public void setContext(UsersActivity context) {
        this.context = context;
    }


        public User() {}

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

