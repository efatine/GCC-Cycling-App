package com.example.cyclingclub;

public abstract class User {
            String username;
            String password;
            String role;
            public User(String username, String password){
                this.username = username;
                this.password = password;
            }

    public java.lang.String getUsername() {
        return username;
    }

    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    public java.lang.String getRole() {
        return role;
    }

    public void setRole(java.lang.String role) {
        this.role = role;
    }
}