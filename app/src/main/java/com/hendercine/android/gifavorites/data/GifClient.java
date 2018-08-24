/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/18/18 7:38 PM
 */

package com.hendercine.android.gifavorites.data;

import com.hendercine.android.gifavorites.BuildConfig;
import com.hendercine.android.gifavorites.model.GifObject;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Gifavorites created by artemis on 8/18/18.
 */
public class GifClient {

    private static final String BASE_URL = "http://api.giphy.com/v1/gifs/";
    private static final String API_KEY = BuildConfig.ApiKey;

    private static GifClient instance;
    private static GifService gifService;

    private GifClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        gifService = retrofit.create(GifService.class);
    }

    public static GifClient getInstance() {
        if (instance == null) {
            instance = new GifClient();
        }
        return instance;
    }

    public rx.Observable<ArrayList<GifObject>> getGiphyJson(String query) {
        return gifService.searchGifs(query + "&", API_KEY);
    }
}
