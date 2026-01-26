package com.example.hostello;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ReviewDao {
    @Insert
    void insertReview(ReviewModel review);

    @Query("SELECT * FROM reviews WHERE hostelName = :name ORDER BY id DESC")
    LiveData<List<ReviewModel>> getReviewsForHostel(String name);
}