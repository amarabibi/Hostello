package com.example.hostello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HostelDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_detail);

        // Initialize Views
        ImageView imageView = findViewById(R.id.detailHostelImage);
        TextView nameTv = findViewById(R.id.detailHostelName);
        TextView priceTv = findViewById(R.id.detailHostelPrice);
        TextView locationTv = findViewById(R.id.detailHostelLocation);
        TextView typeTv = findViewById(R.id.detailHostelType);
        TextView roomTv = findViewById(R.id.detailRoomType);
        TextView descTv = findViewById(R.id.detailHostelDesc);
        TextView messTv = findViewById(R.id.detailMessDetails);
        TextView messPriceTv = findViewById(R.id.detailMessPrice);
        Button callNowBtn = findViewById(R.id.btnCallNow);

        // Get data from Intent
        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String location = getIntent().getStringExtra("location");
        String type = getIntent().getStringExtra("type");
        String room = getIntent().getStringExtra("roomType");
        String desc = getIntent().getStringExtra("desc");
        String mess = getIntent().getStringExtra("mess");        // Example: "Available (Optional) (PKR 6000)"
        String phone = getIntent().getStringExtra("phone");      // Must pass phone via intent
        String imgName = getIntent().getStringExtra("image");

        // Set data to Views
        nameTv.setText(name);
        priceTv.setText(price);
        locationTv.setText("📍 " + location);
        typeTv.setText(type);
        roomTv.setText("Room: " + room);
        descTv.setText(desc);

        // Split mess into availability and charges if needed
        if (mess != null && mess.contains("(")) {
            int index = mess.lastIndexOf("(");
            String availability = mess.substring(0, index).trim();
            String charges = mess.substring(index).trim();
            messTv.setText("Availability: " + availability);
            messPriceTv.setText("Charges: " + charges);
        } else {
            messTv.setText(mess);
            messPriceTv.setText("");
        }

        // Load image safely
        if (imgName != null) {
            int resId = getResources().getIdentifier(imgName, "drawable", getPackageName());
            imageView.setImageResource(resId != 0 ? resId : R.drawable.hostel33);
        }

        // ✅ Call Now Button
        callNowBtn.setOnClickListener(v -> {
            if (phone != null && !phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });
    }
}
