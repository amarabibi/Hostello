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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        db = AppDatabase.getInstance(requireContext());
        recyclerView = view.findViewById(R.id.notificationRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyTextView = view.findViewById(R.id.emptyTextView);

        TextView clearAll = view.findViewById(R.id.markAllRead);
        clearAll.setOnClickListener(v -> clearAllNotifications());

        loadNotifications();
        return view;
    }

    private void clearAllNotifications() {
        executor.execute(() -> {
            db.notificationDao().deleteAll();
            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    adapter = new NotificationAdapter(new ArrayList<>());
                    recyclerView.setAdapter(adapter);
                    updateVisibility(true);
                    Toast.makeText(getContext(), "All notifications cleared", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void loadNotifications() {
        executor.execute(() -> {
            List<Notification> listFromDb = db.notificationDao().getAllNotifications();

            // Seed dummy data if DB is empty
            if (listFromDb == null || listFromDb.isEmpty()) {
                db.notificationDao().insert(new Notification("Welcome to Hostello!", "Find your best stay today.", "Just now", false));
                db.notificationDao().insert(new Notification("Security Alert", "New login detected on your account.", "2h ago", false));
                listFromDb = db.notificationDao().getAllNotifications();
            }

            final List<Notification> finalList = (listFromDb != null) ? listFromDb : new ArrayList<>();

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    adapter = new NotificationAdapter(finalList);
                    recyclerView.setAdapter(adapter);
                    updateVisibility(finalList.isEmpty());
                });
            }
        });
    }

    private void updateVisibility(boolean isEmpty) {
        if (isEmpty) {
            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}