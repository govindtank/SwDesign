package com.swensun.swdesign.ui.viewpager

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.swensun.swdesign.R
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter =  SectionsPagerAdapter(supportFragmentManager)
    val fragmentList = arrayListOf(CardFragment(), DialogFragment(), SystemViewFragment())
    val titleList = arrayListOf("卡片", "对话框", "系统控件")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        view_pager.adapter = mSectionsPagerAdapter
        tab_layout_view_pager.setupWithViewPager(view_pager)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }
        override fun getCount(): Int {
            return 3
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}
