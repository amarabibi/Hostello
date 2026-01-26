package com.example.hostello;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// 1. Add Notification.class to the entities array
@Database(entities = {Hostel.class, Notification.class}, version = 2) // Increment version to 2
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract HostelDao hostelDao();

    // 2. Add this abstract method for the Notifications
    public abstract NotificationDao notificationDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hostello_database")
                    .fallbackToDestructiveMigration() // Helps handle the version change
                    .build();
        }
        return instance;
    }
}