package com.example.hostello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OwnerDocumentSubmissionActivity extends AppCompatActivity {

    private static final int ROOM_IMAGES = 1;
    private static final int FURNITURE_IMAGES = 2;
    private static final int FACILITY_IMAGES = 3;

    EditText etHostelName, etHostelAddress, etFacilities;
    Button btnRoomImages, btnFurnitureImages, btnFacilityImages, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_document_submission);

        etHostelName = findViewById(R.id.etHostelName);
        etHostelAddress = findViewById(R.id.etHostelAddress);
        etFacilities = findViewById(R.id.etFacilities);

        btnRoomImages = findViewById(R.id.btnRoomImages);
        btnFurnitureImages = findViewById(R.id.btnFurnitureImages);
        btnFacilityImages = findViewById(R.id.btnFacilityImages);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnRoomImages.setOnClickListener(v -> selectImages(ROOM_IMAGES));
        btnFurnitureImages.setOnClickListener(v -> selectImages(FURNITURE_IMAGES));
        btnFacilityImages.setOnClickListener(v -> selectImages(FACILITY_IMAGES));

        btnSubmit.setOnClickListener(v -> submitForm());
    }

    private void selectImages(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Toast.makeText(this, "Images selected successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitForm() {
        String hostelName = etHostelName.getText().toString().trim();
        String address = etHostelAddress.getText().toString().trim();

        if (hostelName.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Hostel details submitted", Toast.LENGTH_LONG).show();
        }
    }
}
