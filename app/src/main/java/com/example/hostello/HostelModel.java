package com.example.hostello;

public class HostelModel {
    private String name;
    private String location;
    private int imageResId; // use drawable resource

    public HostelModel(String name, String location, int imageResId) {
        this.name = name;
        this.location = location;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getImageResId() { return imageResId; }
}
