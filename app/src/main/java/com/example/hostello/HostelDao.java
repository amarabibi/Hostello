package com.example.hostello;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface HostelDao {

    // --- Hostel Management Methods ---

    // Use REPLACE strategy to avoid crashes on primary key conflicts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHostel(Hostel hostel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Hostel... hostels);

    @Update
    void updateHostel(Hostel hostel);

    @Delete
    void deleteHostel(Hostel hostel);

    @Query("DELETE FROM hostels")
    void deleteAll();

    @Query("SELECT * FROM hostels")
    List<Hostel> getAllHostels();

    @Query("SELECT * FROM hostels WHERE id = :id")
    Hostel getHostelById(int id);

    // Add this to help the OwnerDashboard find their specific hostel
    @Query("SELECT * FROM hostels WHERE email = :email LIMIT 1")
    Hostel getHostelByEmail(String email);

    // --- Booking/Inquiry Methods ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBooking(Booking booking);

    @Query("SELECT * FROM bookings ORDER BY id DESC")
    List<Booking> getAllBookings();

    @Query("UPDATE bookings SET status = :status WHERE id = :id")
    void updateBookingStatus(int id, String status);

    @Query("DELETE FROM bookings")
    void deleteAllBookings();
}