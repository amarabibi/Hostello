package com.example.hostello;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookings")
public class Booking {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String studentName;
    public String studentPhone;
    public String hostelName;
    public String status; // "Pending", "Confirmed", "Rejected"

    public Booking(String studentName, String studentPhone, String hostelName, String status) {
        this.studentName = studentName;
        this.studentPhone = studentPhone;
        this.hostelName = hostelName;
        this.status = status;
    }
}