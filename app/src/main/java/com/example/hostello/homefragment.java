package com.example.hostello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class homefragment extends Fragment {

    // Phone numbers for each hostel
    private static final String PHONE_1 = "+923001234567";  // Elite Boys Hostel
    private static final String PHONE_2 = "+923012345678";  // Comfort Girls Hostel
    private static final String PHONE_3 = "+923023456789";  // Premium Boys Hostel
    private static final String PHONE_4 = "+923034567890";  // Royal Girls Hostel
    private static final String PHONE_5 = "+923045678901";  // Student Boys Hostel

    // Helper method to make a phone call
    private void makePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        // Inflate the layout first
        View view = inflater.inflate(R.layout.homefragment, container, false);



        // ===== HOSTEL CARD 1 =====
        LinearLayout details1 = view.findViewById(R.id.expandableDetails1);
        TextView viewDetails1 = view.findViewById(R.id.viewDetailsBtn1);
        MaterialButton callNowBtn1 = view.findViewById(R.id.callNowBtn1);

        viewDetails1.setOnClickListener(v -> {
            if (details1.getVisibility() == View.GONE) {
                details1.setVisibility(View.VISIBLE);
                viewDetails1.setText("Hide Details");
            } else {
                details1.setVisibility(View.GONE);
                viewDetails1.setText("View Details");
            }
        });

        callNowBtn1.setOnClickListener(v -> makePhoneCall(PHONE_1));

        // ===== HOSTEL CARD 2 =====
        LinearLayout details2 = view.findViewById(R.id.expandableDetails2);
        TextView viewDetails2 = view.findViewById(R.id.viewDetailsBtn2);
        MaterialButton callNowBtn2 = view.findViewById(R.id.callNowBtn2);

        viewDetails2.setOnClickListener(v -> {
            if (details2.getVisibility() == View.GONE) {
                details2.setVisibility(View.VISIBLE);
                viewDetails2.setText("Hide Details");
            } else {
                details2.setVisibility(View.GONE);
                viewDetails2.setText("View Details");
            }
        });

        callNowBtn2.setOnClickListener(v -> makePhoneCall(PHONE_2));

        // ===== HOSTEL CARD 3 =====
        LinearLayout details3 = view.findViewById(R.id.expandableDetails3);
        TextView viewDetails3 = view.findViewById(R.id.viewDetailsBtn3);
        MaterialButton callNowBtn3 = view.findViewById(R.id.callNowBtn3);

        viewDetails3.setOnClickListener(v -> {
            if (details3.getVisibility() == View.GONE) {
                details3.setVisibility(View.VISIBLE);
                viewDetails3.setText("Hide Details");
            } else {
                details3.setVisibility(View.GONE);
                viewDetails3.setText("View Details");
            }
        });

        callNowBtn3.setOnClickListener(v -> makePhoneCall(PHONE_3));

        // ===== HOSTEL CARD 4 =====
        LinearLayout details4 = view.findViewById(R.id.expandableDetails4);
        TextView viewDetails4 = view.findViewById(R.id.viewDetailsBtn4);
        MaterialButton callNowBtn4 = view.findViewById(R.id.callNowBtn4);

        viewDetails4.setOnClickListener(v -> {
            if (details4.getVisibility() == View.GONE) {
                details4.setVisibility(View.VISIBLE);
                viewDetails4.setText("Hide Details");
            } else {
                details4.setVisibility(View.GONE);
                viewDetails4.setText("View Details");
            }
        });

        callNowBtn4.setOnClickListener(v -> makePhoneCall(PHONE_4));

        // ===== HOSTEL CARD 5 =====
        LinearLayout details5 = view.findViewById(R.id.expandableDetails5);
        TextView viewDetails5 = view.findViewById(R.id.viewDetailsBtn5);
        MaterialButton callNowBtn5 = view.findViewById(R.id.callNowBtn5);

        viewDetails5.setOnClickListener(v -> {
            if (details5.getVisibility() == View.GONE) {
                details5.setVisibility(View.VISIBLE);
                viewDetails5.setText("Hide Details");
            } else {
                details5.setVisibility(View.GONE);
                viewDetails5.setText("View Details");
            }
        });

        callNowBtn5.setOnClickListener(v -> makePhoneCall(PHONE_5));

        return view;
    }
}