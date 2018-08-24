/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/23/18 1:44 PM
 */

package com.hendercine.android.gifavorites.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
/**
 * Gifavorites created by artemis on 8/23/18.
 */

@Parcel(Parcel.Serialization.BEAN)
public class GiphyImage {

    @SuppressWarnings("WeakerAccess")
    @SerializedName("url")
    String mImageUrl;
    @SuppressWarnings("WeakerAccess")
    @SerializedName("width")
    String mImageWidth;
    @SuppressWarnings("WeakerAccess")
    @SerializedName("height")
    String mImageHeight;

    public GiphyImage() {
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getImageWidth() {
        return mImageWidth;
    }

    public void setImageWidth(String imageWidth) {
        mImageWidth = imageWidth;
    }

    public String getImageHeight() {
        return mImageHeight;
    }

    public void setImageHeight(String imageHeight) {
        mImageHeight = imageHeight;
    }
}

