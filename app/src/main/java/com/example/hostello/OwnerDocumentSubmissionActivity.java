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
    private String selectedImageUri = "hostel54";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_document_submission);

        db = AppDatabase.getInstance(this);

        ivPreview = findViewById(R.id.ivPreview);
        previewCard = findViewById(R.id.previewCard);
        etHostelName = findViewById(R.id.etHostelName);
        etHostelAddress = findViewById(R.id.etHostelAddress);
        etFacilities = findViewById(R.id.etFacilities);
        btnRoomImages = findViewById(R.id.btnRoomImages);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);

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

        if (requestCode == ROOM_IMAGES_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                try {
                    // Try to take persistable permission
                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    selectedImageUri = uri.toString();
                    ivPreview.setImageURI(uri);
                    if (previewCard != null) previewCard.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    // Fallback: If persistable fails, we still use the URI for this session
                    selectedImageUri = uri.toString();
                    ivPreview.setImageURI(uri);
                    Toast.makeText(this, "Note: Image link may not persist after reboot", Toast.LENGTH_SHORT).show();
                }
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

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Hostel Registry...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setEnabled(false);

        Hostel newHostel = new Hostel(
                name, "Price TBD", "5.0", "Private", address,
                "WiFi", "CCTV", "Laundry", "Standard", "Available",
                facilities, "Yes", "0", "0000000000", "owner@hostello.com",
                selectedImageUri
        );



        executor.execute(() -> {
            try {
                // If you are using auto-generate IDs, this creates a NEW entry.
                // Consider using db.hostelDao().update(newHostel) if the hostel already exists.
                db.hostelDao().insertAll(newHostel);

                Thread.sleep(500); // Short buffer for stability

                runOnUiThread(() -> {
                    if (!isFinishing()) {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Hostel Registered Successfully!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(this, OwnerDashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    if (!isFinishing()) {
                        progressDialog.dismiss();
                        btnSubmit.setEnabled(true);
                        if (progressBar != null) progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}