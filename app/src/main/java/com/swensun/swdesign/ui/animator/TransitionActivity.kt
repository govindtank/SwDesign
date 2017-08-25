package com.swensun.swdesign.ui.animator

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.transition.TransitionInflater
import android.view.Window
import com.swensun.swdesign.R
import kotlinx.android.synthetic.main.activity_transition.*

class TransitionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        val tran = TransitionInflater.from(this).inflateTransition(R.transition.explore)
//      val tran = TransitionInflater.from(this).inflateTransition(R.transition.slide)
        window.exitTransition = tran
        window.enterTransition = tran
        window.reenterTransition = tran
        setContentView(R.layout.activity_transition)

        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }

}
