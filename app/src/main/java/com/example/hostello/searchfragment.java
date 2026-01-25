package com.example.hostello;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private LinearLayout searchCard;
    private SearchView searchView;
    private RecyclerView hostelRecyclerView;
    private HostelAdapter adapter;
    private List<String> hostelList; // sample hostel names
    private List<String> filteredList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchfragment, container, false);

        searchCard = view.findViewById(R.id.searchCard);
        searchView = view.findViewById(R.id.searchView);
        hostelRecyclerView = view.findViewById(R.id.hostelRecyclerView);

        // Sample hostels
        hostelList = new ArrayList<>();
        hostelList.add("Green Valley Hostel");
        hostelList.add("Sunrise Hostel");
        hostelList.add("Blue Sky Hostel");
        hostelList.add("River View Hostel");

        filteredList = new ArrayList<>(hostelList);

        // Setup RecyclerView
        adapter = new HostelAdapter(filteredList);
        hostelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hostelRecyclerView.setAdapter(adapter);

        // Click on card -> show search bar + RecyclerView
        searchCard.setOnClickListener(v -> {
            searchCard.setVisibility(View.GONE);
            searchView.setVisibility(View.VISIBLE);
            hostelRecyclerView.setVisibility(View.VISIBLE);
        });

        // Handle search input
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
        filteredList.clear();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(hostelList);
        } else {
            for (String hostel : hostelList) {
                if (hostel.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(hostel);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
