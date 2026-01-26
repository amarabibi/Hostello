package com.example.hostello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HostelDetailActivity extends AppCompatActivity {

    private AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private String hostelName;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_detail);

        db = AppDatabase.getInstance(this);

        // 1️⃣ Initialize Views
        ImageView imageView = findViewById(R.id.detailHostelImage);
        TextView nameTv = findViewById(R.id.detailHostelName);
        TextView priceTv = findViewById(R.id.detailHostelPrice);
        TextView locationTv = findViewById(R.id.detailHostelLocation);
        TextView typeTv = findViewById(R.id.detailHostelType);
        TextView roomTv = findViewById(R.id.detailRoomType);
        TextView descTv = findViewById(R.id.detailHostelDesc);
        TextView messTv = findViewById(R.id.detailMessDetails);
        TextView messPriceTv = findViewById(R.id.detailMessPrice);

        RatingBar avgRatingBar = findViewById(R.id.detailAvgRatingBar);
        TextView avgRatingTv = findViewById(R.id.detailAvgRatingText);

        Button callNowBtn = findViewById(R.id.btnCallNow);
        Button addReviewBtn = findViewById(R.id.btnAddReview);

        // 2️⃣ Get data from Intent
        hostelName = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String location = getIntent().getStringExtra("location");
        String type = getIntent().getStringExtra("type");
        String room = getIntent().getStringExtra("roomType");
        String desc = getIntent().getStringExtra("desc");
        String mess = getIntent().getStringExtra("mess");
        phone = getIntent().getStringExtra("phone");
        String imgName = getIntent().getStringExtra("image");

        // 3️⃣ Set basic data
        nameTv.setText(hostelName != null ? hostelName : "");
        priceTv.setText(price != null ? price : "");
        locationTv.setText(location != null ? "📍 " + location : "");
        typeTv.setText(type != null ? type : "");
        roomTv.setText(room != null ? "Room: " + room : "");
        descTv.setText(desc != null ? desc : "");

        // Mess parsing
        if (mess != null && mess.contains("(")) {
            int index = mess.lastIndexOf("(");
            messTv.setText("Availability: " + mess.substring(0, index).trim());
            messPriceTv.setText("Charges: " + mess.substring(index).trim());
        } else {
            messTv.setText(mess != null ? mess : "N/A");
            messPriceTv.setText("");
        }

        // Image
        if (imgName != null) {
            int resId = getResources().getIdentifier(imgName, "drawable", getPackageName());
            imageView.setImageResource(resId != 0 ? resId : R.drawable.hostel33);
        }

        // 4️⃣ Call Button
        callNowBtn.setOnClickListener(v -> {
            if (phone != null && !phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Phone number not available", Toast.LENGTH_SHORT).show();
            }
        });

        // 5️⃣ Add Review Button
        addReviewBtn.setOnClickListener(v -> showAddReviewDialog(hostelName));

        // 6️⃣ Insert dummy reviews if empty
        insertDummyReviews(hostelName);

        // 7️⃣ Load Reviews Fragment
        refreshReviews(hostelName);

        // 8️⃣ Load Average Rating
        executor.execute(() -> {
            float avg = db.reviewDao().getAverageRating(hostelName).getValue();
            runOnUiThread(() -> {
                if (avg > 0) {
                    avgRatingBar.setRating(avg);
                    avgRatingTv.setText(String.format(Locale.getDefault(), "(%.1f)", avg));
                } else {
                    avgRatingBar.setRating(0f);
                    avgRatingTv.setText("(No reviews)");
                }
            });
        });
    }

    private void insertDummyReviews(String hName) {
        executor.execute(() -> {
            int count = db.reviewDao().getReviewCount(hName);
            if (count == 0) {
                db.reviewDao().insertReview(new ReviewModel("Ali Khan", "24 Jan 2026", 5.0f, "Best hostel in the area! Very clean.", hName));
                db.reviewDao().insertReview(new ReviewModel("Zainab Bibi", "20 Jan 2026", 4.0f, "Great food and secure environment.", hName));
                db.reviewDao().insertReview(new ReviewModel("Hamza Ahmed", "15 Jan 2026", 3.5f, "Good place, but the Wi-Fi is a bit slow.", hName));
            }
        });
    }

    private void showAddReviewDialog(String hName) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        dialog.setContentView(view);

        RatingBar ratingBar = view.findViewById(R.id.inputRating);
        EditText nameInput = view.findViewById(R.id.inputName);
        EditText commentInput = view.findViewById(R.id.inputComment);
        Button submitBtn = view.findViewById(R.id.btnSubmitReview);

        submitBtn.setOnClickListener(v -> {
            String uName = nameInput.getText().toString().trim();
            String comment = commentInput.getText().toString().trim();
            float rating = ratingBar.getRating();
            String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

            if (!uName.isEmpty() && !comment.isEmpty()) {
                executor.execute(() -> {
                    db.reviewDao().insertReview(new ReviewModel(uName, date, rating, comment, hName));
                    runOnUiThread(() -> {
                        dialog.dismiss();
                        Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show();
                    });
                });
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void refreshReviews(String hName) {
        Bundle bundle = new Bundle();
        bundle.putString("hostelName", hName);
        ReviewFragment rf = new ReviewFragment();
        rf.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.reviewFragmentContainer, rf)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
