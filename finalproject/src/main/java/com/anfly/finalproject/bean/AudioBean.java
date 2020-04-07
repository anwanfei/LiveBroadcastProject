package com.anfly.finalproject.bean;

public class AudioBean {
    private String name;
    private String path;

    public AudioBean(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
