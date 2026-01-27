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

import java.util.List;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.HostelViewHolder> {

    private final Context context;
    private List<Hostel> hostelList;

    // Constructor: Context first, then List
    public HostelAdapter(Context context, List<Hostel> hostelList) {
        this.context = context;
        this.hostelList = hostelList;
    }

    // Method to update data during Swipe-to-Refresh
    public void setHostels(List<Hostel> newList) {
        this.hostelList = newList;
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

        holder.tvName.setText(hostel.name);
        holder.tvPrice.setText(hostel.price);
        holder.tvLocation.setText(hostel.location);
        holder.tvRating.setText(hostel.rating != null ? hostel.rating : "0.0");

        // Image Loading with Glide (Handles both Gallery URIs and Drawables)
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
                holder.ivHostel.setImageResource(resId != 0 ? resId : R.drawable.hostel54);
            }
        } else {
            holder.ivHostel.setImageResource(R.drawable.hostel54);
        }

        // Click to open Detail Activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HostelDetailActivity.class);
            intent.putExtra("name", hostel.name);
            intent.putExtra("price", hostel.price);
            intent.putExtra("location", hostel.location);
            intent.putExtra("type", hostel.type);
            intent.putExtra("roomType", hostel.roomType);
            intent.putExtra("desc", hostel.facilities);
            intent.putExtra("phone", hostel.phone);
            intent.putExtra("image", hostel.imageResourceName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hostelList != null ? hostelList.size() : 0;
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