import static java.security.AccessController.getContext;

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

import com.example.hostello.ReviewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {

    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<ReviewModel> reviewList;

    private RatingBar reviewRatingBar;
    private EditText reviewCommentInput;
    private Button submitReviewBtn;

    private String hostelName; // selected hostel

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_review, container, false);

        reviewRecyclerView = view.findViewById(R.id.reviewRecyclerView);
        reviewRatingBar = view.findViewById(R.id.reviewRatingBar);
        reviewCommentInput = view.findViewById(R.id.reviewCommentInput);
        submitReviewBtn = view.findViewById(R.id.submitReviewBtn);

        // Get hostel info from arguments
        if (getArguments() != null) {
            hostelName = getArguments().getString("hostelName", "Unknown Hostel");
        }

        // Display hostel name on top
        TextView hostelTitle = view.findViewById(R.id.hostelTitle);
        hostelTitle.setText(hostelName);

        // Load reviews for this hostel
        reviewList = getReviewsForHostel(hostelName);

        // Setup RecyclerView
        reviewAdapter = new ReviewAdapter(getContext(), reviewList);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewRecyclerView.setAdapter(reviewAdapter);

        // Submit review
        submitReviewBtn.setOnClickListener(v -> submitReview());

        return view;
    }

    private List<ReviewModel> getReviewsForHostel(String hostelName) {
        // Sample data, filter by hostel
        List<ReviewModel> allReviews = new ArrayList<>();
        allReviews.add(new ReviewModel("John Doe", "Jan 25, 2026", 4.5f, "The hostel was amazing, clean and cozy!", "Green Valley Hostel"));
        allReviews.add(new ReviewModel("Alice Smith", "Jan 20, 2026", 5.0f, "Excellent experience, very friendly staff.", "Sunrise Hostel"));
        allReviews.add(new ReviewModel("Michael", "Jan 18, 2026", 3.8f, "Decent place but a bit noisy at night.", "Green Valley Hostel"));

        List<ReviewModel> filtered = new ArrayList<>();
        for (ReviewModel review : allReviews) {
            if (review.getHostelName().equals(hostelName)) {
                filtered.add(review);
            }
        }
        return filtered;
    }

    private void submitReview() {
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

        ReviewModel newReview = new ReviewModel("You", "Today", rating, comment, hostelName);
        reviewList.add(0, newReview);
        reviewAdapter.notifyItemInserted(0);
        reviewRecyclerView.scrollToPosition(0);

        reviewRatingBar.setRating(0);
        reviewCommentInput.setText("");

        Toast.makeText(getContext(), "Review submitted!", Toast.LENGTH_SHORT).show();
    }
}
