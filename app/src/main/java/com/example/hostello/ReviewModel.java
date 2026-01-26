package com.example.hostello;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reviews")
public class ReviewModel {
    @PrimaryKey(autoGenerate = true)
    private int id; // Room uses this to keep records unique

    private String reviewerName;
    private String date;
    private float rating;
    private String comment;
    private String hostelName;

    public ReviewModel(String reviewerName, String date, float rating, String comment, String hostelName) {
        this.reviewerName = reviewerName;
        this.date = date;
        this.rating = rating;
        this.comment = comment;
        this.hostelName = hostelName;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReviewerName() { return reviewerName; }
    public String getDate() { return date; }
    public float getRating() { return rating; }
    public String getComment() { return comment; }
    public String getHostelName() { return hostelName; }
}