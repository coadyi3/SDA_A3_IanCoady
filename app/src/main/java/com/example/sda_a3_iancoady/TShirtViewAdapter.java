package com.example.sda_a3_iancoady;
/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/*
 * @author Chris Coughlan 2019
 */
public class TShirtViewAdapter extends RecyclerView.Adapter<TShirtViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mNewContext;

    //add array for each item\
    private ArrayList<TShirtAdapter> tShirts;

    TShirtViewAdapter(Context mNewContext, ArrayList<TShirtAdapter> tShirts) {
        this.mNewContext = mNewContext;
        this.tShirts = tShirts;
    }

    //declare methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: was called");

        viewHolder.tShirtDesc.setText(tShirts.get(position).getTShirtDesc());
        viewHolder.tShirtPrice.setText(tShirts.get(position).getTShirtPrice());
        viewHolder.tShirtImage.setImageResource(tShirts.get(position).getImageResourceId());

        viewHolder.itemParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mNewContext, "You clicked on the " + tShirts.get(position).getTShirtDesc() +" t-shirt which costs " + tShirts.get(position).getTShirtPrice(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tShirts.size();
    }

    //viewholder class
    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView tShirtImage;
        TextView tShirtDesc;
        TextView tShirtPrice;
        RelativeLayout itemParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //grab the image, the text and the layout id's
            tShirtImage = itemView.findViewById(R.id.imageItem);
            tShirtDesc = itemView.findViewById(R.id.shirtDesc);
            tShirtPrice = itemView.findViewById(R.id.shirtPrice);
            itemParentLayout = itemView.findViewById(R.id.listItemLayout);
        }
    }
}
