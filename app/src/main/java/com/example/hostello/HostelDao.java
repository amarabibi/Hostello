package com.example.hostello;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface HostelDao {
    @Query("SELECT * FROM hostels")
    List<Hostel> getAllHostels();

    @Insert
    void insertAll(Hostel... hostels);

    @Query("DELETE FROM hostels")
    void deleteAll();
}