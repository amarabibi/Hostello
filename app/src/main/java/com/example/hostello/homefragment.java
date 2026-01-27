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

    // Using a single thread executor to handle database operations off the Main Thread
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homefragment, container, false);

        // 1️⃣ Initialize Database
        db = AppDatabase.getInstance(requireContext().getApplicationContext());

        // 2️⃣ Initialize UI Components
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

        // 3️⃣ Setup Pull-to-Refresh
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadData(() -> swipeRefreshLayout.setRefreshing(false));
        });

        // 4️⃣ Initial Data Load
        loadData(null);

        return view;
    }

    /**
     * Fetches data from Room Database and updates the RecyclerView.
     * @param onComplete Callback to stop the refreshing animation.
     */
    private void loadData(@Nullable Runnable onComplete) {
        executor.execute(() -> {
            // Fetch list from DAO
            List<Hostel> hostels = db.hostelDao().getAllHostels();

            // Seed database if this is the first run
            if (hostels == null || hostels.isEmpty()) {
                db.hostelDao().insertAll(HostelDataGenerator.getPredefinedHostels().toArray(new Hostel[0]));
                hostels = db.hostelDao().getAllHostels();
            }

            final List<Hostel> finalHostels = (hostels != null) ? hostels : new ArrayList<>();

            // Return to UI Thread to update the Adapter
            if (isAdded() && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    // Double check fragment state before updating UI
                    if (!isAdded()) return;

                    if (adapter == null) {
                        // Matching the 2-argument constructor: (List, Listener)
                        adapter = new HostelAdapter(finalHostels, hostelName -> {
                            // Logic for when a user clicks the rating/review section
                            Bundle bundle = new Bundle();
                            bundle.putString("hostelName", hostelName);
                            ReviewFragment rf = new ReviewFragment();
                            rf.setArguments(bundle);

                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, rf)
                                    .addToBackStack(null)
                                    .commit();
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        // Use the setter method we added to the Adapter
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
        // Prevent memory leaks by shutting down the executor
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }
}