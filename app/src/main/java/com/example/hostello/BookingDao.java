package com.example.hostello;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookingDao {

    // Insert a new booking. Using REPLACE ensures that if a booking
    // with the same ID exists, it simply updates it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBooking(Booking booking);

    // Fetch all bookings - useful for the ManageBookingsActivity
    @Query("SELECT * FROM bookings ORDER BY id DESC")
    List<Booking> getAllBookings();

    // Fetch bookings for a specific hostel (useful for Owners)
    @Query("SELECT * FROM bookings WHERE hostelName = :hostelName")
    List<Booking> getBookingsByHostel(String hostelName);

    // Update the status (e.g., Pending -> Confirmed or Cancelled)
    @Query("UPDATE bookings SET status = :status WHERE id = :id")
    void updateBookingStatus(int id, String status);

    @Update
    void updateBooking(Booking booking);

    @Delete
    void deleteBooking(Booking booking);

    @Query("DELETE FROM bookings")
    void deleteAllBookings();
}