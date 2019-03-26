package com.example.myapplication.entity;

public class itemdata {
    public String username;
    public String message;

    public itemdata(String username,String message){
        this.username=username;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
