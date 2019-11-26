package com.example.sda_a3_iancoady;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/*
 * A simple {@link Fragment} subclass.
 * @author Chris Coughlan 2019
 */
public class ProductList extends Fragment {

    private static final String TAG = "RecyclerViewActivity";
    private ArrayList<TShirtAdapter> styles = new ArrayList<>();

    public ProductList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_product_list, container, false);
        // Create an ArrayList of AndroidFlavor objects
        styles.add(new TShirtAdapter("Black", "5€", R.drawable.black));
        styles.add(new TShirtAdapter("Blue", "2.50€", R.drawable.blue));
        styles.add(new TShirtAdapter("Yellow", "3€", R.drawable.yellow));
        styles.add(new TShirtAdapter("Red", "6€", R.drawable.red));
        styles.add(new TShirtAdapter("Pink", "10€", R.drawable.pink));
        styles.add(new TShirtAdapter("Orange", "7.50€", R.drawable.orange));
        styles.add(new TShirtAdapter("White", "1€", R.drawable.white));
        styles.add(new TShirtAdapter("Green", "12€", R.drawable.green));

        //start it with the view
        Log.d(TAG, "Starting recycler view");
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_view);
        TShirtViewAdapter recyclerViewAdapter = new TShirtViewAdapter(getContext(), styles);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
