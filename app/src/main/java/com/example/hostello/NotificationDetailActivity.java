package com.example.hostello; // use your package name

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification_detail_activity);

        TextView title = findViewById(R.id.detailTitle);
        TextView message = findViewById(R.id.detailMessage);
        TextView time = findViewById(R.id.detailTime);

        title.setText(getIntent().getStringExtra("title"));
        message.setText(getIntent().getStringExtra("message"));
        time.setText(getIntent().getStringExtra("time"));
    }
}
