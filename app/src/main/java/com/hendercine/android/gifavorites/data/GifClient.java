/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/18/18 7:38 PM
 */

package com.hendercine.android.gifavorites.data;

import com.hendercine.android.gifavorites.BuildConfig;
import com.hendercine.android.gifavorites.model.GiphyObject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Gifavorites created by artemis on 8/18/18.
 */
public class GifClient {

    private final static String API_KEY = BuildConfig.ApiKey;
    private static final String BASE_URL = "http://api.giphy.com/v1/";
    private static GifApiService mGifApiService;

    public GifClient() {
        Retrofit retrofit = getRetrofit();
        mGifApiService = retrofit.create(GifApiService.class);

    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient(API_KEY))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getHttpClient(String apiKey) {
        return new OkHttpClient.Builder().addInterceptor(new GiphyInterceptor(apiKey)).build();
    }

    public rx.Observable<GiphyObject> getTrendingGifs(int offset) {
        return mGifApiService.getTrending(offset);
    }

    public rx.Observable<GiphyObject> getSearchQuery(String query, int offset) {
        return mGifApiService.queryGiphy(query, offset);
    }
}
