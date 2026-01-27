package com.example.hostello;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hostels")
public class Hostel {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name, price, rating, type, location;
    public String amenity1, amenity2, amenity3;
    public String roomType, availableRooms, facilities;
    public String messAvailability, messCharges;
    public String phone, email, imageResourceName;

    public boolean isExpanded = false;

    // ✅ ADD THIS: The missing empty constructor
    public Hostel() {
    }

    // This is your existing 16-parameter constructor for the DataGenerator
    public Hostel(String name, String price, String rating, String type, String location,
                  String amenity1, String amenity2, String amenity3,
                  String roomType, String availableRooms, String facilities,
                  String messAvailability, String messCharges,
                  String phone, String email, String imageResourceName) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.type = type;
        this.location = location;
        this.amenity1 = amenity1;
        this.amenity2 = amenity2;
        this.amenity3 = amenity3;
        this.roomType = roomType;
        this.availableRooms = availableRooms;
        this.facilities = facilities;
        this.messAvailability = messAvailability;
        this.messCharges = messCharges;
        this.phone = phone;
        this.email = email;
        this.imageResourceName = imageResourceName;
    }
}