package com.example.hostello;

import java.util.ArrayList;
import java.util.List;

public class HostelDataGenerator {

    public static List<Hostel> getPredefinedHostels() {
        List<Hostel> list = new ArrayList<>();

        list.add(new Hostel("Elite Boys Hostel", "PKR 12,000", "4.5", "Boys", "G-11 Markaz, Islamabad",
                "2-3 Seater", "Free WiFi", "AC Rooms", "2-3 Seater", "5 Rooms",
                "✓ Air Conditioning\n✓ Free WiFi\n✓ Study Tables", "Available (Optional)",
                "PKR 6,000/month", "+92 300 1234567", "elite.boys@hostel.com", "hostel_image_1"));

        list.add(new Hostel("Sunrise Girls Hostel", "PKR 15,000", "4.8", "Girls", "H-12 Near NUST",
                "Single Room", "Laundry", "Full Mess", "Single/Double", "2 Rooms",
                "✓ Laundry Service\n✓ 24/7 Security", "Included",
                "PKR 0/month", "+92 311 5556667", "sunrise.girls@hostel.com", "hostel_image_2"));

        list.add(new Hostel("Comfort Living Hostel", "PKR 10,500", "4.2", "Boys", "F-10 Sector",
                "Shared Room", "WiFi", "Mess Available", "3 Seater", "6 Rooms",
                "✓ Mess\n✓ Parking\n✓ CCTV", "Available",
                "PKR 5,000/month", "+92 333 1122334", "comfort@hostel.com", "hostel_image_3"));

        list.add(new Hostel("Royal Girls Residence", "PKR 16,000", "4.9", "Girls", "G-13 Sector",
                "Single Room", "Attached Bath", "Laundry", "Single", "3 Rooms",
                "✓ Attached Baths\n✓ Security Guard", "Included",
                "PKR 0", "+92 345 9876543", "royal@hostel.com", "hostel_image_4"));

        list.add(new Hostel("Student Haven", "PKR 9,000", "4.0", "Boys", "I-8 Markaz",
                "Shared Room", "WiFi", "Mess", "4 Seater", "10 Rooms",
                "✓ Budget Friendly\n✓ Water Filter", "Available",
                "PKR 4,500", "+92 301 2223344", "haven@hostel.com", "hostel_image_5"));

        list.add(new Hostel("Safe Stay Girls Hostel", "PKR 14,500", "4.7", "Girls", "F-8 Sector",
                "Double Room", "WiFi", "Laundry", "Double", "4 Rooms",
                "✓ Biometric Entry\n✓ Mess", "Included",
                "PKR 0", "+92 312 1112233", "safestay@hostel.com", "hostel_image_6"));

        list.add(new Hostel("City Boys Hostel", "PKR 11,000", "4.3", "Boys", "Blue Area",
                "Shared Room", "WiFi", "Parking", "3 Seater", "7 Rooms",
                "✓ Parking\n✓ Mess", "Available",
                "PKR 5,500", "+92 321 4445566", "cityboys@hostel.com", "hostel_image_7"));

        list.add(new Hostel("Peace Girls Hostel", "PKR 13,000", "4.6", "Girls", "G-9 Markaz",
                "Double Room", "WiFi", "Mess", "Double", "5 Rooms",
                "✓ Mess\n✓ Security", "Included",
                "PKR 0", "+92 333 7778899", "peace@hostel.com", "hostel_image_8"));

        list.add(new Hostel("Scholars Hostel", "PKR 8,500", "4.1", "Boys", "H-13",
                "Shared", "WiFi", "Mess", "4 Seater", "12 Rooms",
                "✓ Study Hall\n✓ WiFi", "Available",
                "PKR 4,000", "+92 300 5557788", "scholars@hostel.com", "hostel_image_9"));

        list.add(new Hostel("Modern Girls Hostel", "PKR 17,000", "4.9", "Girls", "E-11",
                "Single Deluxe", "AC", "Laundry", "Single Deluxe", "2 Rooms",
                "✓ AC Rooms\n✓ Mess\n✓ Security", "Included",
                "PKR 0", "+92 312 8889900", "modern@hostel.com", "hostel_image_10"));

        list.add(new Hostel("Pak Boys Hostel", "PKR 9,800", "4.2", "Boys", "G-10",
                "Shared", "WiFi", "Mess", "3 Seater", "8 Rooms",
                "✓ Mess\n✓ Laundry", "Available",
                "PKR 5,000", "+92 333 2233445", "pakboys@hostel.com", "hostel_image_11"));

        list.add(new Hostel("Bright Girls Residence", "PKR 15,200", "4.8", "Girls", "F-11",
                "Double", "WiFi", "AC", "Double", "3 Rooms",
                "✓ AC\n✓ Mess", "Included",
                "PKR 0", "+92 301 6677889", "bright@hostel.com", "hostel_image_12"));

        list.add(new Hostel("Capital Boys Hostel", "PKR 11,700", "4.4", "Boys", "I-10",
                "Shared", "WiFi", "Mess", "4 Seater", "6 Rooms",
                "✓ Mess\n✓ Security", "Available",
                "PKR 5,000", "+92 300 9998877", "capital@hostel.com", "hostel_image_13"));

        list.add(new Hostel("Grace Girls Hostel", "PKR 16,800", "4.9", "Girls", "G-12",
                "Single", "WiFi", "Laundry", "Single", "2 Rooms",
                "✓ Mess\n✓ CCTV", "Included",
                "PKR 0", "+92 311 1239876", "grace@hostel.com", "hostel_image_14"));

        list.add(new Hostel("Metro Boys Hostel", "PKR 10,200", "4.3", "Boys", "Rawalpindi Saddar",
                "Shared", "WiFi", "Parking", "3 Seater", "9 Rooms",
                "✓ Parking\n✓ Mess", "Available",
                "PKR 5,500", "+92 321 4567788", "metro@hostel.com", "hostel_image_15"));

        list.add(new Hostel("Horizon Girls Hostel", "PKR 14,900", "4.7", "Girls", "Bahria Town",
                "Double", "AC", "Mess", "Double", "4 Rooms",
                "✓ AC\n✓ Security", "Included",
                "PKR 0", "+92 333 1110099", "horizon@hostel.com", "hostel_image_16"));

        list.add(new Hostel("Unity Boys Hostel", "PKR 9,500", "4.1", "Boys", "G-15",
                "Shared", "WiFi", "Mess", "4 Seater", "10 Rooms",
                "✓ Mess\n✓ Study Area", "Available",
                "PKR 4,800", "+92 300 2221199", "unity@hostel.com", "hostel_image_17"));

        list.add(new Hostel("Serene Girls Hostel", "PKR 15,500", "4.8", "Girls", "F-17",
                "Single", "WiFi", "Laundry", "Single", "3 Rooms",
                "✓ Mess\n✓ Security", "Included",
                "PKR 0", "+92 312 5553344", "serene@hostel.com", "hostel_image_18"));

        list.add(new Hostel("Star Boys Hostel", "PKR 10,800", "4.4", "Boys", "I-9",
                "Shared", "WiFi", "Mess", "3 Seater", "7 Rooms",
                "✓ Mess\n✓ CCTV", "Available",
                "PKR 5,200", "+92 333 8882233", "star@hostel.com", "hostel_image_19"));

        list.add(new Hostel("Bloom Girls Hostel", "PKR 16,200", "4.9", "Girls", "E-7",
                "Single Deluxe", "AC", "Laundry", "Single Deluxe", "2 Rooms",
                "✓ AC\n✓ Mess\n✓ Security", "Included",
                "PKR 0", "+92 301 4445566", "bloom@hostel.com", "hostel_image_20"));

        return list;
    }
}
