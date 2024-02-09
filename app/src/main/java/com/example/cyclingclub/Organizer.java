package com.example.cyclingclub;

public class Organizer extends User{
    public Organizer(String username, String password){
        super(username,password);
        setRole("Organizer");
    }
}