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
    private List<HostelModel> hostelList;
    private List<HostelModel> filteredList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.searchfragment, container, false);

        searchIcon = view.findViewById(R.id.searchIcon);
        searchLayout = view.findViewById(R.id.searchLayout);
        searchView = view.findViewById(R.id.searchView);
        hostelRecyclerView = view.findViewById(R.id.hostelRecyclerView);

        // Prevent null crashes
        if (searchLayout == null || searchIcon == null || searchView == null || hostelRecyclerView == null) {
            return view;
        }

        searchLayout.setVisibility(View.GONE);

        // Sample data
        hostelList = new ArrayList<>();
        hostelList.add(new HostelModel("Green Valley Hostel", "G-11 Markaz", R.drawable.hostel54));
        hostelList.add(new HostelModel("Sunrise Hostel", "F-10 Sector", R.drawable.hostel33));
        hostelList.add(new HostelModel("Blue Sky Hostel", "G-9 Sector", R.drawable.hostel13));
        hostelList.add(new HostelModel("River View Hostel", "G-8 Sector", R.drawable.hostel66));

        filteredList = new ArrayList<>(hostelList);

        adapter = new HostelAdapter(getContext(), filteredList);
        hostelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hostelRecyclerView.setAdapter(adapter);

        // Show search bar
        searchIcon.setOnClickListener(v -> {
            searchIcon.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
        });

        // Filter logic
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
            for (HostelModel hostel : hostelList) {
                if (hostel.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(hostel);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}
