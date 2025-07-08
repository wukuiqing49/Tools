package com.wkq.tools.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

/**
 * 支持自定义线性渐变的View
 */
class LinearGradientView : View {
    // 1. 代码创建时调用
    constructor(context: Context?) : super(context)

    // 2. XML布局加载时必须调用（关键！）
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    // 3. 可选：支持主题样式时添加
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    // 使用更合适的命名和初始化方式
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 使用val声明不可变数组，初始化颜色值
    private val colors = intArrayOf(
        Color.parseColor("#FF4081"),
        Color.parseColor("#FF03DAC5"),
        Color.parseColor("#3F51B5")
    )

    // 确保gradientLocations长度与colors一致
    private val gradientLocations = floatArrayOf(0f, 0.46f, 1f)

    // 缓存渐变对象，避免每次onDraw重新创建
    private var linearGradient: Shader? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 当View尺寸变化时创建渐变对象
        if (w > 0 && h > 0) {
            linearGradient = LinearGradient(
                0f, 0f,
                w.toFloat(), h.toFloat(),
                colors,
                gradientLocations,
                Shader.TileMode.CLAMP
            )
            paint.shader = linearGradient
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制矩形区域，应用渐变
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }
}