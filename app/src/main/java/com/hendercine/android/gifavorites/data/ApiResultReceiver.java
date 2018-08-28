/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/28/18 2:28 PM
 */

package com.hendercine.android.gifavorites.data;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.hendercine.android.gifavorites.BuildConfig;

import static android.app.Activity.RESULT_OK;

/**
 * Gifavorites created by artemis on 8/28/18.
 */
public class ApiResultReceiver extends ResultReceiver {

    public final static String GIPHY_URL = "giphy_url";
    public final static String API_KEY = BuildConfig.ApiKey;
    public final static int REQUEST_CODE = 1001;
    public final static int PAGE_COUNT = 25;
    private Listener mListener;

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler to be received from MainActivity thread subscriptions
     */
    public ApiResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == RESULT_OK) {
                    if (mListener != null) {
                        mListener.onReceiveResult(resultCode, resultData);
                    }
            }
        }

    public interface Listener {

        void onReceiveResult(int resultCode, Bundle resultData);

    }

}
