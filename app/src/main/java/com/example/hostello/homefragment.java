package com.example.hostello;

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

import com.google.android.material.card.MaterialCardView;

public class homefragment extends Fragment {

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

        viewDetails1.setOnClickListener(v -> {
            if (details1.getVisibility() == View.GONE) {
                details1.setVisibility(View.VISIBLE);
                viewDetails1.setText("Hide Details");
            } else {
                details1.setVisibility(View.GONE);
                viewDetails1.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 2 =====
        LinearLayout details2 = view.findViewById(R.id.expandableDetails2);
        TextView viewDetails2 = view.findViewById(R.id.viewDetailsBtn2);

        viewDetails2.setOnClickListener(v -> {
            if (details2.getVisibility() == View.GONE) {
                details2.setVisibility(View.VISIBLE);
                viewDetails2.setText("Hide Details");
            } else {
                details2.setVisibility(View.GONE);
                viewDetails2.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 3 =====
        LinearLayout details3 = view.findViewById(R.id.expandableDetails3);
        TextView viewDetails3 = view.findViewById(R.id.viewDetailsBtn3);

        viewDetails3.setOnClickListener(v -> {
            if (details3.getVisibility() == View.GONE) {
                details3.setVisibility(View.VISIBLE);
                viewDetails3.setText("Hide Details");
            } else {
                details3.setVisibility(View.GONE);
                viewDetails3.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 4 =====
        LinearLayout details4 = view.findViewById(R.id.expandableDetails4);
        TextView viewDetails4 = view.findViewById(R.id.viewDetailsBtn4);

        viewDetails4.setOnClickListener(v -> {
            if (details4.getVisibility() == View.GONE) {
                details4.setVisibility(View.VISIBLE);
                viewDetails4.setText("Hide Details");
            } else {
                details4.setVisibility(View.GONE);
                viewDetails4.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 5 =====
        LinearLayout details5 = view.findViewById(R.id.expandableDetails5);
        TextView viewDetails5 = view.findViewById(R.id.viewDetailsBtn5);

        viewDetails5.setOnClickListener(v -> {
            if (details5.getVisibility() == View.GONE) {
                details5.setVisibility(View.VISIBLE);
                viewDetails5.setText("Hide Details");
            } else {
                details5.setVisibility(View.GONE);
                viewDetails5.setText("View Details");
            }
        });

        return view;
    }
}