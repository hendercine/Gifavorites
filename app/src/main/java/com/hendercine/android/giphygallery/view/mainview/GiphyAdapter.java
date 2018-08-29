/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/23/18 9:43 PM
 */

package com.hendercine.android.giphygallery.view.mainview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hendercine.android.giphygallery.R;
import com.hendercine.android.giphygallery.model.Gif;
import com.hendercine.android.giphygallery.model.GiphyObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Gifavorites created by artemis on 8/23/18.
 */
public class GiphyAdapter extends RecyclerView.Adapter<GiphyAdapter.GiphyViewHolder>{

    private LayoutInflater mInflater;
    private GiphyObject mResponse;
    private Context mContext;

    GiphyAdapter(@NonNull Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GiphyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = mInflater.inflate(R.layout.list_item_main, parent, false);
        return new GiphyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiphyViewHolder holder, int position) {
        if (mResponse != null) {
            final Gif gif = mResponse.getImagesData(position);

            Glide.with(mContext)
                    .load(gif.getUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .placeholder(R.drawable.ic_placeholder)
                            .error(R.drawable.ic_error)
                            .centerCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mResponse != null) {
            return mResponse.getGifs().size();
        }
        return 0;
    }

    void setResponse(GiphyObject response) {
        this.mResponse = response;
    }

    void appendResponse(GiphyObject response) {
        this.mResponse.appendImagesData(response.getGifs());
    }

    class GiphyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_grid_item_img)
        AppCompatImageView mImageView;

        GiphyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }
    }
}
