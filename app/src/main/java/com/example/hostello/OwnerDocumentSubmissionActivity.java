package com.example.hostello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnerDocumentSubmissionActivity extends AppCompatActivity {

    private static final int ROOM_IMAGES = 1;
    private static final int FURNITURE_IMAGES = 2;
    private static final int FACILITY_IMAGES = 3;

    private TextInputEditText etHostelName, etHostelAddress, etFacilities;
    private TextView tvRoomCount, tvFurnitureCount, tvFacilityCount;
    private String mainImageUri = ""; // We'll use the first room image as the main display

    private AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_document_submission);

        db = AppDatabase.getInstance(this);

        // Initialize Input Fields
        etHostelName = findViewById(R.id.etHostelName);
        etHostelAddress = findViewById(R.id.etHostelAddress);
        etFacilities = findViewById(R.id.etFacilities);

        // Initialize Count TextViews
        tvRoomCount = findViewById(R.id.tvRoomImagesCount);
        tvFurnitureCount = findViewById(R.id.tvFurnitureImagesCount);
        tvFacilityCount = findViewById(R.id.tvFacilityImagesCount);

        // Initialize Buttons
        Button btnRoom = findViewById(R.id.btnRoomImages);
        Button btnFurniture = findViewById(R.id.btnFurnitureImages);
        Button btnFacility = findViewById(R.id.btnFacilityImages);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // Set Click Listeners
        btnRoom.setOnClickListener(v -> selectImages(ROOM_IMAGES));
        btnFurniture.setOnClickListener(v -> selectImages(FURNITURE_IMAGES));
        btnFacility.setOnClickListener(v -> selectImages(FACILITY_IMAGES));

        btnSubmit.setOnClickListener(v -> submitForm());
    }

    private void selectImages(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Allow multiple selection
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            int count = 0;
            Uri firstUri = null;

            if (data.getClipData() != null) {
                // User selected multiple images
                count = data.getClipData().getItemCount();
                firstUri = data.getClipData().getItemAt(0).getUri();
            } else if (data.getData() != null) {
                // User selected single image
                count = 1;
                firstUri = data.getData();
            }

            // Persist permission for the first image so we can show it in the list later
            if (firstUri != null) {
                final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                getContentResolver().takePersistableUriPermission(firstUri, takeFlags);

                if (requestCode == ROOM_IMAGES) {
                    mainImageUri = firstUri.toString();
                }
            }

            // Update the UI Counters
            String countText = count + " images selected";
            if (requestCode == ROOM_IMAGES) tvRoomCount.setText(countText);
            else if (requestCode == FURNITURE_IMAGES) tvFurnitureCount.setText(countText);
            else if (requestCode == FACILITY_IMAGES) tvFacilityCount.setText(countText);
        }
    }

    private void submitForm() {
        String name = etHostelName.getText().toString().trim();
        String address = etHostelAddress.getText().toString().trim();
        String facilities = etFacilities.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || mainImageUri.isEmpty()) {
            Toast.makeText(this, "Please fill required fields and upload room images", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            Hostel newHostel = new Hostel();
            newHostel.name = name;
            newHostel.location = address;
            newHostel.facilities = facilities;
            newHostel.imageResourceName = mainImageUri; // The persisted Gallery URI
            newHostel.price = "Contact for Price"; // Default placeholder
            newHostel.type = "Boys/Girls"; // Default placeholder
            newHostel.rating = "5.0";

            db.hostelDao().insertAll(newHostel);

            runOnUiThread(() -> {
                Toast.makeText(this, "Submission Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, OwnerDashboardActivity.class));
                finish();
            });
        });
    }
}