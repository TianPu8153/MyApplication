package com.example.myapplication.entity;

public class itemdata {
    public String username;
    public String message;
    public String imgsrc;
    public itemdata(String username,String message,String imgsrc){
        this.username=username;
        this.message=message;
        this.imgsrc=imgsrc;
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


    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

}
