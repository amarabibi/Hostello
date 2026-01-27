package com.example.hostello;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Add ALL your entities here
@Database(entities = {Hostel.class, Booking.class, Notification.class, ReviewModel.class},
        version = 7,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // DAOs
    public abstract HostelDao hostelDao();
    public abstract NotificationDao notificationDao();
    public abstract ReviewDao reviewDao();

    // Singleton instance
    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "hostello_database"
                            )
                            .fallbackToDestructiveMigration() // wipes old schema if version changes
                            .build();
                }
            }
        }
        return instance;
    }
}
