package com.swensun.swdesign.ui.animator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewAnimationUtils
import com.swensun.swdesign.R
import kotlinx.android.synthetic.main.content_circular_reveal.*

class CircularRevealActivity : AppCompatActivity() {

    var isImage1Show = true
    var isImage2Show = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_reveal)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        setView()
    }

    private fun setView() {
        btn_style1.setOnClickListener {
            //相对位置
            val cx = image_style1.width / 2
            val cy = image_style1.height / 2
            val radius = Math.max(image_style1.width, image_style1.height)

            if (isImage1Show) {
                val anim = ViewAnimationUtils.createCircularReveal(image_style1, cx, cy, radius.toFloat(), 0f).apply {
                    duration = 300
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            image_style1.visibility = View.INVISIBLE
                            btn_style1.text = "show"
                            isImage1Show = false
                        }
                    })
                }
                anim.start()
            } else {
                val anim = ViewAnimationUtils.createCircularReveal(image_style1, cx, cy, 0f, radius.toFloat()).apply {
                    duration = 300
                }
                image_style1.visibility = View.VISIBLE
                isImage1Show = true
                btn_style1.text = "hide"
                anim.start()
            }
        }
        btn_style2.setOnClickListener {
            //相对位置
            val cx = image_style2.width
            val cy = 0
            val radius = Math.hypot(image_style2.width.toDouble(), image_style2.height.toDouble())

            if (isImage2Show) {
                val anim = ViewAnimationUtils.createCircularReveal(image_style2, cx, cy, radius.toFloat(), 0f).apply {
                    duration = 300
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            image_style2.visibility = View.INVISIBLE
                            btn_style2.text = "show"
                            isImage2Show = false
                        }
                    })
                }
                anim.start()
            } else {
                val anim = ViewAnimationUtils.createCircularReveal(image_style2, cx, cy, 0f, radius.toFloat()).apply {
                    duration = 300
                }
                image_style2.visibility = View.VISIBLE
                isImage2Show = true
                btn_style2.text = "hide"
                anim.start()
            }
        }
    }

}
