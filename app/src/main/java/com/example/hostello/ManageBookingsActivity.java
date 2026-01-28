package com.example.hostello;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManageBookingsActivity extends AppCompatActivity {

    private RecyclerView rvBookings;
    private AppDatabase db;
    private BookingAdapter adapter;
    private final List<Booking> bookingList = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bookings);

        // Hide the default Action Bar since we are using a custom TextView header in the XML
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = AppDatabase.getInstance(this);
        rvBookings = findViewById(R.id.rvBookings);

        rvBookings.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookingAdapter(bookingList);
        rvBookings.setAdapter(adapter);

        loadBookings();
    }

    private void loadBookings() {
        executor.execute(() -> {
            try {
                // Fetching all bookings/inquiries from the database
                List<Booking> fetchedBookings = db.hostelDao().getAllBookings();
                runOnUiThread(() -> {
                    bookingList.clear();
                    if (fetchedBookings != null && !fetchedBookings.isEmpty()) {
                        bookingList.addAll(fetchedBookings);
                    } else {
                        Toast.makeText(this, "No inquiries found.", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Error loading inquiries", Toast.LENGTH_SHORT).show());
            }
        });
    }

    /**
     * Updated Adapter for displaying Student Inquiries.
     * Fixed field references to match the Booking model (studentName, studentPhone, etc.)
     */
    private class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

        private final List<Booking> list;

        public BookingAdapter(List<Booking> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflates the professional card layout (item_booking_card.xml)
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_card, parent, false);
            return new BookingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
            Booking booking = list.get(position);

            // Using direct field access as per the Booking model definition
            holder.tvGuestName.setText("Student: " + booking.studentName);
            holder.tvHostelName.setText("Hostel: " + booking.hostelName);

            // Providing phone details in the subtitle for owner convenience
            String contactInfo = "Phone: " + (booking.studentPhone != null ? booking.studentPhone : "N/A");
            holder.tvBookingDate.setText(contactInfo);

            // Assuming status field exists, defaulting to "Pending" if null
            String status = (booking.status != null) ? booking.status : "Pending";
            holder.tvStatus.setText(status.toUpperCase());

            // Color coding the status indicator for quick visual reference
            if (status.equalsIgnoreCase("Confirmed")) {
                holder.statusIndicator.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            } else if (status.equalsIgnoreCase("Pending")) {
                holder.statusIndicator.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
            } else {
                holder.statusIndicator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class BookingViewHolder extends RecyclerView.ViewHolder {
            TextView tvGuestName, tvHostelName, tvBookingDate, tvStatus;
            View statusIndicator;
            MaterialCardView card;

            public BookingViewHolder(@NonNull View itemView) {
                super(itemView);
                tvGuestName = itemView.findViewById(R.id.tvGuestName);
                tvHostelName = itemView.findViewById(R.id.tvHostelName);
                tvBookingDate = itemView.findViewById(R.id.tvBookingDate);
                tvStatus = itemView.findViewById(R.id.tvStatus);
                statusIndicator = itemView.findViewById(R.id.statusIndicator);
                card = (MaterialCardView) itemView;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdown();
        }
    }
}