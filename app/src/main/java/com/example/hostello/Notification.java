package com.example.hostello;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String message;
    public String timeStamp;
    public boolean isRead;

    public Notification(String title, String message, String timeStamp) {
        this.title = title;
        this.message = message;
        this.timeStamp = timeStamp;
        this.isRead = false;
    }
}