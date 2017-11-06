package com.swensun.swdesign.ui.animator.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.swensun.swutils.util.getColor


/**
 * Created by macmini on 2017/8/23.
 */
class PathPainterCircle @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), View.OnClickListener {


    var mAnimatorValue = 0f
    var mLength =  0f
    var mDst = Path()
    val mPathMeasure = PathMeasure()
    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val mValueAnimator = ValueAnimator.ofFloat(0f, 1f)!!

    init {

        setBackgroundColor(getColor("#00ff00"))

        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f

        val mPath = Path()
        //圆形
        mPath.addCircle(100f ,100f, 100f, Path.Direction.CW)
//        mPath.lineTo(500f, 500f)



        mPathMeasure.setPath(mPath, true)

        mLength = mPathMeasure.length



        mValueAnimator.addUpdateListener {
            mAnimatorValue = it.animatedValue as Float
            invalidate()
        }
        mValueAnimator.duration = 3000
        mValueAnimator.repeatCount = ValueAnimator.INFINITE
        mValueAnimator.start()


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mDst.reset()
//        mDst.lineTo(0f, 0f)
        val stop = mLength * mAnimatorValue
//        val start = stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength)
        mPathMeasure.getSegment(0f, stop, mDst, true)
        canvas?.drawPath(mDst, mPaint)
    }

    override fun onClick(v: View?) {

    }
}