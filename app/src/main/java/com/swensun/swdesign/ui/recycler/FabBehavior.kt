package com.swensun.swdesign.ui.recycler

import android.animation.Animator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import com.swensun.swdesign.base.dp2px



class FabBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior(context, attrs) {

    val INTERPOLATOR = FastOutSlowInInterpolator()

    private var isAnimate: Boolean = false

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes)
    }


    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        if (dyConsumed >= 0 && !isAnimate && child.visibility == View.VISIBLE) {
            hide(child)
        } else if (dyConsumed < 0 && !isAnimate && child.visibility == View.INVISIBLE) {
            show(child)
        }
    }

    private fun hide(view: View) {
        val animator = view.animate().translationY((view.height + dp2px(20f)).toFloat()).setInterpolator(INTERPOLATOR).setDuration(300)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                isAnimate = true
            }

            override fun onAnimationEnd(animator: Animator) {
                view.visibility = View.INVISIBLE
                isAnimate = false
            }

            override fun onAnimationCancel(animator: Animator) {
                show(view)
            }

            override fun onAnimationRepeat(animator: Animator) {}
        })
        animator.start()
    }

    private fun show(view: View) {
        val animator = view.animate().translationY(0f).setInterpolator(INTERPOLATOR).setDuration(300)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                view.visibility = View.VISIBLE
                isAnimate = true
            }

            override fun onAnimationEnd(animator: Animator) {
                isAnimate = false
            }

            override fun onAnimationCancel(animator: Animator) {
                hide(view)
            }

            override fun onAnimationRepeat(animator: Animator) {}
        })
        animator.start()
    }
}
