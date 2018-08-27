/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/22/18 8:55 PM
 */

package com.hendercine.android.gifavorites.view.mainview;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.hendercine.android.gifavorites.R;
import com.hendercine.android.gifavorites.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Gifavorites created by artemis on 8/22/18.
 */
public class MainActivity extends BaseActivity {

    private static final String POSITION_STATE_KEY = "scroll_position";
    private MainGridRecyclerAdapter mAdapter;
    private Subscription subscription;
    private CompositeSubscription mCompositeSubscription;
    private ConnectivityManager mConnectivityManager;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private RecoverySystem.ProgressListener mListener;
    private MenuItem mActionProgressItem;
    private int mScrollPosition;
    private boolean mIsTablet;

    @Nullable
    @BindView(R.id.main_grid_recycler)
    RecyclerView mMainGridRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.tag("LifeCycles");
        Timber.d("In onCreate");

        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mIsTablet = getResources().getBoolean(R.bool.isTablet);

        int spanCount;
        // Set the spanCount for the view depending on device type.
        if (mIsTablet) {
            mRecyclerView = tabletGridCards;
            spanCount = 3;
        } else {
            mRecyclerView = handHeldGridCards;
            spanCount = 1;
        }
    }
}
