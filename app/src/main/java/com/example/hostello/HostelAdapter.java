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

    private final List<Hostel> hostelList;
    private final OnHostelClickListener listener;

    public HostelAdapter(List<Hostel> hostelList, OnHostelClickListener listener) {
        this.hostelList = hostelList;
        this.listener = listener;
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

        // 🔹 Contact
        holder.phone.setText(h.phone);
        holder.email.setText(h.email);

        // 🔹 Image
        int resId = holder.itemView.getContext().getResources()
                .getIdentifier(h.imageResourceName, "drawable",
                        holder.itemView.getContext().getPackageName());
        holder.hostelImg.setImageResource(resId != 0 ? resId : R.drawable.hostel54);

        // 🔹 Expand/Collapse
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

        // 🔹 Visit Hostel Button → Open Detail Screen
        holder.visitHostelBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HostelDetailActivity.class);
            intent.putExtra("name", h.name);
            intent.putExtra("price", h.price);
            intent.putExtra("location", h.location);
            intent.putExtra("type", h.type);
            intent.putExtra("roomType", h.roomType);
            intent.putExtra("desc", h.facilities);
            intent.putExtra("mess", h.messAvailability + " (" + h.messCharges + ")");
            intent.putExtra("phone", h.phone);
            intent.putExtra("email", h.email);
            intent.putExtra("image", h.imageResourceName);
            v.getContext().startActivity(intent);
        });

        // 📞 Click Phone → Open Dialer
        holder.phone.setOnClickListener(v -> {
            if (h.phone != null && !h.phone.isEmpty()) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + h.phone));
                v.getContext().startActivity(dialIntent);
            } else {
                Toast.makeText(v.getContext(), "Phone number not available", Toast.LENGTH_SHORT).show();
            }
        });

        // 📧 Click Email → Open Email App
        holder.email.setOnClickListener(v -> {
            if (h.email != null && !h.email.isEmpty()) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + h.email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hostel Inquiry");
                v.getContext().startActivity(emailIntent);
            } else {
                Toast.makeText(v.getContext(), "Email not available", Toast.LENGTH_SHORT).show();
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
        MaterialButton viewDetailsBtn, visitHostelBtn;

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
        }
    }

    public interface OnHostelClickListener {
        void onReviewClick(String hostelName);
    }
}
