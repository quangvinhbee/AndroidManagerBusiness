package com.nhom1.models;

public class Notification {
    private String title, time, body;

    public Notification() {
    }

    public Notification(String title, String time, String body) {
        this.title = title;
        this.time = time;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
