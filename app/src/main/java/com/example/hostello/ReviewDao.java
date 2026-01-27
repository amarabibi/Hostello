package com.example.hostello;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ReviewDao {

    @Insert
    void insertReview(ReviewModel review);

    // Get all reviews for a specific hostel, latest first
    @Query("SELECT * FROM reviews WHERE hostelName = :name ORDER BY id DESC")
    LiveData<List<ReviewModel>> getReviewsForHostel(String name);

    // Returns LiveData Float. Note: This can return NULL if no reviews exist.
    @Query("SELECT AVG(rating) FROM reviews WHERE hostelName = :name")
    LiveData<Float> getAverageRating(String name);

    // Used for logic checks (like inserting dummy data)
    @Query("SELECT COUNT(*) FROM reviews WHERE hostelName = :name")
    int getReviewCount(String name);

    // Optional: Allow users to delete their review
    @Delete
    void deleteReview(ReviewModel review);
}