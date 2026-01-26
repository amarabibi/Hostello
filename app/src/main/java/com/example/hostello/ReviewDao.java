package com.example.hostello;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ReviewDao {
    @Insert
    void insertReview(ReviewModel review);

    // Fetch only reviews that match the hostel name passed from the Detail screen
    @Query("SELECT * FROM reviews WHERE hostelName = :name ORDER BY id DESC")
    List<ReviewModel> getReviewsForHostel(String name);

    @Query("SELECT AVG(rating) FROM reviews WHERE hostelName = :name")
    float getAverageRating(String name);
}