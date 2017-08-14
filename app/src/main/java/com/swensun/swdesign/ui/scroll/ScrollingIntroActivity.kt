package com.swensun.swdesign.ui.scroll

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.swensun.swdesign.R
import kotlinx.android.synthetic.main.activity_scrolling_intro.*

class ScrollingIntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(R.layout.activity_scrolling_intro)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }



        fab.setOnClickListener {
            //share
            val share_intent = Intent()
            share_intent.type = "text/plain"
            share_intent.action = Intent.ACTION_SEND
            share_intent.putExtra(Intent.EXTRA_SUBJECT, "material demo title")//添加分享内容标题
            share_intent.putExtra(Intent.EXTRA_TEXT, "material demo content")//添加分享内容
            startActivity(Intent.createChooser(share_intent, "分享至"))
        }

        setView()
    }

    private fun setView() {
        Glide.with(this).load(R.drawable.icon_scroll_intro).into(scroll_intro_image_view)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling_intro, menu)
        return true
    }
}
