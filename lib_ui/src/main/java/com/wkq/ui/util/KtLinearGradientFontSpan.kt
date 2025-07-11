package com.wkq.ui.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.Shader
import android.text.style.ReplacementSpan

/**
 * @Author: wkq
 * @Time: 2025/2/11 10:45
 * @Desc:
 */
class KtLinearGradientFontSpan : ReplacementSpan {
    // 文字宽度
    private var mSize = 0

    // 渐变开始颜色
    private var startColor = Color.BLUE

    // 渐变结束颜色
    private var endColor = Color.RED

    // 是否左右渐变
    private var isLeftToRight = true

    constructor()

    constructor(startColor: Int, endColor: Int, leftToRight: Boolean=false) {
        this.startColor = startColor
        this.endColor = endColor
        this.isLeftToRight = leftToRight
    }

    override fun getSize(
        paint: Paint, text: CharSequence, start: Int, end: Int, fm: FontMetricsInt?
    ): Int {
        mSize = paint.measureText(text, start, end).toInt()
        return mSize
    }

    override fun draw(
        canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int,
        bottom: Int, paint: Paint
    ) {
        // 修改y1的值从上到下渐变， 修改x1的值从左到右渐变
        val lg = if (isLeftToRight) {
            LinearGradient(
                0f, 0f, mSize.toFloat(), 0f,
                startColor,
                endColor,
                Shader.TileMode.REPEAT
            )
        } else {
            LinearGradient(
                0f, 0f, 0f, paint.descent() - paint.ascent(),
                startColor,
                endColor,
                Shader.TileMode.REPEAT
            )
        }
        paint.setShader(lg)

        canvas.drawText(text, start, end, x, y.toFloat(), paint) //绘制文字
    }

    fun setLeftToRight(leftToRight: Boolean) {
        isLeftToRight = leftToRight
    }

    fun setEndColor(endColor: Int) {
        this.endColor = endColor
    }

    fun setStartColor(startColor: Int) {
        this.startColor = startColor
    }
}