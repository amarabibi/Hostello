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

        // ===== LOOP TO HANDLE EXPANDABLE HOSTEL DETAILS =====
        int totalHostels = 20; // number of hostel cards
        for (int i = 1; i <= totalHostels; i++) {
            int detailsId = getResources().getIdentifier("expandableDetails" + i, "id", getActivity().getPackageName());
            int btnId = getResources().getIdentifier("viewDetailsBtn" + i, "id", getActivity().getPackageName());

            LinearLayout detailsLayout = view.findViewById(detailsId);
            TextView viewDetailsBtn = view.findViewById(btnId);

            if (detailsLayout != null && viewDetailsBtn != null) {
                viewDetailsBtn.setOnClickListener(v -> {
                    if (detailsLayout.getVisibility() == View.GONE) {
                        detailsLayout.setVisibility(View.VISIBLE);
                        viewDetailsBtn.setText("Hide Details");
                    } else {
                        detailsLayout.setVisibility(View.GONE);
                        viewDetailsBtn.setText("View Details");
                    }
                });
            }
        }

        // ===== LOOP TO HANDLE REVIEW COMMENT CLICKS =====
        int totalReviews = 10; // number of review comment TextViews
        String[] hostelNames = new String[]{
                "Green Valley Hostel",
                "Sunrise Hostel",
                "Blue Sky Hostel",
                "River View Hostel",
                "Sunset Hostel",
                "Moonlight Hostel",
                "Star Hostel",
                "Ocean View Hostel",
                "Mountain Hostel",
                "City Hostel"
        };

        for (int i = 1; i <= totalReviews; i++) {
            int reviewId = getResources().getIdentifier("ReviewComment" + i, "id", getActivity().getPackageName());
            TextView reviewComment = view.findViewById(reviewId);

            if (reviewComment != null) {
                final String hostelName = hostelNames[i - 1];
                reviewComment.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("hostelName", hostelName);

                    ReviewFragment reviewFragment = new ReviewFragment();
                    reviewFragment.setArguments(bundle);

                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, reviewFragment)
                            .addToBackStack(null)
                            .commit();
                });
            }
        }

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
