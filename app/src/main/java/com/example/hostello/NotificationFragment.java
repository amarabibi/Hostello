package com.example.hostello;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppDatabase db;
    private NotificationAdapter adapter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private TextView emptyTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        // 1️⃣ Initialize DB and Views
        db = AppDatabase.getInstance(getContext());
        recyclerView = view.findViewById(R.id.notificationRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        emptyTextView = view.findViewById(R.id.emptyTextView); // Optional
        TextView clearAll = view.findViewById(R.id.markAllRead);

        // 2️⃣ Clear All Notifications Click
        clearAll.setOnClickListener(v -> clearAllNotifications());

        // 3️⃣ Load notifications
        loadNotifications();

        return view;
    }

    private void clearAllNotifications() {
        executor.execute(() -> {
            db.notificationDao().deleteAll();

            if (isAdded() && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (adapter != null) {
                        adapter.updateData(new ArrayList<>());
                        Toast.makeText(getContext(), "Notifications cleared", Toast.LENGTH_SHORT).show();
                        emptyTextView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void loadNotifications() {
        executor.execute(() -> {
            List<Notification> list = db.notificationDao().getAllNotifications();

            // Seed sample data if empty
            if (list.isEmpty()) {
                db.notificationDao().insert(
                        new Notification("Room Inspection", "Owner will visit at 10 AM.", "2 hours ago"),
                        new Notification("Mess Update", "Biryani is served today!", "Yesterday")
                );
                list = db.notificationDao().getAllNotifications();
            }

            final List<Notification> finalList = list;

            if (isAdded() && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    adapter = new NotificationAdapter(finalList);
                    recyclerView.setAdapter(adapter);

                    if (finalList.isEmpty()) {
                        emptyTextView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyTextView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
