package com.example.hostello;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    List<Notification> getAllNotifications();

    @Insert
    void insert(Notification... notifications);

    @Query("DELETE FROM notifications")
    void deleteAll();
}