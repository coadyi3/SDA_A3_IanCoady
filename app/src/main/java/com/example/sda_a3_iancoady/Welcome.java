package com.example.sda_a3_iancoady;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple welcome page for the user which also informs them to swipe to view other tabs via @link(Toast)
 *
 * @author Ian Coady
 * @version 1.0
 * @since 2019-02-12
 */

public class Welcome extends Fragment {

    /*
     * OnCreation of the fragment it inflates the welcome fragment XML file inside the parent MainActivity XML container.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_welcome, container, false);

    }
}
