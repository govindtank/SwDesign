package com.swensun.swdesign.ui.recycler

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.swensun.swdesign.R
import com.swensun.swdesign.database.entity.DoubanMovieEntity
import com.swensun.swdesign.viewmodel.ScrollingViewModel
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity(), LifecycleRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    val viewModel: ScrollingViewModel by lazy {
        ViewModelProviders.of(this).get(ScrollingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//        val explore = TransitionInflater.from(this).inflateTransition(R.transition.explore)
//        window.exitTransition = explore
//        window.enterTransition = explore
//        window.reenterTransition = explore
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        setView(viewModel.getDoubanMovieEntity())
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

    private fun setView(doubanMovieEntity: DoubanMovieEntity) {
        doubanMovieEntity.title.let {
            supportActionBar?.title = it
        }
    }
}
