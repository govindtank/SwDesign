package com.swensun.swdesign.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.swensun.swdesign.R
import com.swensun.swdesign.utils.recyclePictureList
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        val drawableIndex = intent.getIntExtra("listID", 0)
        setView(drawableIndex)
    }

    private fun setView(drawableIndex: Int) {
        toolbar_layout.title = "图片：$drawableIndex"
        Glide.with(this).load(recyclePictureList[drawableIndex]).into(image_scrolling_top)
    }
}
