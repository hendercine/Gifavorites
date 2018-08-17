package com.hendercine.android.gifavorites

import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun init(state: Bundle?) {
        Glide.with(this)
                .load(R.drawable.ic_launcher_foreground)
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

    override fun getContentResource(): Int {
        return R.layout.activity_main
    }

}
