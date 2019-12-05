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

/**
 * {@link TShirtAdapter} represents a single Android platform release.
 * Each object has 3 properties: name, version number, and image resource ID.
 * This is a basic arrayAdapter
 */
public class TShirtAdapter {

    // style of tshirt
    private String tStyleName;

    // price of tshirt
    private String tPrice;

    // Drawable resource ID
    private int tImage;

    /*
     * Create a new TShirtAdapter object.
     * @param tName is the name of the tshirt style
     * @param tPriceVal is the corresponding price for that style
     * @param image is drawable reference ID that corresponds to image of the style of tshirt
     *
     * */
    public TShirtAdapter(String tName, String tPriceVal, int imageResourceId)
    {
        tStyleName = tName;
        tPrice = tPriceVal;
        tImage = imageResourceId;
    }

    /*
     * Get the tshirt style
     */
    public String getTShirtDesc() {
        return tStyleName;
    }

    /*
     * Get the tshirt price
     */
    public String getTShirtPrice() {
        return tPrice;
    }

    /*
     * Get the image resource ID
     */
    public int getImageResourceId() {
        return tImage;
    }

}

