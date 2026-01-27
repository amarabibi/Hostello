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

public class SearchFragment extends Fragment {

    private ImageView searchIcon;
    private View searchLayout;
    private SearchView searchView;
    private RecyclerView hostelRecyclerView;
    private HostelAdapter adapter;

    private List<Hostel> hostelList = new ArrayList<>();
    private List<Hostel> filteredList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.searchfragment, container, false);

        searchIcon = view.findViewById(R.id.searchIcon);
        searchLayout = view.findViewById(R.id.searchLayout);
        searchView = view.findViewById(R.id.searchView);
        hostelRecyclerView = view.findViewById(R.id.hostelRecyclerView);

        if (searchLayout == null || searchIcon == null || searchView == null || hostelRecyclerView == null) {
            return view;
        }

        searchLayout.setVisibility(View.GONE);

        // 1️⃣ Load predefined data (Clear first to prevent duplicates on fragment reload)
        hostelList.clear();
        hostelList.addAll(HostelDataGenerator.getPredefinedHostels());

        filteredList.clear();
        filteredList.addAll(hostelList);

        // 2️⃣ FIXED CONSTRUCTOR: Matches (Context, List)
        adapter = new HostelAdapter(getContext(), filteredList);

        hostelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hostelRecyclerView.setAdapter(adapter);

        // 3️⃣ Search bar toggle
        searchIcon.setOnClickListener(v -> {
            searchIcon.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            searchView.setIconified(false); // Opens search bar immediately
            searchView.requestFocus();
        });

        // 4️⃣ Search logic
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

    private void filterHostels(String query) {
        List<Hostel> tempResultList = new ArrayList<>();

        if (TextUtils.isEmpty(query)) {
            tempResultList.addAll(hostelList);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Hostel hostel : hostelList) {
                if (hostel.name.toLowerCase().contains(lowerCaseQuery)
                        || hostel.location.toLowerCase().contains(lowerCaseQuery)) {
                    tempResultList.add(hostel);
                }
            }
        }

        // Update the adapter using the method we added earlier
        if (adapter != null) {
            adapter.setHostels(tempResultList);
            adapter.notifyDataSetChanged();
        }
    }
}