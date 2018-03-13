package com.example.ghulam.myapplicationtodo;

/**
 * Created by Ghulam on 2/27/2018.
 */

public class Task {
    private String name;
    private String time;
    private String key;



    public Task() {
    }

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public Task(String name, String time, String key) {
        this.name = name;
        this.time = time;
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getKey() {
        return key;
    }
}
