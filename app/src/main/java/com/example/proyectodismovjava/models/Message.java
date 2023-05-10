package com.example.proyectodismovjava.models;


import com.example.proyectodismovjava.VideoLlamada;

public class Message {
    private String msg;
    private String name;
    private long hour;
    public VideoLlamada context;
    public Message() {
    }

    public Message(String msg, String name, long hour) {
        this.msg = msg;
        this.name = name;
        this.hour = hour;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

}
