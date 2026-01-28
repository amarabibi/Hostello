package com.example.hostello;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchFragment extends Fragment {

    private ImageView searchIcon;
    private View searchLayout;
    private SearchView searchView;
    private RecyclerView hostelRecyclerView;
    private HostelAdapter adapter;

    private List<Hostel> hostelList = new ArrayList<>();
    private AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.searchfragment, container, false);

        // Initialize Database
        db = AppDatabase.getInstance(getContext());

        searchIcon = view.findViewById(R.id.searchIcon);
        searchLayout = view.findViewById(R.id.searchLayout);
        searchView = view.findViewById(R.id.searchView);
        hostelRecyclerView = view.findViewById(R.id.hostelRecyclerView);

        if (searchLayout == null || searchIcon == null || searchView == null || hostelRecyclerView == null) {
            return view;
        }

        searchLayout.setVisibility(View.GONE);

        // Initialize Adapter with empty list initially
        adapter = new HostelAdapter(getContext(), new ArrayList<>(hostelList));
        hostelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hostelRecyclerView.setAdapter(adapter);

        // Load data from both Predefined source and Database
        loadAllHostels();

        // Search bar toggle
        searchIcon.setOnClickListener(v -> {
            searchIcon.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            searchView.setIconified(false);
            searchView.requestFocus();
        });

        // Search logic
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterHostels(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterHostels(newText);
                return true;
            }
        });

        return view;
    }

    private void loadAllHostels() {
        executor.execute(() -> {
            List<Hostel> allHostels = new ArrayList<>();

            // 1. Get Predefined Data
            allHostels.addAll(HostelDataGenerator.getPredefinedHostels());

            // 2. Fetch New Hostels from Database
            if (db != null && db.hostelDao() != null) {
                List<Hostel> dbHostels = db.hostelDao().getAllHostels();
                if (dbHostels != null) {
                    allHostels.addAll(dbHostels);
                }
            }

            // Update UI
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    hostelList.clear();
                    hostelList.addAll(allHostels);
                    adapter.setHostels(new ArrayList<>(hostelList));
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void filterHostels(String query) {
        List<Hostel> tempResultList = new ArrayList<>();

        if (TextUtils.isEmpty(query)) {
            tempResultList.addAll(hostelList);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Hostel hostel : hostelList) {
                // Check name or location
                boolean matchesName = hostel.name != null && hostel.name.toLowerCase().contains(lowerCaseQuery);
                boolean matchesLocation = hostel.location != null && hostel.location.toLowerCase().contains(lowerCaseQuery);

                if (matchesName || matchesLocation) {
                    tempResultList.add(hostel);
                }
            }
        }

        if (adapter != null) {
            adapter.setHostels(tempResultList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdown();
        }
    }
}