package com.example.hostello; // change to your package name

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotificationFragment extends Fragment {

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        View notif1 = view.findViewById(R.id.notif1);
        View notif2 = view.findViewById(R.id.notif2);
        View notif3 = view.findViewById(R.id.notif3);

        notif1.setOnClickListener(v -> openDetail(
                "Room Inspection Notice",
                "The hostel owner will inspect rooms tomorrow at 10 AM. Please ensure your room is clean and organized.",
                "2 hours ago"
        ));

        notif2.setOnClickListener(v -> openDetail(
                "Water Supply Maintenance",
                "Water supply will be unavailable from 2 PM to 5 PM due to maintenance work.",
                "Yesterday"
        ));

        notif3.setOnClickListener(v -> openDetail(
                "Mess Menu Updated",
                "The weekly mess menu has been updated. Please check the notice board for details.",
                "2 days ago"
        ));

        return view;
    }

    private void openDetail(String title, String message, String time) {
        Intent intent = new Intent(getActivity(), NotificationDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("time", time);
        startActivity(intent);
    }
}
