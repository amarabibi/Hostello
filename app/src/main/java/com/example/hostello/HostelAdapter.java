package com.example.hostello;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.ViewHolder> {

    private Context context;
    private List<HostelModel> hostelList;

    public HostelAdapter(Context context, List<HostelModel> hostelList) {
        this.context = context;
        this.hostelList = hostelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hostel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HostelModel hostel = hostelList.get(position);

        holder.hostelName.setText(hostel.getName());
        holder.hostelAddress.setText(hostel.getLocation());
        holder.hostelImage.setImageResource(hostel.getImageResId());

        // Click on rating to open ReviewFragment
        holder.reviewRating.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("hostelName", hostel.getName());

            ReviewFragment reviewFragment = new ReviewFragment();
            reviewFragment.setArguments(bundle);

            ((FragmentActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, reviewFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return hostelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hostelImage;
        TextView hostelName, hostelAddress, reviewRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hostelImage = itemView.findViewById(R.id.hostelImage);
            hostelName = itemView.findViewById(R.id.hostelName);
            hostelAddress = itemView.findViewById(R.id.hostelLocation);
            reviewRating = itemView.findViewById(R.id.reviewRating);
        }
    }
}
