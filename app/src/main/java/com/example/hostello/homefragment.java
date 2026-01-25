package com.example.hostello;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class homefragment extends Fragment {

    @SuppressLint("SetTextI18n")
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

        // ===== HOSTEL CARD 6 =====
        LinearLayout details6 = view.findViewById(R.id.expandableDetails6);
        TextView viewDetails6 = view.findViewById(R.id.viewDetailsBtn6);

        viewDetails6.setOnClickListener(v -> {
            if (details6.getVisibility() == View.GONE) {
                details6.setVisibility(View.VISIBLE);
                viewDetails6.setText("Hide Details");
            } else {
                details6.setVisibility(View.GONE);
                viewDetails6.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 7 =====
        LinearLayout details7 = view.findViewById(R.id.expandableDetails7);
        TextView viewDetails7 = view.findViewById(R.id.viewDetailsBtn7);

        viewDetails7.setOnClickListener(v -> {
            if (details7.getVisibility() == View.GONE) {
                details7.setVisibility(View.VISIBLE);
                viewDetails7.setText("Hide Details");
            } else {
                details7.setVisibility(View.GONE);
                viewDetails7.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 8 =====
        LinearLayout details8 = view.findViewById(R.id.expandableDetails8);
        TextView viewDetails8 = view.findViewById(R.id.viewDetailsBtn8);

        viewDetails8.setOnClickListener(v -> {
            if (details8.getVisibility() == View.GONE) {
                details8.setVisibility(View.VISIBLE);
                viewDetails8.setText("Hide Details");
            } else {
                details8.setVisibility(View.GONE);
                viewDetails8.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 9 =====
        LinearLayout details9 = view.findViewById(R.id.expandableDetails9);
        TextView viewDetails9 = view.findViewById(R.id.viewDetailsBtn9);

        viewDetails9.setOnClickListener(v -> {
            if (details9.getVisibility() == View.GONE) {
                details9.setVisibility(View.VISIBLE);
                viewDetails9.setText("Hide Details");
            } else {
                details9.setVisibility(View.GONE);
                viewDetails9.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 10 =====
        LinearLayout details10 = view.findViewById(R.id.expandableDetails10);
        TextView viewDetails10 = view.findViewById(R.id.viewDetailsBtn10);

        viewDetails10.setOnClickListener(v -> {
            if (details10.getVisibility() == View.GONE) {
                details10.setVisibility(View.VISIBLE);
                viewDetails10.setText("Hide Details");
            } else {
                details10.setVisibility(View.GONE);
                viewDetails10.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 11 =====
        LinearLayout details11 = view.findViewById(R.id.expandableDetails11);
        TextView viewDetails11 = view.findViewById(R.id.viewDetailsBtn11);

        viewDetails11.setOnClickListener(v -> {
            if (details11.getVisibility() == View.GONE) {
                details11.setVisibility(View.VISIBLE);
                viewDetails11.setText("Hide Details");
            } else {
                details11.setVisibility(View.GONE);
                viewDetails11.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 12 =====
        LinearLayout details12 = view.findViewById(R.id.expandableDetails12);
        TextView viewDetails12 = view.findViewById(R.id.viewDetailsBtn12);

        viewDetails12.setOnClickListener(v -> {
            if (details12.getVisibility() == View.GONE) {
                details12.setVisibility(View.VISIBLE);
                viewDetails12.setText("Hide Details");
            } else {
                details12.setVisibility(View.GONE);
                viewDetails12.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 13 =====
        LinearLayout details13 = view.findViewById(R.id.expandableDetails13);
        TextView viewDetails13 = view.findViewById(R.id.viewDetailsBtn13);

        viewDetails13.setOnClickListener(v -> {
            if (details13.getVisibility() == View.GONE) {
                details13.setVisibility(View.VISIBLE);
                viewDetails13.setText("Hide Details");
            } else {
                details13.setVisibility(View.GONE);
                viewDetails13.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 14 =====
        LinearLayout details14 = view.findViewById(R.id.expandableDetails14);
        TextView viewDetails14 = view.findViewById(R.id.viewDetailsBtn14);

        viewDetails14.setOnClickListener(v -> {
            if (details14.getVisibility() == View.GONE) {
                details14.setVisibility(View.VISIBLE);
                viewDetails14.setText("Hide Details");
            } else {
                details14.setVisibility(View.GONE);
                viewDetails14.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 15 =====
        LinearLayout details15 = view.findViewById(R.id.expandableDetails15);
        TextView viewDetails15 = view.findViewById(R.id.viewDetailsBtn15);

        viewDetails15.setOnClickListener(v -> {
            if (details15.getVisibility() == View.GONE) {
                details15.setVisibility(View.VISIBLE);
                viewDetails15.setText("Hide Details");
            } else {
                details15.setVisibility(View.GONE);
                viewDetails15.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 16 =====
        LinearLayout details16 = view.findViewById(R.id.expandableDetails16);
        TextView viewDetails16 = view.findViewById(R.id.viewDetailsBtn16);

        viewDetails16.setOnClickListener(v -> {
            if (details16.getVisibility() == View.GONE) {
                details16.setVisibility(View.VISIBLE);
                viewDetails16.setText("Hide Details");
            } else {
                details16.setVisibility(View.GONE);
                viewDetails16.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 17 =====
        LinearLayout details17 = view.findViewById(R.id.expandableDetails17);
        TextView viewDetails17 = view.findViewById(R.id.viewDetailsBtn17);

        viewDetails17.setOnClickListener(v -> {
            if (details17.getVisibility() == View.GONE) {
                details17.setVisibility(View.VISIBLE);
                viewDetails17.setText("Hide Details");
            } else {
                details17.setVisibility(View.GONE);
                viewDetails17.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 18 =====
        LinearLayout details18 = view.findViewById(R.id.expandableDetails18);
        TextView viewDetails18 = view.findViewById(R.id.viewDetailsBtn18);

        viewDetails18.setOnClickListener(v -> {
            if (details18.getVisibility() == View.GONE) {
                details18.setVisibility(View.VISIBLE);
                viewDetails18.setText("Hide Details");
            } else {
                details18.setVisibility(View.GONE);
                viewDetails18.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 19 =====
        LinearLayout details19 = view.findViewById(R.id.expandableDetails19);
        TextView viewDetails19 = view.findViewById(R.id.viewDetailsBtn19);

        viewDetails19.setOnClickListener(v -> {
            if (details19.getVisibility() == View.GONE) {
                details19.setVisibility(View.VISIBLE);
                viewDetails19.setText("Hide Details");
            } else {
                details19.setVisibility(View.GONE);
                viewDetails19.setText("View Details");
            }
        });

        // ===== HOSTEL CARD 20 =====
        LinearLayout details20 = view.findViewById(R.id.expandableDetails20);
        TextView viewDetails20 = view.findViewById(R.id.viewDetailsBtn20);

        viewDetails20.setOnClickListener(v -> {
            if (details20.getVisibility() == View.GONE) {
                details20.setVisibility(View.VISIBLE);
                viewDetails20.setText("Hide Details");
            } else {
                details20.setVisibility(View.GONE);
                viewDetails20.setText("View Details");
            }
        });
        ImageView icUser = view.findViewById(R.id.ic_user);

        icUser.setOnClickListener(v -> {
            // Replace current fragment with ProfileFragment
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment()) // your container ID
                    .addToBackStack(null)
                    .commit();
        });



        return view;
    }



}