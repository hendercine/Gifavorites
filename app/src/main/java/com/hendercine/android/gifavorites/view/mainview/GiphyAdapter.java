/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/23/18 9:43 PM
 */

package com.hendercine.android.gifavorites.view.mainview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hendercine.android.gifavorites.R;
import com.hendercine.android.gifavorites.model.Gif;
import com.hendercine.android.gifavorites.model.GiphyObject;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Gifavorites created by artemis on 8/23/18.
 */
public class GiphyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator shrinkY = ObjectAnimator.ofFloat(view,
                            "y", 50f, 100f
                    ).setDuration(100);
                    Animator shrinkX = ObjectAnimator.ofFloat(view,
                            "x", 1.0f, 0.9f
                    ).setDuration(100);
                    Animator growY = ObjectAnimator.ofFloat(view,
                            "scaleY", 0.9f, 1.0f
                    ).setDuration(100);
                    Animator growX = ObjectAnimator.ofFloat(view,
                            "scaleX", 0.9f, 1.0f
                    ).setDuration(100);

                    AnimatorSet set = new AnimatorSet();
                    set.play(shrinkX);
                    set.play(shrinkY);
                    set.play(growX).after(shrinkX);
                    set.play(growY).after(shrinkY);
                    set.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (mListener != null) {
                                mListener.onSelected(gif.getUrl());
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    set.start();
                }
            });
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

    //    @NonNull
//    @Override
//    public MainGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
//                .list_item_main, parent, false);
//        return new MainGridViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MainGridViewHolder holder, int position) {
//
//            mContext = holder.mCardView.getContext();
//    loadGif(gif, holder.mImageView);
//            mContext.
//
//        }
////
////    @Override
////    public int getItemCount() {
////        return null;
////    }
////
////    public long getItemId(int position) {
////        return position;
////    }
//
////    private Gif getItem(int position) {
////        if (position < 0 || position >= mGifs.size()) {
////            return null;
////        } else {
////            return mGifs.get(position);
////        }
////    }
//
////    public void setList(ArrayList<Gif> giphyObjects) {
////        if (mList == null) {
////            return;
////        }
////        mGifs.clear();
////        mGifs.addAll(giphyObjects);
////        notifyDataSetChanged();
//    }
//
//    private void setListener(Listener listener) {
//        this.mListener = listener;
//    }
//
//
//    class MainGridViewHolder extends ViewHolder {
//
//        @BindView(R.id.main_grid_card_view)
//        CardView mCardView;
//        @BindView(R.id.main_grid_item_img)
//        ImageView mImageView;
//
//        @BindView(R.id.main_grid_item_title)
//        TextView mTitleView;
//        public MainGridViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//
//    }
//    //
//    //        void onItemCLick(View view, int position);
//    //
//    //    public interface ItemClickListener {
//    //
//    //    }
//    //        this.mClickListener = itemClickListener;
////    public void setClickListener(ItemClickListener itemClickListener) {
//
////    }
//
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }

}
