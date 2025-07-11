package com.wkq.tools.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/2 9:23
 *
 *@Desc:  渐变色进度条
 */
class RoundRectProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var progress = 0f // 0 ~ 1

    private var backgroundColor = Color.LTGRAY
    private var cornerRadius = 30f
    private var gradientColors = intArrayOf(Color.RED, Color.YELLOW, Color.GREEN)

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = backgroundColor
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, 1f)
        invalidate()
    }

    fun setGradientColors(colors: IntArray) {
        gradientColors = colors
        invalidate()
    }


    /** 设置背景颜色 */
    fun setBarBackgroundColor(color: Int) {
        bgPaint.color = color
        invalidate()
    }

    /** 设置圆角半径 */
    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 1. 背景圆角矩形
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, bgPaint)

        // 2. 进度条区域
        val progressWidth = width * progress
        if (progressWidth > 0) {
            // 渐变填充
            val shader = LinearGradient(
                0f, 0f, progressWidth, 0f,
                gradientColors, null, Shader.TileMode.CLAMP
            )
            progressPaint.shader = shader

            val progressRect = RectF(0f, 0f, progressWidth, height.toFloat())
            canvas.drawRoundRect(progressRect, cornerRadius, cornerRadius, progressPaint)
        }
    }
}
