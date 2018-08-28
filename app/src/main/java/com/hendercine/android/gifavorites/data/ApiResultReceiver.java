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

/**
 * Gifavorites created by artemis on 8/28/18.
 */
public class ApiResultReceiver extends ResultReceiver {

    private ResultListener mResultListener;

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

    public void setResultListener(ResultListener resultListener) {
        mResultListener = resultListener;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mResultListener != null) {
            mResultListener.onReceiveResult(resultCode, resultData);
        }
    }

    public interface ResultListener {

        void onReceiveResult(int resultCode, Bundle resultData);

    }

}
