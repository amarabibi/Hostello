package com.example.hostello;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.HostelViewHolder> {

    private List<String> hostels;

    public HostelAdapter(List<String> hostels) {
        this.hostels = hostels;
    }

    @NonNull
    @Override
    public HostelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hostel, parent, false);
        return new HostelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelViewHolder holder, int position) {
        String hostelName = hostels.get(position);
        holder.hostelName.setText(hostelName);
    }

    @Override
    public int getItemCount() {
        return hostels.size();
    }

    static class HostelViewHolder extends RecyclerView.ViewHolder {
        TextView hostelName;

        HostelViewHolder(@NonNull View itemView) {
            super(itemView);
            hostelName = itemView.findViewById(R.id.hostelName);
        }
    }
}
