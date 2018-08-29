/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/22/18 8:55 PM
 */

package com.hendercine.android.gifavorites.view.mainview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hendercine.android.gifavorites.BuildConfig;
import com.hendercine.android.gifavorites.R;
import com.hendercine.android.gifavorites.data.ApiResultReceiver;
import com.hendercine.android.gifavorites.data.GifClient;
import com.hendercine.android.gifavorites.model.Gif;
import com.hendercine.android.gifavorites.model.GiphyObject;
import com.hendercine.android.gifavorites.view.base.BaseActivity;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

import static com.hendercine.android.gifavorites.data.ApiResultReceiver.ResultListener;

/**
 * Gifavorites created by artemis on 8/22/18.
 */
public class MainActivity extends BaseActivity implements ResultListener {

    public final static String GIPHY_URL = "giphy_url";
    public final static String API_KEY = BuildConfig.ApiKey;
    public final static int REQUEST_CODE = 1001;
    public final static int PAGE_COUNT = 25;
    private static final String REC = "rec";

    public ResultListener mResultListener;

    @Nullable
    @BindView(R.id.main_grid_recycler)
    RecyclerView mHandHeldGridCards;

    @Nullable
    @BindView(R.id.tablet_grid_recycler)
    RecyclerView mTabletGridCards;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;

    @BindView(R.id.search_field_view)
    AppCompatEditText mEditText;

    RecyclerView mRecyclerView;

    GiphyAdapter mAdapter;
    GifClient mGifClient;

    ExecutorService mCacheExecutor;
    Subscription mTrendingSubscription;
    Subscription mSearchSubscription;
    Subscription mEditTextSubscription;
    CompositeSubscription mCompositeSubscription;
    ConnectivityManager mConnectivityManager;

    boolean loadMore;
    int offset;

    Observer<GiphyObject> mObserver = new Observer<GiphyObject>() {
        @Override
        public void onCompleted() {
            Timber.d("In onCompleted()");
        }

        @Override
        public void onError(Throwable e) {
            Timber.d("In onError");
            e.printStackTrace();
        }

        @Override
        public void onNext(final GiphyObject giphyObject) {
            Timber.d("In onNext");
            // Caching Strategy
            mCacheExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    cache(giphyObject);
                }
            });
            // Adapter Mods
            if (loadMore) {
                mAdapter.appendResponse(giphyObject);
                offset += PAGE_COUNT;
            } else {
                mAdapter.setResponse(giphyObject);
                offset = 0;
            }

            mAdapter.notifyDataSetChanged();
            loadMore = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.tag("LifeCycles");
        Timber.d("In onCreate");

        mToolbar.setTitle(R.string.app_name);

        mCacheExecutor = Executors.newFixedThreadPool(1);
        mGifClient = new GifClient();
        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int spanCount;

        if (isTablet) {
            mRecyclerView = mTabletGridCards;
            spanCount = 3;
        } else {
            mRecyclerView = mHandHeldGridCards;
            spanCount = 2;
        }

        mAdapter = new GiphyAdapter(getApplicationContext());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager
                (getApplicationContext(), spanCount));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Load more Items
                if (loadMore) {
                    return;
                }
                GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView
                        .getLayoutManager();
                int visibleItems = layoutManager.getChildCount();
                int totalItems = layoutManager.getItemCount();
                int pastItems = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItems + pastItems) >= totalItems) {
                    loadMore = true;
                    loadMore();
                }
            }
        });
        int spacingInPixels = 16;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration
                (spanCount, spacingInPixels, true));
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard();
            }
        });

        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                mEditText.requestFocus();
                InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (mgr != null) {
                    mgr.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
                }
            }
        });

        setUpEditText();
        dismissKeyboard();
        getTrending(offset);
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
        dismissKeyboard();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mCompositeSubscription.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSearchSubscription != null && !mSearchSubscription.isUnsubscribed()) {
            mSearchSubscription.unsubscribe();
        }
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        start(MainActivity.this, mResultListener, API_KEY);
    }

    public void start(Activity activity, ResultListener resultListener, String apiKey) {
        this.mResultListener = resultListener;
        activity.startActivityForResult(buildIntent(activity, apiKey), REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    if (mResultListener != null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            mResultListener.onReceiveResult(resultCode, bundle.getBundle(GIPHY_URL));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private Intent buildIntent(Context context, String apiKey) {
        Intent intent =  new Intent(context, MainActivity.class);
        ApiResultReceiver receiver = new ApiResultReceiver(new Handler());
        receiver.setResultListener(this);
        intent.putExtra(REC, receiver);
        intent.putExtra(API_KEY, apiKey);
        return intent;
    }

    private void setUpEditText() {
        if (mEditTextSubscription != null) {
            mEditTextSubscription.unsubscribe();
        }
        mEditTextSubscription = RxTextView.textChanges(mEditText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {

                        String query = charSequence.toString();

                        if (query.equals("")) {
                            mRecyclerView.smoothScrollToPosition(0);
                            getTrending(0);
                        } else {
                            mRecyclerView.smoothScrollToPosition(0);
                            getSearch(query, 0);
                        }
                    }
                });
    }

    private Context getContext() {
        return getApplicationContext();
    }

    private void dismissKeyboard() {
        mEditText.setInputType(InputType.TYPE_NULL);
    }

    private void loadMore() {
        loadMore = true;
        if (mEditText.getText().length() > 0) {
            getSearch(mEditText.getText().toString(), offset);
        } else {
            getTrending(offset);
        }
    }

    protected void getTrending(int offset) {
        if (mTrendingSubscription != null) {
            mTrendingSubscription.unsubscribe();
        }
        mTrendingSubscription = mGifClient.getTrendingGifs(offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(mObserver);

    }

    protected void getSearch(String query, int offset) {
        if (mSearchSubscription != null) {
            mSearchSubscription.unsubscribe();
        }
        mSearchSubscription = mGifClient.getSearchQuery(query, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(mObserver);

    }

    protected void cache(GiphyObject response) {
        for (Gif gif : response.getGifs()) {
            Glide.with(getContext())
                    .load(gif.images.fixed_width.url)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop())
                    .transition(DrawableTransitionOptions.withCrossFade());
        }
    }

}
