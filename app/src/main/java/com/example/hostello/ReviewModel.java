package com.example.hostello;

public class ReviewModel {
    private String name;
    private String date;
    private float rating;
    private String comment;

    public ReviewModel(String name, String date, float rating, String comment) {
        this.name = name;
        this.date = date;
        this.rating = rating;
        this.comment = comment;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public float getRating() { return rating; }
    public String getComment() { return comment; }
}
