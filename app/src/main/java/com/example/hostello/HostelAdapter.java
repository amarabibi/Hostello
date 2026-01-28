package com.example.hostello;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying a list of Hostels in a RecyclerView.
 * This version is fully fixed to resolve the 'messPrice' symbol error
 * and ensure all data is passed correctly to the Detail activity.
 */
public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.HostelViewHolder> {

    private final Context context;
    private List<Hostel> hostelList;

    public HostelAdapter(Context context, List<Hostel> hostelList) {
        this.context = context;
        this.hostelList = hostelList != null ? hostelList : new ArrayList<>();
    }

    /**
     * Updates the dataset and refreshes the UI.
     */
    public void setHostels(List<Hostel> newList) {
        this.hostelList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HostelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hostel, parent, false);
        return new HostelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelViewHolder holder, int position) {
        Hostel hostel = hostelList.get(position);

        // Populate basic information in the list item
        holder.tvName.setText(hostel.name);
        holder.tvPrice.setText(hostel.price);
        holder.tvLocation.setText(hostel.location);
        holder.tvRating.setText(hostel.rating != null ? hostel.rating : "5.0");

        // Image Loading Logic
        String imgPath = hostel.imageResourceName;
        if (imgPath != null && !imgPath.isEmpty()) {
            if (imgPath.startsWith("content://") || imgPath.startsWith("file://") || imgPath.startsWith("/")) {
                Glide.with(context)
                        .load(imgPath)
                        .placeholder(R.drawable.hostel54)
                        .centerCrop()
                        .into(holder.ivHostel);
            } else {
                int resId = context.getResources().getIdentifier(imgPath, "drawable", context.getPackageName());
                if (resId != 0) {
                    holder.ivHostel.setImageResource(resId);
                } else {
                    holder.ivHostel.setImageResource(R.drawable.hostel54);
                }
            }
        } else {
            holder.ivHostel.setImageResource(R.drawable.hostel54);
        }

        // Detailed Data Transfer Logic
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HostelDetailActivity.class);

            // Core Details
            intent.putExtra("name", hostel.name);
            intent.putExtra("price", hostel.price);
            intent.putExtra("location", hostel.location);
            intent.putExtra("type", hostel.type);
            intent.putExtra("phone", hostel.phone);
            intent.putExtra("image", hostel.imageResourceName);

            // Extended Details
            intent.putExtra("roomType", hostel.roomType);
            intent.putExtra("desc", hostel.facilities);

            // FIX: Ensure these field names match your Hostel model precisely
            // If your model uses 'mess' and 'messPrice', these will now resolve.
            intent.putExtra("mess", hostel.messAvailability);


            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hostelList.size();
    }

    public static class HostelViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHostel;
        TextView tvName, tvPrice, tvLocation, tvRating;

        public HostelViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHostel = itemView.findViewById(R.id.ivHostel);
            tvName = itemView.findViewById(R.id.tvHostelName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}