package com.example.hostello;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homefragment, container, false);

        // ===== EXAMPLE: GETTING A HOSTEL CARD =====
        LinearLayout details1 = view.findViewById(R.id.expandableDetails1);
        TextView viewDetails1 = view.findViewById(R.id.viewDetailsBtn1);

        viewDetails1.setOnClickListener(v -> {
            if (details1.getVisibility() == View.GONE) {
                details1.setVisibility(View.VISIBLE);
                viewDetails1.setText("Hide Details");
            } else {
                details1.setVisibility(View.GONE);
                viewDetails1.setText("View Details");
            }
        });

        // ===== EXAMPLE: REVIEW COMMENT CLICK =====
        // Make sure your TextView in XML has android:id="@+id/reviewComment1"
        TextView reviewComment = view.findViewById(R.id.ReviewComment);

        reviewComment.setOnClickListener(v -> {
            // Create a bundle to pass hostel info
            Bundle bundle = new Bundle();
            bundle.putString("hostelName", "Green Valley Hostel"); // pass hostel name

            // Create fragment instance and pass arguments
            ReviewFragment reviewFragment = new ReviewFragment();
            reviewFragment.setArguments(bundle);

            // Open ReviewFragment
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, reviewFragment) // must be instance, not class
                    .addToBackStack(null)
                    .commit();
        });

        // ===== USER ICON CLICK =====
        ImageView icUser = view.findViewById(R.id.ic_user);
        icUser.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
