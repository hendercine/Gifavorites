/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/27/18 11:19 AM
 */

package com.hendercine.android.gifavorites.view.mainview;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hendercine.android.gifavorites.R;
import com.hendercine.android.gifavorites.model.Gif;

/**
 * Gifavorites created by artemis on 8/27/18.
 */
public final class GiphyView extends RecyclerView.ViewHolder {

    Context mContext;
    AppCompatImageView mImageView;

    public GiphyView(View itemView) {
        super(itemView);

        mContext = itemView.getContext();
        mImageView = itemView.findViewById(R.id.main_grid_item_img);
    }

    public void loadGif(Gif gif) {

        Glide.with(mContext)
                .load(gif.getUrl())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.ic_audiotrack_black_24dp)
                        .error(R.drawable.ic_casino_black_24dp)
                        .centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mImageView);
    }
}
