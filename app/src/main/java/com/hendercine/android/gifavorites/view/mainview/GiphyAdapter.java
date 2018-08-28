/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/23/18 9:43 PM
 */

package com.hendercine.android.gifavorites.view.mainview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hendercine.android.gifavorites.R;
import com.hendercine.android.gifavorites.model.Gif;
import com.hendercine.android.gifavorites.model.GiphyObject;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Gifavorites created by artemis on 8/23/18.
 */
public class GiphyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    LayoutInflater mInflater;
    Listener mListener;
    GiphyObject mResponse;
    private Context mContext;

    interface Listener {
        void onSelected(String url);
    }

    GiphyAdapter(@NonNull Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        return new GiphyView(mInflater.inflate(R.layout.list_item_main,
                parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mResponse != null) {
            final Gif gif = mResponse.getImagesData(position);

            final GiphyView view = (GiphyView) holder;
            view.loadGif(gif);
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

    void setListener(Listener listener) {
        this.mListener = listener;
    }

}
