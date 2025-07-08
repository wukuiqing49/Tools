package com.wkq.tools.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * 支持扫描渐变的自定义View
 */
class SweepGradientView: View{
    // 1. 代码创建时调用
    constructor(context: Context?) : super(context)

    // 2. XML布局加载时必须调用（关键！）
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    // 3. 可选：支持主题样式时添加
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 使用不可变数组存储颜色
    private val colors = intArrayOf(
        Color.parseColor("#FF4081"),
        Color.parseColor("#FF03DAC5"),
        Color.parseColor("#3F51B5")
    )

    // 确保颜色位置数组长度与颜色数组一致
    private val gradientLocations = floatArrayOf(0f, 0.46f, 1f)

    // 缓存渐变对象，避免每次onDraw重新创建
    private var sweepGradient: SweepGradient? = null

    // 记录当前尺寸
    private var currentWidth = 0
    private var currentHeight = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        currentWidth = w
        currentHeight = h
        createSweepGradient()
    }

    private fun createSweepGradient() {
        if (currentWidth <= 0 || currentHeight <= 0) return

        val centerX = currentWidth / 2f
        val centerY = currentHeight / 2f

        sweepGradient = SweepGradient(
            centerX, centerY,
            colors,
            gradientLocations
        )

        paint.shader = sweepGradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (currentWidth <= 0 || currentHeight <= 0) return

        val centerX = currentWidth / 2f
        val centerY = currentHeight / 2f
        val radius = min(centerX, centerY)

        // 绘制圆形区域应用扫描渐变
        canvas.drawCircle(centerX, centerY, radius, paint)
    }
}