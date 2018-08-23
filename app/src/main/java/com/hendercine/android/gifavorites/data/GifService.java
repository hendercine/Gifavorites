/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/18/18 7:41 PM
 */

package com.hendercine.android.gifavorites.data;

import com.hendercine.android.gifavorites.model.GifObject;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
/**
 * Gifavorites created by artemis on 8/18/18.
 */
interface GifService {

    @GET("giphy-json")
    Observable<ArrayList<GifObject>> searchGifs(@Query("q") String query);

}
