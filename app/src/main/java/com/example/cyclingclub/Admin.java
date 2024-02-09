package com.example.cyclingclub;

public class Admin extends User{
    public Admin(String username, String password){
        super(username,password);
        setRole("Admin");
    }
}