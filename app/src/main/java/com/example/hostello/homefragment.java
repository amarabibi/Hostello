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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private AppDatabase db;
    private RecyclerView recyclerView;
    private HostelAdapter adapter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homefragment, container, false);

        // 1️⃣ Initialize DB
        db = AppDatabase.getInstance(getContext());

        // 2️⃣ Initialize Views
        recyclerView = view.findViewById(R.id.hostelRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        ImageView icUser = view.findViewById(R.id.ic_user);
        icUser.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 3️⃣ Pull-to-Refresh
        swipeRefreshLayout.setOnRefreshListener(() -> loadData(() -> swipeRefreshLayout.setRefreshing(false)));

        // 4️⃣ Load Data initially
        loadData(null);

        return view;
    }

    private void loadData(@Nullable Runnable onComplete) {
        executor.execute(() -> {
            List<Hostel> hostels = db.hostelDao().getAllHostels();

            // Populate DB if empty
            if (hostels.isEmpty()) {
                db.hostelDao().insertAll(HostelDataGenerator.getPredefinedHostels().toArray(new Hostel[0]));
                hostels = db.hostelDao().getAllHostels();
            }

            final List<Hostel> finalHostels = hostels;
            if (isAdded() && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (adapter == null) {
                        adapter = new HostelAdapter(finalHostels, hostelName -> {
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
        executor.shutdown();
    }
}
