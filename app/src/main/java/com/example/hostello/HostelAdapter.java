package com.example.hostello;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.ViewHolder> {

    private List<Hostel> hostelList; // Removed 'final' so we can update it
    private final OnHostelClickListener listener;

    // Fixed Constructor: Removed the extra 'String phone' to match your HomeFragment call
    public HostelAdapter(List<Hostel> hostelList, OnHostelClickListener listener) {
        this.hostelList = hostelList;
        this.listener = listener;
    }

    // Method to update data during Refresh
    public void setHostels(List<Hostel> newList) {
        this.hostelList = newList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hostel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hostel h = hostelList.get(position);

        // 🔹 Basic Info
        holder.name.setText(h.name);
        holder.price.setText(h.price);
        holder.rating.setText("⭐ " + h.rating);
        holder.type.setText(h.type);
        holder.location.setText(h.location);

        // 🔹 Amenities
        holder.amenity1.setText(h.amenity1);
        holder.amenity2.setText(h.amenity2);
        holder.amenity3.setText(h.amenity3);

        // 🔹 Room & Facilities
        holder.roomVal.setText(h.roomType);
        holder.availRooms.setText(h.availableRooms);
        holder.facilities.setText(h.facilities);
        holder.messAvail.setText(h.messAvailability);
        holder.messPrice.setText(h.messCharges);

        // 🔹 Contact Info
        holder.phone.setText(h.phone);
        holder.email.setText(h.email);

        // 🔹 Image Handling
        int resId = holder.itemView.getContext().getResources()
                .getIdentifier(h.imageResourceName, "drawable",
                        holder.itemView.getContext().getPackageName());
        holder.hostelImg.setImageResource(resId != 0 ? resId : R.drawable.hostel54);

        // 🔹 Expand/Collapse Logic
        holder.expandableLayout.setVisibility(h.isExpanded ? View.VISIBLE : View.GONE);
        holder.viewDetailsBtn.setText(h.isExpanded ? "Hide Details" : "View Full Details");
        holder.viewDetailsBtn.setOnClickListener(v -> {
            h.isExpanded = !h.isExpanded;
            notifyItemChanged(position);
        });

        // 🔹 Rating Click → Open Reviews
        holder.rating.setOnClickListener(v -> {
            if (listener != null) listener.onReviewClick(h.name);
        });

        // 🔹 Visit Hostel Button → Open Detail Activity
        holder.visitHostelBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HostelDetailActivity.class);
            intent.putExtra("name", h.name);
            intent.putExtra("phone", h.phone);
            v.getContext().startActivity(intent);
        });

        // 🔹 FIXED: Call Button Logic
        holder.callNowBtn.setOnClickListener(v -> {
            if (h.phone != null && !h.phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + h.phone));
                v.getContext().startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "Phone not available", Toast.LENGTH_SHORT).show();
            }
        });

        // 📧 Email Logic
        holder.email.setOnClickListener(v -> {
            if (h.email != null && !h.email.isEmpty()) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + h.email));
                v.getContext().startActivity(emailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hostelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, rating, type, location;
        TextView amenity1, amenity2, amenity3;
        TextView roomVal, availRooms, facilities, messAvail, messPrice, phone, email;
        ImageView hostelImg;
        LinearLayout expandableLayout;
        MaterialButton viewDetailsBtn, visitHostelBtn, callNowBtn; // Added callNowBtn

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hostelName);
            price = itemView.findViewById(R.id.hostelPrice);
            rating = itemView.findViewById(R.id.ratingBadge);
            type = itemView.findViewById(R.id.typeBadge);
            location = itemView.findViewById(R.id.hostelLocation);
            amenity1 = itemView.findViewById(R.id.amenity1Text);
            amenity2 = itemView.findViewById(R.id.amenity2Text);
            amenity3 = itemView.findViewById(R.id.amenity3Text);
            roomVal = itemView.findViewById(R.id.roomTypeValue);
            availRooms = itemView.findViewById(R.id.availableRoomsValue);
            facilities = itemView.findViewById(R.id.facilitiesList);
            messAvail = itemView.findViewById(R.id.messAvailability);
            messPrice = itemView.findViewById(R.id.messChargesValue);
            phone = itemView.findViewById(R.id.contactPhone);
            email = itemView.findViewById(R.id.contactEmail);
            hostelImg = itemView.findViewById(R.id.hostelImage);
            expandableLayout = itemView.findViewById(R.id.expandableDetails);
            viewDetailsBtn = itemView.findViewById(R.id.viewDetailsBtn);
            visitHostelBtn = itemView.findViewById(R.id.visitHostelBtn);

            // Make sure this ID exists in your item_hostel.xml
            callNowBtn = itemView.findViewById(R.id.btnCallNow);
        }
    }

    public interface OnHostelClickListener {
        void onReviewClick(String hostelName);
    }
}