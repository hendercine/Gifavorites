/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/22/18 8:55 PM
 */

package com.hendercine.android.giphygallery.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
/**
 * Gifavorites created by artemis on 8/22/18.
 */


public class GiphyObject {

    @SuppressWarnings("WeakerAccess")
    @SerializedName("data")
    ArrayList<Gif> mGifs;

    public ArrayList<Gif> getGifs() {
        return mGifs;
    }

    public Gif getImagesData(int pos) {
        return mGifs.get(pos);
    }

    public void appendImagesData(ArrayList<Gif> data) {
        this.mGifs.addAll(data);
    }
}
