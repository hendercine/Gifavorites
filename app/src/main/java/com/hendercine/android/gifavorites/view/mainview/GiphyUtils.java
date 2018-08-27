/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/27/18 12:15 PM
 */

package com.hendercine.android.gifavorites.view.mainview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Gifavorites created by artemis on 8/27/18.
 */
public class GiphyUtils extends AppCompatActivity {

    public final static String GIPHY_URL = "giphy_url";
    public final static String API_KEY = "api_key";
    public final static int REQUEST_CODE = 1001;
    public final static int PAGE_COUNT = 25;

    public Listener mListener;

    public interface Listener {

        void onGiphySeleted(String url);
    }

    public void start(Activity activity, Listener listener, String apiKey) {
        this.mListener = listener;
        activity.startActivityForResult(buildIntent(activity, apiKey), REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GiphyUtils.REQUEST_CODE:
                    if (mListener != null) {
                        mListener.onGiphySeleted(data.getStringExtra
                                (GiphyUtils.GIPHY_URL));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private Intent buildIntent(Context context, String apiKey) {
        Intent intent =  new Intent(context, MainActivity.class);
        intent.putExtra(API_KEY, apiKey);
        return intent;
    }

}
