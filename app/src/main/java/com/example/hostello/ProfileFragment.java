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

    private Button btnCreateAccount, btnSignIn;
    private LinearLayout googleLogin, facebookLogin, whatsappLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Ensure the layout name matches your file: activity_profile.xml
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Buttons
        btnCreateAccount = view.findViewById(R.id.botn_buy_sell);
        btnSignIn = view.findViewById(R.id.botn_buy_sell2);

        // Initialize Social Layouts
        googleLogin = view.findViewById(R.id.googleLogin);
        facebookLogin = view.findViewById(R.id.facebookLogin);
        whatsappLogin = view.findViewById(R.id.whatsapp);

        // Navigate to Owner Side / Registration
        if (btnCreateAccount != null) {
            btnCreateAccount.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), buy_or_sell.class);
                startActivity(intent);
            });
        }

        // Sign In logic
        if (btnSignIn != null) {
            btnSignIn.setOnClickListener(v -> {
                // Add your Sign In activity navigation here
            });
        }

        // Social Login Links with safety checks
        if (googleLogin != null) {
            googleLogin.setOnClickListener(v -> openUrl("https://accounts.google.com/"));
        }

        if (facebookLogin != null) {
            facebookLogin.setOnClickListener(v -> openUrl("https://www.facebook.com/login/"));
        }

        if (whatsappLogin != null) {
            whatsappLogin.setOnClickListener(v -> openUrl("https://web.whatsapp.com/"));
        }
    }

    /**
     * Helper method to open external URLs in a browser
     * @param url The destination address
     */
    private void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            // Log the error or handle missing browser app
            e.printStackTrace();
        }
    }
}