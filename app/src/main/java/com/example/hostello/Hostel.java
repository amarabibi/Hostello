package com.example.hostello;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "hostels")
public class Hostel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String price;
    public String rating;
    public String type; // Boys/Girls
    public String location;
    public String amenity1, amenity2, amenity3;
    public String roomType;
    public String availableRooms;
    public String facilities;
    public String messAvailability;
    public String messCharges;
    public String phone;
    public String email;
    public String imageResourceName; // drawable image name

    // ✅ Not stored in Room DB (only for UI expand/collapse)
    @Ignore
    public boolean isExpanded = false;

    // ✅ Constructor used by your app
    @Ignore
    public Hostel(String name, String price, String rating, String type, String location,
                  String amenity1, String amenity2, String amenity3, String roomType,
                  String availableRooms, String facilities, String messAvailability,
                  String messCharges, String phone, String email, String imageResourceName) {
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

    // ✅ Constructor required by Room
    public Hostel(int id, String name, String price, String rating, String type, String location,
                  String amenity1, String amenity2, String amenity3, String roomType,
                  String availableRooms, String facilities, String messAvailability,
                  String messCharges, String phone, String email, String imageResourceName) {
        this.id = id;
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
