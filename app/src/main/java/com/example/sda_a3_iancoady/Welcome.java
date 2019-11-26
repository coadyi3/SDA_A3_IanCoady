package com.example.sda_a3_iancoady;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Welcome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_welcome, container, false);

    }

    public void showToast(View view) {
        Toast informUser = Toast.makeText(getContext(), "Please swipe to view products!", Toast.LENGTH_SHORT);
        informUser.show();
    }
}
