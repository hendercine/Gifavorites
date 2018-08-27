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
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hendercine.android.gifavorites.R;
import com.hendercine.android.gifavorites.model.GiphyObject;
import com.hendercine.android.gifavorites.model.GiphyImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Gifavorites created by artemis on 8/23/18.
 */
public class MainGridRecyclerAdapter extends RecyclerView
                                                     .Adapter<MainGridRecyclerAdapter.MainGridViewHolder> {

    private ArrayList<GiphyObject> mList;
    private ArrayList<GiphyImage> mGiphyImages;
    private ItemClickListener mClickListener;

    private int focusedItem = RecyclerView.NO_POSITION;
    private GiphyObject giphyObject;
    private GiphyImage giphyImage;

    public MainGridRecyclerAdapter(ArrayList<GiphyObject> giphyObjects, ArrayList<GiphyImage> giphyImages) {
        this.mList = giphyObjects;
        this.mGiphyImages = giphyImages;
    }

    @NonNull
    @Override
    public MainGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .list_item_main, parent, false);
        return new MainGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainGridViewHolder holder, int position) {
        Context context = holder.mCardView.getContext();
        giphyObject = mList.get(position);
        giphyImage = getItem(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.mTitleView.setText(giphyObject.getGifTitle());
        if (giphyImage != null && !giphyImage.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(giphyImage.getImageUrl())
                    .into(holder.mImageView);
        } else {
            holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public long getItemId(int position) {
        return position;
    }

    private GiphyImage getItem(int position) {
        if (position < 0 || position >= mGiphyImages.size()) {
            return null;
        } else {
            return mGiphyImages.get(position);
        }
    }

    public void setList(ArrayList<GiphyImage> giphyObjects) {
        if (mList == null) {
            return;
        }
        mGiphyImages.clear();
        mGiphyImages.addAll(giphyObjects);
        notifyDataSetChanged();
    }

    class MainGridViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.main_grid_card_view)
        CardView mCardView;

        @BindView(R.id.main_grid_item_img)
        ImageView mImageView;
        @BindView(R.id.main_grid_item_title)
        TextView mTitleView;
        public MainGridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemCLick(v, getAdapterPosition());
            }

        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {

        void onItemCLick(View view, int position);

    }
}
