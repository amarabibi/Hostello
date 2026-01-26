package com.example.hostello;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private String hostelName;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        db = AppDatabase.getInstance(getContext());
        recyclerView = view.findViewById(R.id.reviewRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Disable nested scrolling so it works smoothly inside the Activity's ScrollView
        recyclerView.setNestedScrollingEnabled(false);

        if (getArguments() != null) {
            hostelName = getArguments().getString("hostelName");
        }

        adapter = new ReviewAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Observe LiveData - The core of the auto-refresh
        if (hostelName != null) {
            db.reviewDao().getReviewsForHostel(hostelName).observe(getViewLifecycleOwner(), reviews -> {
                if (reviews != null) {
                    adapter.setReviews(reviews);
                }
            });
        }

        return view;
    }
}