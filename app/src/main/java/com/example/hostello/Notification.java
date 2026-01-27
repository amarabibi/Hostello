package com.example.hostello;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    public int id; // Room needs this to manage the DB

    public String title;
    public String message;
    public String time;
    public boolean isRead;

    // Standard constructor for seeding/creating new ones
    public Notification(String title, String message, String time, boolean isRead) {
        this.title = title;
        this.message = message;
        this.time = time;
        this.isRead = isRead;
    }
}