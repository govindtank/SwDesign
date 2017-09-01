package com.swensun.swdesign.ui

import android.app.ActivityOptions
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.swensun.swdesign.R
import com.swensun.swdesign.ui.animator.AnimatorActivity
import com.swensun.swdesign.ui.animator.PathMeasureActivity
import com.swensun.swdesign.ui.bottom.BottomNavigationActivity
import com.swensun.swdesign.ui.guide.OnboardingActivity
import com.swensun.swdesign.ui.recycler.RecyclerViewActivity
import com.swensun.swdesign.ui.scroll.ScrollingIntroActivity
import com.swensun.swdesign.ui.viewpager.ViewPagerActivity
import com.swensun.swdesign.viewmodel.MainViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, LifecycleRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.getHeaderView(0).nav_header_layout.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        setView()
//        if (viewModel.shouldShowGuide()) {
//
//            viewModel.markGuide()
//        }
        startActivity(Intent(this, OnboardingActivity::class.java))

    }

    private fun setView() {

        app_info_layout.setOnClickListener {
            startActivity(Intent(this, PathMeasureActivity::class.java))
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    --------- menu ---------------------------
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//
//        if (id == R.id.action_settings) {
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        drawer_layout.closeDrawer(GravityCompat.START, true)
        Observable.timer(150, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    goToAct(item)
                }
//        goToAct(item)
        return true
    }

    private fun  goToAct(item: MenuItem) {
        when(item.itemId) {
            R.id.nav_recycler_view -> {
                startActivity(Intent(this, RecyclerViewActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.nav_scroll -> {
                startActivity(Intent(this, ScrollingIntroActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.nav_bottom_navigation -> {
                startActivity(Intent(this, BottomNavigationActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.nav_view_pager -> {
                startActivity(Intent(this, ViewPagerActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.nav_animator -> {
                startActivity(Intent(this, AnimatorActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }
    }
}
