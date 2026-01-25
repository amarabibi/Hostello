package com.example.hostello;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.HostelViewHolder> {

    private final List<HostelModel> hostels;
    private final Context context;

    public HostelAdapter(Context context, List<HostelModel> hostels) {
        this.context = context;
        this.hostels = hostels;
    }

    @NonNull
    @Override
    public HostelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hostel, parent, false);
        return new HostelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelViewHolder holder, int position) {
        HostelModel hostel = hostels.get(position);

        holder.hostelName.setText(hostel.getName());
        holder.hostelLocation.setText(hostel.getLocation());
        holder.hostelImage.setImageResource(hostel.getImageResId()); // local image

        holder.viewDetailsBtn.setOnClickListener(v ->
                Toast.makeText(context, "Viewing details of " + hostel.getName(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return hostels.size();
    }

    static class HostelViewHolder extends RecyclerView.ViewHolder {
        TextView hostelName, hostelLocation;
        ImageView hostelImage;
        View viewDetailsBtn;

        HostelViewHolder(@NonNull View itemView) {
            super(itemView);
            hostelName = itemView.findViewById(R.id.hostelName);
            hostelLocation = itemView.findViewById(R.id.hostelLocation);
            hostelImage = itemView.findViewById(R.id.hostelImage);
            viewDetailsBtn = itemView.findViewById(R.id.viewDetailsBtn);
        }
    }
}
