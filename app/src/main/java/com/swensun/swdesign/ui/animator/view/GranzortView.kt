package com.swensun.swdesign.ui.animator.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.swensun.swdesign.R
import com.swensun.swdesign.base.getColor

/**
 * Created by macmini on 2017/8/24.
 */
class GranzortView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val innnerCircle = Path()
    private val outerCircle = Path()
    private val trangle1 = Path()
    private val trangle2 = Path()
    private val drawPath = Path()

    private val pathMeasure = PathMeasure()

    private var mViewHeight = 0f
    private var mViewWidth = 0f

    private val duration = 3000L

    private val valueAnimator = ValueAnimator()

    private val distance = 0f

    enum class State {
        CIRCLE_STATE,
        TRANGLE_STATE,
        FINISH_STATE
    }

    init {
        initPaint()
        initPath()
    }

    private fun initPaint() {
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin =  Paint.Join.BEVEL
        paint.setShadowLayer(15f, 0f, 0f, Color.WHITE)
    }

    private fun initPath() {
        val innerRect = RectF(-220f, -220f, 220f, 220f)
        val outerRect = RectF(-280f, -280f, 280f, 280f)

        innnerCircle.addArc(innerRect, 150f, -359.9f)
        outerCircle.addArc(outerRect, 60f, -359.9f)

        pathMeasure.setPath(innnerCircle, false)

        val pos = FloatArray(2) //坐标
        pathMeasure.getPosTan(0f, pos, null)
        trangle1.moveTo(pos[0], pos[1])

        pathMeasure.getPosTan((1f / 3f) * pathMeasure.length, pos, null)
        trangle1.lineTo(pos[0], pos[1])
        pathMeasure.getPosTan((2f / 3f) * pathMeasure.length, pos, null)
        trangle1.lineTo(pos[0], pos[1])
        trangle1.close()

        val matrix = Matrix()
        matrix.postRotate(-180f)
        trangle1.transform(matrix, trangle2)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewHeight = h.toFloat()
        mViewWidth = w.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return
        canvas.drawColor(getColor(R.color.colorPrimary))
        canvas.save()
        canvas.translate(mViewWidth / 2, mViewHeight / 2)
        canvas.drawPath(outerCircle, paint)
        canvas.drawPath(innnerCircle, paint)
        canvas.drawPath(trangle1, paint)
        canvas.drawPath(trangle2, paint)
        canvas.restore()
    }

}