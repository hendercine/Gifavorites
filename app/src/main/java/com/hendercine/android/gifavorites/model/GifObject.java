/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/22/18 8:55 PM
 */

package com.hendercine.android.gifavorites.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
/**
 * Gifavorites created by artemis on 8/22/18.
 */

@Parcel(Parcel.Serialization.BEAN)
public class GifObject {

    @SuppressWarnings("WeakerAccess")
    @SerializedName("id")
    String mGifObjectId;
    @SuppressWarnings("WeakerAccess")
    @SerializedName("slug")
    String mGifObjectSlug;
    @SuppressWarnings("WeakerAccess")
    @SerializedName("url")
    String mGifObjecturl;
    @SuppressWarnings("WeakerAccess")
    @SerializedName("images")
    ArrayList<GiphyImage> mGiphyImages;

    public GifObject() {
    }

    public String getGifObjectId() {
        return mGifObjectId;
    }

    public void setGifObjectId(String gifObjectId) {
        mGifObjectId = gifObjectId;
    }

    public String getGifObjectSlug() {
        return mGifObjectSlug;
    }

    public void setGifObjectSlug(String gifObjectSlug) {
        mGifObjectSlug = gifObjectSlug;
    }

    public String getGifObjecturl() {
        return mGifObjecturl;
    }

    public void setGifObjecturl(String gifObjecturl) {
        mGifObjecturl = gifObjecturl;
    }

    public ArrayList<GiphyImage> getGiphyImages() {
        return mGiphyImages;
    }

    public void setGiphyImages(ArrayList<GiphyImage> giphyImages) {
        mGiphyImages = giphyImages;
    }
}
