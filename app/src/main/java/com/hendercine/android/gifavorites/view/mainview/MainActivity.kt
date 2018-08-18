/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 8/17/18 9:20 PM
 */

package com.hendercine.android.gifavorites.view.mainview

import android.os.Bundle
import com.bumptech.glide.Glide
import com.hendercine.android.gifavorites.R
import com.hendercine.android.gifavorites.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide.with(this)
                .load(R.drawable.abc_ic_arrow_drop_right_black_24dp)
                .into(top_left_iv)
        Glide.with(this)
                .load(R.drawable.ic_audiotrack_black_24dp)
                .into(top_right_iv)
        Glide.with(this)
                .load(R.drawable.ic_casino_black_24dp)
                .into(bottom_left_iv)
        Glide.with(this)
                .load(R.drawable.ic_cloud_black_24dp)
                .into(bottom_right_iv)

    }

}
