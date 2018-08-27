/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/22/18 8:55 PM
 */

package com.hendercine.android.gifavorites.view.mainview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hendercine.android.gifavorites.R;
import com.hendercine.android.gifavorites.data.GifClient;
import com.hendercine.android.gifavorites.model.GiphyImage;
import com.hendercine.android.gifavorites.model.GiphyObject;
import com.hendercine.android.gifavorites.view.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.andref.rx.network.RxNetwork;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Gifavorites created by artemis on 8/22/18.
 */
public class MainActivity extends BaseActivity implements MainGridRecyclerAdapter.ItemClickListener {

    private static final String POSITION_STATE_KEY = "scroll_position";
    private MainGridRecyclerAdapter mAdapter;
    private Subscription subscription;
    private CompositeSubscription mCompositeSubscription;
    private ConnectivityManager mConnectivityManager;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private RecoverySystem.ProgressListener mListener;
    private MenuItem mActionProgressItem;
    private ArrayList<GiphyObject> mGiphyObjects;
    private GiphyObject mGiphyObject;
    private ArrayList<GiphyImage> mGiphyImages;
    private GiphyImage mGiphyImage;
    private int mScrollPosition;
    private boolean mIsTablet;
    private SearchView mSearchView;
    private String mQuery;

    @Nullable
    @BindView(R.id.main_grid_recycler)
    RecyclerView mHandHeldGridCards;
    @Nullable
    @BindView(R.id.tablet_grid_recycler)
    RecyclerView mTabletGridCards;

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
//        // Set the spanCount for the view depending on device type.
//        if (mIsTablet) {
//            mRecyclerView = mTabletGridCards;
//            spanCount = 3;
//        } else {
        mRecyclerView = mHandHeldGridCards;
        spanCount = 1;
//        }

        mGiphyObjects = new ArrayList<>();
        mGiphyImages = new ArrayList<>();
        mGridLayoutManager = new GridLayoutManager(MainActivity.this, spanCount);

        assert mRecyclerView != null;
        int spacingInPixels = 50;

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mAdapter = new MainGridRecyclerAdapter(mGiphyObjects, mGiphyImages);
        mAdapter.setClickListener(MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacingInPixels, true));

        getGiphyData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id
                .app_bar_search).getActionView();
        if (mSearchView != null && searchManager != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            mSearchView.setIconifiedByDefault(false);
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent intent = getIntent();
                    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                        mQuery = intent.getStringExtra(SearchManager.QUERY);
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            };
            mSearchView.setOnQueryTextListener(queryTextListener);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Use CompositeSubscription to check if network is available.
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(
                RxNetwork.connectivityChanges(this, mConnectivityManager)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean connected) {
                                if (connected) {
                                    Timber.i(getString(R.string.network_is_connected));
                                } else {
                                    showToast(R.string.no_network_msg);
                                }
                            }
                        }));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCompositeSubscription.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    private void getGiphyData() {
        subscription = GifClient.getInstance()
                .getGiphyJson(mQuery)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<GiphyObject>>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("In onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("In onError");
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(ArrayList<GiphyObject> giphyObjects) {
                        Timber.d("in onNext");
                        mGiphyObjects = new ArrayList<>();
                        for (int i = 0; i < giphyObjects.size(); i++) {
                            mGiphyObject = new GiphyObject();

                            mGiphyObject.setGifObjectId(giphyObjects.get(i)
                                    .getGifObjectId());
                            mGiphyObject.setGifTitle(giphyObjects.get(i)
                                    .getGifTitle());
                            mGiphyObject.setGiphyImageObject(giphyObjects.get(i)
                                    .getGiphyImageObject());

                            mGiphyObjects.add(mGiphyObject);
                        }

                        ArrayList<GiphyImage> giphyImages = new ArrayList<>();
                        giphyImages.add(mGiphyObject.getGiphyImageObject());
                        mGiphyImages = new ArrayList<>();
                        for (int i = 0; i < giphyImages.size(); i++) {
                            mGiphyImage = mGiphyObject
                                    .getGiphyImageObject();

                            mGiphyImage.setImageUrl(giphyImages.get(i)
                                    .getImageUrl());
                            mGiphyImage.setImageHeight(giphyImages.get(i)
                                    .getImageHeight());

                            mGiphyImages.add(mGiphyImage);
                        }

                        mRecyclerView.setLayoutManager(mGridLayoutManager);
                        mRecyclerView.smoothScrollToPosition(mScrollPosition);
                        mAdapter.setList(mGiphyImages);
                    }
                });
    }

    @Override
    public void onItemCLick(View view, int position) {
        position = mRecyclerView.getChildLayoutPosition(view);
        GiphyObject giphyObject = mGiphyObjects.get(position);
//        Intent intent = new Intent(MainActivity.this, EnlargedGiphyActivity.class);
//        intent.putExtra("giphy", Parcels.wrap(giphyObject));
//        startActivity(intent);

    }

}
