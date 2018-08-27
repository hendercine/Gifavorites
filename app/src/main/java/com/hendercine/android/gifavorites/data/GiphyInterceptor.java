/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/27/18 10:23 AM
 */

package com.hendercine.android.gifavorites.data;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Gifavorites created by artemis on 8/27/18.
 */
class GiphyInterceptor implements Interceptor {

    private String mApiKey;

    public GiphyInterceptor(String apiKey) {
        this.mApiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        // Parse request
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        // Modify request
        HttpUrl url = originalHttpUrl
                .newBuilder()
                .addQueryParameter("api_key", this.mApiKey).build();
        // Build request
        Request.Builder requestBuilder = original.newBuilder().url(url);
        Request request = requestBuilder.build();

        return chain.proceed(request);
    }
}
