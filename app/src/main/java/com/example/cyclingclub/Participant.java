package com.example.cyclingclub;

public class Participant extends User{
    public Participant(String username, String password){
        super(username,password);
        setRole("Participant");
    }
}