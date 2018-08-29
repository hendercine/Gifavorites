/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/23/18 1:44 PM
 */

package com.hendercine.android.giphygallery.model;

/**
 * Gifavorites created by artemis on 8/23/18.
 */

public class Gif {

    public static class ImageContainer {

        public String url;
        public String width;
        public String height;
    }

    public static class GiphyImage {

        public ImageContainer fixed_width_downsampled;
        public ImageContainer downsized_large;
    }

    public GiphyImage images;
    public String mTitle;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getUrl() {
        return images.fixed_width_downsampled.url;
    }

    public String getUrlLarge() {
        return images.downsized_large.url;
    }

}

