package com.example.hostello;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 🔹 Fixed: signup is now a Button/MaterialButton
        View signup = view.findViewById(R.id.botn_buy_sell);
        LinearLayout googleLogin = view.findViewById(R.id.googleLogin);
        LinearLayout facebookLogin = view.findViewById(R.id.facebookLogin);
        LinearLayout instaLogin = view.findViewById(R.id.insta);
        LinearLayout whatsappLogin = view.findViewById(R.id.whatsapp);

        // 🔹 Navigate to Owner Side (Registration/Intro)
        signup.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OwnerActivity.class);
            startActivity(intent);
        });

        // Open Google URL
        googleLogin.setOnClickListener(v -> openUrl("https://accounts.google.com/"));

        // Open Facebook URL
        facebookLogin.setOnClickListener(v -> openUrl("https://www.facebook.com/login/"));

        // Open Instagram URL
        instaLogin.setOnClickListener(v -> openUrl("https://www.instagram.com/accounts/login/"));

        // Open WhatsApp Web
        whatsappLogin.setOnClickListener(v -> openUrl("https://web.whatsapp.com/"));
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}