package com.example.hostello;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {

    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<ReviewModel> reviewList;

    private RatingBar reviewRatingBar;
    private EditText reviewCommentInput;
    private Button submitReviewBtn;

    private TextView ratingText; // optional TextView to show passed rating

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_review, container, false);

        // Initialize views
        reviewRecyclerView = view.findViewById(R.id.reviewRecyclerView);
        reviewRatingBar = view.findViewById(R.id.reviewRatingBar);
        reviewCommentInput = view.findViewById(R.id.reviewCommentInput);
        submitReviewBtn = view.findViewById(R.id.submitReviewBtn);
        ratingText = view.findViewById(R.id.reviewRating); // optional, if exists in layout

        // Handle rating passed from previous screen
        if (getArguments() != null) {
            String rating = getArguments().getString("rating", "N/A");
            if (ratingText != null) {
                ratingText.setText("Rating: " + rating);
            }
        }

        // Sample review data
        reviewList = new ArrayList<>();
        reviewList.add(new ReviewModel("John Doe", "Jan 25, 2026", 4.5f, "The hostel was amazing, clean and cozy!"));
        reviewList.add(new ReviewModel("Alice Smith", "Jan 20, 2026", 5.0f, "Excellent experience, very friendly staff."));
        reviewList.add(new ReviewModel("Michael", "Jan 18, 2026", 3.8f, "Decent place but a bit noisy at night."));

        // Setup RecyclerView
        reviewAdapter = new ReviewAdapter(getContext(), reviewList);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewRecyclerView.setAdapter(reviewAdapter);

        // Submit review button
        submitReviewBtn.setOnClickListener(v -> {
            float rating = reviewRatingBar.getRating();
            String comment = reviewCommentInput.getText().toString().trim();

            if (rating == 0) {
                Toast.makeText(getContext(), "Please provide a rating", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(comment)) {
                Toast.makeText(getContext(), "Please write a comment", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add new review at top
            ReviewModel newReview = new ReviewModel("You", "Today", rating, comment);
            reviewList.add(0, newReview);
            reviewAdapter.notifyItemInserted(0);
            reviewRecyclerView.scrollToPosition(0);

            // Reset inputs
            reviewRatingBar.setRating(0);
            reviewCommentInput.setText("");

            Toast.makeText(getContext(), "Review submitted!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
