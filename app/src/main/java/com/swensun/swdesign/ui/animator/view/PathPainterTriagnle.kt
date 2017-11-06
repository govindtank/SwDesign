package com.swensun.swdesign.ui.animator.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.swensun.swutils.util.sp2px


/**
 * Created by macmini on 2017/8/23.
 */
class PathPainterTriagnle @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), View.OnClickListener{


    var mAnimatorValue = 0f
    var mLength =  0f
    lateinit var mEffect: PathEffect

    val mPath = Path()
    val mPathMeasure = PathMeasure()
    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val mValueAnimator = ValueAnimator.ofFloat(0f, 1f)!!

    init {

//        paint
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 5f
        mPaint.textSize = sp2px(50f)

//        path

        mPath.reset()
        mPath.moveTo(300f, 0f)
        mPath.lineTo(300f, 200f)
        mPath.lineTo(500f, 100f)
        mPath.close()

        mPathMeasure.setPath(mPath, false)
        mLength = mPathMeasure.length

        mValueAnimator.addUpdateListener {
            mAnimatorValue = it.animatedValue as Float
            mEffect = DashPathEffect(floatArrayOf(mLength, mLength),  ( 1 -  mAnimatorValue)* mLength)
            mPaint.pathEffect = mEffect
            invalidate()
        }
        mValueAnimator.duration = 2000
        mValueAnimator.interpolator = AccelerateDecelerateInterpolator()
        mValueAnimator.start()
        setOnClickListener(this)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(mPath, mPaint)
    }

    override fun onClick(v: View?) {
        mValueAnimator.start()
    }
}