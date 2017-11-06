package com.swensun.swdesign.ui.guide

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.swensun.swdesign.R
import com.swensun.swdesign.ui.MainActivity
import com.swensun.swutils.util.getColor
import kotlinx.android.synthetic.main.activity_onboarding.view.*
import kotlinx.android.synthetic.main.view_onboarding_bottom.view.*


class OnboardingBottomView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_onboarding_bottom, this)
    }

    fun updateView(position: Int) {
        when(position) {
            0 -> {
                left_view.visibility = View.INVISIBLE
                right_view.visibility = View.VISIBLE
                done_text.visibility = View.INVISIBLE

                image_button_one.setColorFilter(getColor("#ffffff"))
                image_button_two.colorFilter = null
                image_button_three.colorFilter = null
            }
            1 -> {
                left_view.visibility = View.VISIBLE
                right_view.visibility = View.VISIBLE
                done_text.visibility = View.INVISIBLE
                image_button_one.colorFilter = null
                image_button_two.setColorFilter(getColor("#ffffff"))
                image_button_three.colorFilter = null
            }
            2 -> {
                left_view.visibility = View.INVISIBLE
                right_view.visibility = View.INVISIBLE
                done_text.visibility = View.VISIBLE
                image_button_one.colorFilter = null
                image_button_two.colorFilter = null
                image_button_three.setColorFilter(getColor("#ffffff"))
            }
        }
    }

    fun setListener(context: Context, guide_view_pager: ViewPager) {
        left_view.setOnClickListener {
            if (guide_view_pager.currentItem > 0) {
                guide_view_pager.setCurrentItem(guide_view_pager.currentItem - 1, true)
            }
        }
        bottom_view.right_view.setOnClickListener {
            when(guide_view_pager.currentItem) {
                0 -> {
                    guide_view_pager.setCurrentItem(1, true)
                }
                1 -> {
                    guide_view_pager.setCurrentItem(2, true)
                }
            }
        }
        done_text.setOnClickListener {
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as Activity).finish()
        }
    }


}