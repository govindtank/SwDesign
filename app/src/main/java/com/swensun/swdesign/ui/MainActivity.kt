package com.swensun.swdesign.ui

import android.app.ActivityOptions
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.swensun.swdesign.R
import com.swensun.swdesign.ui.develop.DevelopHelpActivity
import com.swensun.swdesign.ui.develop.DeviceInfoActivity
import com.swensun.swdesign.ui.guide.OnboardingActivity
import com.swensun.swdesign.ui.ins.InstagramProActivity
import com.swensun.swdesign.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        nav_view.getHeaderView(0).nav_header_layout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            drawer_layout.closeDrawer(GravityCompat.START, false)
        }
        setView()
        if (viewModel.shouldShowGuide()) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            viewModel.markGuide()
        }
    }

    private fun setView() {

        app_info_layout.setOnClickListener {


            //            val intent = Intent(this, PathMeasureActivity::class.java)
//            val options = ActivityOptions.makeSceneTransitionAnimation(this, it, "sharedView")
//            startActivity(intent, options.toBundle())

//            developSettingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//          com.android.settings/.Settings$DevelopmentSettingsActivity
//            Observable.interval(1, TimeUnit.SECONDS)
//                    .take(10)
//                    .doOnComplete {
//                        showSnackBar(this, "complete")
//                        startActivity(Intent(this, MainActivity::class.java))
//                    }
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        showToast(it.toString(), 500)
//                    }
            var uri = Uri.parse("ks://profile/user/515481447?from=singlemessage")
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
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
        goToAct(item)
        drawer_layout.closeDrawer(GravityCompat.START, false)
        return true
    }

    private fun goToAct(item: MenuItem) {
        val cls = when (item.itemId) {
//            R.id.nav_recycler_view -> RecyclerViewActivity::class.java
//            R.id.nav_scroll -> ScrollingIntroActivity::class.java
//            R.id.nav_bottom_navigation -> BottomNavigationActivity::class.java
//            R.id.nav_view_pager -> ViewPagerActivity::class.java
//            R.id.nav_animator -> AnimatorActivity::class.java
//            R.id.nav_touch_event -> TouchEventActivity::class.java
            R.id.nav_ins_pro -> InstagramProActivity::class.java
            R.id.nav_ins_develop_help -> DevelopHelpActivity::class.java
            R.id.nav_ins_device_info -> DeviceInfoActivity::class.java
            else -> InstagramProActivity::class.java
        }
        startActivity(Intent(this, cls), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}
