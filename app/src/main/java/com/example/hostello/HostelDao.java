package com.example.hostello;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface HostelDao {

    // --- Hostel Management Methods ---

    @Insert
    void insertAll(Hostel... hostels);

    @Update
    void updateHostel(Hostel hostel);

    @Delete
    void deleteHostel(Hostel hostel);

    @Query("DELETE FROM hostels")
    void deleteAll();

    // This name matches your HomeFragment and OwnerDashboard
    @Query("SELECT * FROM hostels")
    List<Hostel> getAllHostels();

    @Query("SELECT * FROM hostels WHERE id = :id")
    Hostel getHostelById(int id);


    // --- Booking/Inquiry Methods ---

    @Insert
    void insertBooking(Booking booking);

    @Query("SELECT * FROM bookings ORDER BY id DESC")
    List<Booking> getAllBookings();

    @Query("UPDATE bookings SET status = :status WHERE id = :id")
    void updateBookingStatus(int id, String status);

    @Query("DELETE FROM bookings")
    void deleteAllBookings();
}