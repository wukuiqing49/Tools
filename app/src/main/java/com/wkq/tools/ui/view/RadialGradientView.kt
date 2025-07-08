package com.wkq.tools.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * 支持径向渐变的自定义View
 */
class RadialGradientView : View {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 使用不可变数组并修正颜色位置定义
    private val colors = intArrayOf(
        Color.parseColor("#FF4081"),
        Color.parseColor("#FF03DAC5"),
        Color.parseColor("#3F51B5")
    )

    // 确保颜色位置数组长度与颜色数组一致
    private val gradientLocations = floatArrayOf(0f, 0.46f, 1f)

    // 缓存渐变对象避免重复创建
    private var radialGradient: Shader? = null

    // 记录当前尺寸
    private var currentWidth = 0
    private var currentHeight = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 保存当前尺寸
        currentWidth = w
        currentHeight = h
        // 尺寸变化时重新创建渐变
        createRadialGradient()
    }

    private fun createRadialGradient() {
        if (currentWidth <= 0 || currentHeight <= 0) return

        val centerX = currentWidth / 2f
        val centerY = currentHeight / 2f
        val radius = min(centerX, centerY)

        radialGradient = RadialGradient(
            centerX, centerY,
            radius,
            colors,
            gradientLocations,
            Shader.TileMode.CLAMP
        )

        paint.shader = radialGradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (currentWidth <= 0 || currentHeight <= 0) return

        val centerX = currentWidth / 2f
        val centerY = currentHeight / 2f
        val radius = min(centerX, centerY)

        // 绘制圆形区域应用径向渐变
        canvas.drawCircle(centerX, centerY, radius, paint)
    }
}