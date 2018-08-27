/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/22/18 8:53 PM
 */

package com.hendercine.android.gifavorites.data;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Gifavorites created by artemis on 8/22/18.
 */
public class GiphyService extends IntentService {

    GifClient mGifClient;
    GifApiService mGifApiService;

    public GiphyService() {
        super(GiphyService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
