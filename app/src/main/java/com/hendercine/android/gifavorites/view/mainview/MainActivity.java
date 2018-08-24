/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/22/18 8:55 PM
 */

package com.hendercine.android.gifavorites.view.mainview;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hendercine.android.gifavorites.R;
import com.hendercine.android.gifavorites.view.base.BaseActivity;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Gifavorites created by artemis on 8/22/18.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.tag("LifeCycles");
        Timber.d("In onCreate");


    }
}
