package com.example.hostello;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotifViewHolder> {

    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
        Notification notif = notificationList.get(position);

        holder.tvTitle.setText(notif.title);
        holder.tvMessage.setText(notif.message);
        holder.tvTime.setText(notif.time);

        // UI state for unread notifications
        holder.statusIndicator.setVisibility(notif.isRead ? View.GONE : View.VISIBLE);
        holder.itemView.setAlpha(notif.isRead ? 0.7f : 1.0f);

        holder.itemView.setOnClickListener(v -> {
            if (!notif.isRead) {
                notif.isRead = true;
                notifyItemChanged(position);
                // Note: You should also update the DB state here via a callback or DAO
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList != null ? notificationList.size() : 0;
    }

    public static class NotifViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMessage, tvTime;
        View statusIndicator;
        ImageView notifIcon;

        public NotifViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.notifTitle);
            tvMessage = itemView.findViewById(R.id.notifMessage);
            tvTime = itemView.findViewById(R.id.notifTime);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
            notifIcon = itemView.findViewById(R.id.notifIcon);
        }
    }
}