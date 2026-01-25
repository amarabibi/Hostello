package com.example.hostello; // your package name

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Correct layout name
        setContentView(R.layout.fragment_notification_detail_activity);

        TextView title = findViewById(R.id.detailTitle);
        TextView message = findViewById(R.id.detailMessage);
        TextView time = findViewById(R.id.detailTime);

        // ✅ Prevent crash if data is null
        String t = getIntent().getStringExtra("title");
        String m = getIntent().getStringExtra("message");
        String ti = getIntent().getStringExtra("time");

        title.setText(t != null ? t : "Notification");
        message.setText(m != null ? m : "No details available.");
        time.setText(ti != null ? ti : "");
    }
}
