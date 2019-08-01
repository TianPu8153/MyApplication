package com.example.myapplication.entity;



public class event {
    public int id;
    public String name;
    public String date;
    public int state;
    public int level;
    public String imgsrc;

    public event(int id,String name,String date,int state,int level,String imgsrc){
        this.id=id;
        this.name=name;
        this.date=date;
        this.state=state;
        this.level=level;
        this.imgsrc=imgsrc;
    }


    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
