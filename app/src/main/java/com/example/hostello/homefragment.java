package com.example.hostello;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private AppDatabase db;
    private RecyclerView recyclerView;
    private HostelAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homefragment, container, false);

        db = AppDatabase.getInstance(requireContext().getApplicationContext());

        recyclerView = view.findViewById(R.id.hostelRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        ImageView icUser = view.findViewById(R.id.ic_user);
        icUser.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .addToBackStack(null)
                    .commit();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> loadData(() -> swipeRefreshLayout.setRefreshing(false)));

        loadData(null);

        return view;
    }

    private void loadData(@Nullable Runnable onComplete) {
        executor.execute(() -> {
            List<Hostel> hostels = db.hostelDao().getAllHostels();

            // Seed database if empty
            if (hostels == null || hostels.isEmpty()) {
                db.hostelDao().insertAll(HostelDataGenerator.getPredefinedHostels().toArray(new Hostel[0]));
                hostels = db.hostelDao().getAllHostels();
            }

            final List<Hostel> finalHostels = (hostels != null) ? hostels : new ArrayList<>();

            if (isAdded() && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (!isAdded()) return;

                    if (adapter == null) {
                        // Corrected: Passing Context and List
                        adapter = new HostelAdapter(getContext(), finalHostels);
                        recyclerView.setAdapter(adapter);
                    } else {
                        // Corrected: Updating existing adapter
                        adapter.setHostels(finalHostels);
                        adapter.notifyDataSetChanged();
                    }

                    if (onComplete != null) onComplete.run();
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }
}