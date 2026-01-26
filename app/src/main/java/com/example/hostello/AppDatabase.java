package com.example.hostello;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// ✅ Single @Database annotation including all entities
@Database(entities = {Hostel.class, Notification.class, ReviewModel.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    // DAOs
    public abstract HostelDao hostelDao();
    public abstract NotificationDao notificationDao();
    public abstract ReviewDao reviewDao();

    // Singleton instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hostello_database")
                    .fallbackToDestructiveMigration() // Handles version changes
                    .build();
        }
        return instance;
    }
}
