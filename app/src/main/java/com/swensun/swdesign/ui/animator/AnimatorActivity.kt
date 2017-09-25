package com.swensun.swdesign.ui.animator

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.swensun.swdesign.R
import kotlinx.android.synthetic.main.activity_animator.*
import kotlinx.android.synthetic.main.content_animator.*

class AnimatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        btn_dialog_1.setOnClickListener {
            Snackbar.make(it, "点击时有波纹效果", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        btn_dialog_2.setOnClickListener {
            var options = ActivityOptions.makeScaleUpAnimation(it, (it.x.toInt() + it.width) / 2, (it.y.toInt() + it.height) / 2, 0, 0)
            startActivity(Intent(this, CircularRevealActivity::class.java), options.toBundle())
        }
        btn_dialog_3.setOnClickListener {
            val intent = Intent(this, TransitionActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
        btn_dialog_4.setOnClickListener {

        }
        btn_dialog_6.setOnClickListener {
            val intent = Intent(this, PathMeasureActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

}
