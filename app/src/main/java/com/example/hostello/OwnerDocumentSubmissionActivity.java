package com.example.hostello;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OwnerDocumentSubmissionActivity extends AppCompatActivity {

    private ImageView ivPreview;
    private CardView previewCard;
    private EditText etHostelName, etHostelAddress, etFacilities;
    private Button btnRoomImages, btnSubmit;
    private ProgressBar progressBar;

    private static final int ROOM_IMAGES_REQUEST = 1;
    private AppDatabase db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Default placeholder or the path to the selected image
    private String selectedImageUri = "hostel54";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_document_submission);

        // Initialize Database
        db = AppDatabase.getInstance(this);

        // Initialize Views
        ivPreview = findViewById(R.id.ivPreview);
        previewCard = findViewById(R.id.previewCard);
        etHostelName = findViewById(R.id.etHostelName);
        etHostelAddress = findViewById(R.id.etHostelAddress);
        etFacilities = findViewById(R.id.etFacilities);
        btnRoomImages = findViewById(R.id.btnRoomImages);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar); // Initialize if present in XML

        // Click Listeners
        btnRoomImages.setOnClickListener(v -> selectImages());
        btnSubmit.setOnClickListener(v -> submitForm());
    }

    private void selectImages() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, ROOM_IMAGES_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ROOM_IMAGES_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // Take persistable permission so the image loads after app restarts
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                selectedImageUri = uri.toString();

                ivPreview.setImageURI(uri);
                if (previewCard != null) {
                    previewCard.setVisibility(View.VISIBLE);
                }
                Toast.makeText(this, "Image selected!", Toast.LENGTH_SHORT).show();
            } catch (SecurityException e) {
                e.printStackTrace();
                Toast.makeText(this, "Permission error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void submitForm() {
        String name = etHostelName.getText().toString().trim();
        String address = etHostelAddress.getText().toString().trim();
        String facilities = etFacilities.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Hostel Name and Address are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. UI Loading State
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Hostel Details...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setEnabled(false);

        // 2. Create Object
        Hostel newHostel = new Hostel(
                name, "Price TBD", "5.0", "Private", address,
                "WiFi", "CCTV", "Laundry", "Standard", "Available",
                facilities, "Yes", "0", "0000000000", "owner@hostello.com",
                selectedImageUri
        );

        // 3. Background Save
        executor.execute(() -> {
            db.hostelDao().insertAll(newHostel);

            // Small delay so user can see the progress
            try { Thread.sleep(800); } catch (InterruptedException e) { e.printStackTrace(); }

            runOnUiThread(() -> {
                progressDialog.dismiss();
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                btnSubmit.setEnabled(true);

                Toast.makeText(this, "Hostel Registered Successfully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, OwnerDashboardActivity.class));
                finish();
            });
        });
    }
}