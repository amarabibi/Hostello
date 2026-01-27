package com.example.hostello;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// version 8: Added BookingDao accessor to resolve initialization crashes
@Database(entities = {Hostel.class, Booking.class, Notification.class, ReviewModel.class},
        version = 8,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // --- DAOs ---
    public abstract HostelDao hostelDao();
    public abstract NotificationDao notificationDao();
    public abstract ReviewDao reviewDao();

    // 🔹 CRITICAL: This was missing!
    // Without this, Room cannot handle Booking operations even if the Entity is listed above.
    public abstract BookingDao bookingDao();

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
                            // This will wipe the database and recreate it when you change the version
                            // Very helpful during development to fix "app not moving" issues
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}