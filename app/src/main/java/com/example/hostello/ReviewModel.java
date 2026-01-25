package com.example.hostello;

public class ReviewModel {
    private String reviewerName;
    private String date;
    private float rating;
    private String comment;
    private String hostelName; // NEW

    public ReviewModel(String reviewerName, String date, float rating, String comment, String hostelName) {
        this.reviewerName = reviewerName;
        this.date = date;
        this.rating = rating;
        this.comment = comment;
        this.hostelName = hostelName;
    }

    // getters
    public String getReviewerName() { return reviewerName; }
    public String getDate() { return date; }
    public float getRating() { return rating; }
    public String getComment() { return comment; }
    public String getHostelName() { return hostelName; }
}
