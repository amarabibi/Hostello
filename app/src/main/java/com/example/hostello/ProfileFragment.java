package com.example.hostello;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnBuySell = view.findViewById(R.id.botn_buy_sell);

        btnBuySell.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), intro.class);
            startActivity(intent);
        });

        return view;
    }
}
