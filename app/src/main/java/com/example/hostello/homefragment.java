package com.example.hostello;
import android.content.Context;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
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

    // NEW: Variable to hold the animator so we can stop it if needed
    private ValueAnimator colorAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homefragment, container, false);

        db = AppDatabase.getInstance(requireContext().getApplicationContext());

        // --- 1. SETUP HEADER ANIMATION ---
        // Find the layout you ID'd in Step 1
        View headerView = view.findViewById(R.id.headerContainer);
        if (headerView != null) {
            animateHeaderColors(headerView);
        }

        recyclerView = view.findViewById(R.id.hostelRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        ImageView icUser = view.findViewById(R.id.ic_user);
        animatePopIn(icUser);

        icUser.setOnClickListener(v -> {
            v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(100).withEndAction(() -> {
                v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                navigateToProfile();
            }).start();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> loadData(() -> swipeRefreshLayout.setRefreshing(false)));

        loadData(null);

        return view;
    }

    // --- NEW METHOD: Animates Background Color ---
    private void animateHeaderColors(View view) {
        // Define your colors (e.g., Blue to Purple)
        int colorFrom = Color.parseColor("#1976D2"); // Dark Blue
        int colorTo = Color.parseColor("#7B1FA2");   // Purple

        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(3000); // 3 seconds to change
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE); // Loop forever
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE); // Go back and forth smoothly

        colorAnimation.addUpdateListener(animator -> {
            if (view != null) {
                view.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });

        colorAnimation.start();
    }

    private void navigateToProfile() {
        if (getParentFragmentManager() != null) {
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void animatePopIn(View view) {
        view.setScaleX(0f);
        view.setScaleY(0f);
        view.animate()
                .scaleX(1f).scaleY(1f)
                .setDuration(500)
                .setStartDelay(200)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    private void loadData(@Nullable Runnable onComplete) {
        executor.execute(() -> {
            List<Hostel> hostels = db.hostelDao().getAllHostels();
            if (hostels == null || hostels.isEmpty()) {
                db.hostelDao().insertAll(HostelDataGenerator.getPredefinedHostels().toArray(new Hostel[0]));
                hostels = db.hostelDao().getAllHostels();
            }
            final List<Hostel> finalHostels = (hostels != null) ? hostels : new ArrayList<>();

            if (isAdded() && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (!isAdded()) return;

                    if (adapter == null) {
                        adapter = new HostelAdapter(getContext(), finalHostels);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.setHostels(finalHostels);
                        adapter.notifyDataSetChanged();
                    }
                    runLayoutAnimation(recyclerView);
                    if (onComplete != null) onComplete.run();
                });
            }
        });
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_up);
        recyclerView.setLayoutAnimation(controller);
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // CLEANUP: Stop animation when fragment is destroyed to prevent memory leaks
        if (colorAnimation != null && colorAnimation.isRunning()) {
            colorAnimation.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }
}