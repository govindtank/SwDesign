package com.swensun.swdesign.ui.guide


import android.animation.ArgbEvaluator
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.swensun.swdesign.R
import com.swensun.swdesign.base.getColor
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {



    private var mOnboardingPagerAdapter: OnboardingPagerAdapter? = null
    private val mOnboardingFragmentList = arrayListOf(OnboardingFragment(),
            OnboardingFragment(),
            OnboardingFragment()
    )
    private val mViewPagerBgColor = arrayListOf(Color.parseColor("#03A9F4"),
            Color.parseColor("#00BCD4"),
            Color.parseColor("#009688"))
    var mCurrentViewIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_onboarding)
        initViewPager()
    }

    private fun initViewPager() {
        mOnboardingPagerAdapter = OnboardingPagerAdapter(supportFragmentManager)

        guide_view_pager.adapter = mOnboardingPagerAdapter
        guide_view_pager.setBackgroundColor(getColor("#03A9F4"))
        guide_view_pager.currentItem = 0
        bottom_view.updateView(0)
        bottom_view.setListener(this, guide_view_pager)
        guide_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            //控制状态变化
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val colorUpdate = ArgbEvaluator().evaluate(positionOffset, mViewPagerBgColor[position],
                        mViewPagerBgColor[if (position == 2) position else position + 1]) as Int
                guide_view_pager.setBackgroundColor(colorUpdate)

            }

            override fun onPageSelected(position: Int) {
                mCurrentViewIndex = position
                bottom_view.updateView(position)
            }

        })
    }

    inner class OnboardingPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return mOnboardingFragmentList[position]
        }

        override fun getCount(): Int {
            return 3
        }
    }
}
