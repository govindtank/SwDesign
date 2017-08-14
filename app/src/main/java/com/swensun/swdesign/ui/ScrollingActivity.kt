package com.swensun.swdesign.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.swensun.swdesign.R
import com.swensun.swdesign.utils.recyclePictureList
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//        val explore = TransitionInflater.from(this).inflateTransition(R.transition.explore)
//        window.exitTransition = explore
//        window.enterTransition = explore
//        window.reenterTransition = explore
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        val drawableIndex = intent.getIntExtra("listID", 0)
        setView(drawableIndex)
    }
    //退出时的转场动画
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setView(drawableIndex: Int) {
        toolbar_layout.title = "图片：$drawableIndex"
        content_text.text = "这是第 $drawableIndex 张图片"
        Glide.with(this).load(recyclePictureList[drawableIndex]).into(image_scrolling_top)
    }
}
