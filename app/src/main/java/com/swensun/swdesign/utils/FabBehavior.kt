package com.swensun.swdesign.utils

import android.animation.Animator
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View

/**
 * Created by swensun on 2017/8/12.
 */

class FabBehavior : FloatingActionButton.Behavior() {

    var isAnimate = false
    var viewY = 0f

    val INTERPOLATOR = FastOutSlowInInterpolator()

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?,
                                     directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {

        child?.let {
            if (child.visibility == View.VISIBLE && viewY == 0f) {
                viewY = coordinatorLayout!!.height - child.y
            }
        }
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?,
                                   target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed)

        if (dy >= 0 && !isAnimate && child?.visibility == View.VISIBLE) {
            hide(child)
        } else if (dy < 0 && !isAnimate && child?.visibility == View.INVISIBLE){
            show(child)
        }
    }

    private fun show(child: FloatingActionButton) {
        val animtor = child.animate().setDuration(300).setInterpolator(INTERPOLATOR).translationY(0f)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        isAnimate = false

                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        hide(child)
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        child.visibility = View.VISIBLE
                        isAnimate = true
                    }

                }).start()
    }

    private fun hide(child: FloatingActionButton) {
        val animtor = child.animate().setDuration(300).setInterpolator(INTERPOLATOR).translationY(viewY)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        isAnimate = false
                        child.visibility = View.INVISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        show(child)
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        isAnimate = true
                    }

                }).start()
    }
}
