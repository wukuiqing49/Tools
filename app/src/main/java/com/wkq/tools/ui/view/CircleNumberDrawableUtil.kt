package com.wkq.tools.ui.view
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/9 11:41
 *
 *@Desc:  圆形 Drawable  圆角Drawable 和带渐变色背景的Drawable
 */


object CircleNumberDrawableUtil{

    /**
     * 带边框圆形数字 Drawable
     */
    fun createCircleWithBorderDrawable(
        context: Context,
        sizeDp: Float,                       // drawable尺寸 dp
        @ColorInt borderColor: Int,          // 圆环颜色
        borderWidthDp: Float,                // 圆环宽度 dp
        number: String,                      // 中间数字
        textSizeDp: Float,                   // 数字字体大小 dp
        @ColorInt textColor: Int             // 数字颜色
    ): Drawable {
        val density = context.resources.displayMetrics.density
        val sizePx = (sizeDp * density).toInt()
        val borderWidthPx = borderWidthDp * density
        val textSizePx = textSizeDp * density

        return object : Drawable() {
            private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = borderColor
                style = Paint.Style.STROKE
                strokeWidth = borderWidthPx
            }

            private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = textColor
                textSize = textSizePx
                textAlign = Paint.Align.CENTER
                typeface = Typeface.DEFAULT_BOLD
            }

            override fun draw(canvas: Canvas) {
                val cx = bounds.width() / 2f
                val cy = bounds.height() / 2f
                val radius = (Math.min(bounds.width(), bounds.height()) - borderWidthPx) / 2f

                // 画圆环
                canvas.drawCircle(cx, cy, radius, borderPaint)

                // 画数字
                val textY = cy - (textPaint.descent() + textPaint.ascent()) / 2f
                canvas.drawText(number, cx, textY, textPaint)
            }

            override fun setAlpha(alpha: Int) {
                borderPaint.alpha = alpha
                textPaint.alpha = alpha
            }

            override fun setColorFilter(colorFilter: ColorFilter?) {
                borderPaint.colorFilter = colorFilter
                textPaint.colorFilter = colorFilter
            }

            @Deprecated("Deprecated in Java")
            override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

            override fun getIntrinsicWidth(): Int = sizePx
            override fun getIntrinsicHeight(): Int = sizePx
        }
    }

    /**
     * 不带边框的实心圆形数字 Drawable
     */
    fun createCircleDrawable(
        context: Context,
        sizeDp: Float,                       // drawable尺寸 dp
        @ColorInt fillColor: Int,            // 填充颜色
        number: String,                      // 中间数字
        textSizeDp: Float,                   // 字体大小 dp
        @ColorInt textColor: Int             // 字体颜色
    ): Drawable {
        val density = context.resources.displayMetrics.density
        val sizePx = (sizeDp * density).toInt()
        val textSizePx = textSizeDp * density

        return object : Drawable() {
            private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = fillColor
                style = Paint.Style.FILL
            }

            private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = textColor
                textSize = textSizePx
                textAlign = Paint.Align.CENTER
                typeface = Typeface.DEFAULT_BOLD
            }

            override fun draw(canvas: Canvas) {
                val cx = bounds.width() / 2f
                val cy = bounds.height() / 2f
                val radius = Math.min(bounds.width(), bounds.height()) / 2f

                // 画实心圆
                canvas.drawCircle(cx, cy, radius, fillPaint)

                // 画数字
                val textY = cy - (textPaint.descent() + textPaint.ascent()) / 2f
                canvas.drawText(number, cx, textY, textPaint)
            }

            override fun setAlpha(alpha: Int) {
                fillPaint.alpha = alpha
                textPaint.alpha = alpha
            }

            override fun setColorFilter(colorFilter: ColorFilter?) {
                fillPaint.colorFilter = colorFilter
                textPaint.colorFilter = colorFilter
            }

            @Deprecated("Deprecated in Java")
            override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

            override fun getIntrinsicWidth(): Int = sizePx
            override fun getIntrinsicHeight(): Int = sizePx
        }
    }

    /**
     * 创建带背景渐变色的圆形数字 Drawable
     */
    fun createGradientCircleDrawable(
        context: Context,
        sizeDp: Float,                        // Drawable 尺寸 dp
        colors: IntArray,                     // 渐变色数组（至少2个颜色）
        orientation: GradientOrientation,     // 渐变方向
        number: String,                       // 中间数字
        textSizeDp: Float,                    // 字体大小 dp
        @ColorInt textColor: Int              // 字体颜色
    ): Drawable {
        val density = context.resources.displayMetrics.density
        val sizePx = (sizeDp * density).toInt()
        val textSizePx = textSizeDp * density

        return object : Drawable() {
            private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
            }

            private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = textColor
                textSize = textSizePx
                textAlign = Paint.Align.CENTER
                typeface = Typeface.DEFAULT_BOLD
            }

            override fun draw(canvas: Canvas) {
                // 设置渐变色
                val shader = LinearGradient(
                    orientation.startX(sizePx.toFloat()),
                    orientation.startY(sizePx.toFloat()),
                    orientation.endX(sizePx.toFloat()),
                    orientation.endY(sizePx.toFloat()),
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
                backgroundPaint.shader = shader

                val cx = bounds.width() / 2f
                val cy = bounds.height() / 2f
                val radius = Math.min(bounds.width(), bounds.height()) / 2f

                // 画渐变背景圆
                canvas.drawCircle(cx, cy, radius, backgroundPaint)

                // 画数字
                val textY = cy - (textPaint.descent() + textPaint.ascent()) / 2f
                canvas.drawText(number, cx, textY, textPaint)
            }

            override fun setAlpha(alpha: Int) {
                backgroundPaint.alpha = alpha
                textPaint.alpha = alpha
            }

            override fun setColorFilter(colorFilter: ColorFilter?) {
                backgroundPaint.colorFilter = colorFilter
                textPaint.colorFilter = colorFilter
            }

            @Deprecated("Deprecated in Java")
            override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

            override fun getIntrinsicWidth(): Int = sizePx
            override fun getIntrinsicHeight(): Int = sizePx
        }
    }

    /**
     * 渐变方向
     */
    enum class GradientOrientation {
        LEFT_RIGHT, RIGHT_LEFT, TOP_BOTTOM, BOTTOM_TOP;

        fun startX(size: Float) = when (this) {
            LEFT_RIGHT, RIGHT_LEFT -> 0f
            TOP_BOTTOM, BOTTOM_TOP -> size / 2f
        }

        fun endX(size: Float) = when (this) {
            LEFT_RIGHT -> size
            RIGHT_LEFT -> 0f
            TOP_BOTTOM, BOTTOM_TOP -> size / 2f
        }

        fun startY(size: Float) = when (this) {
            TOP_BOTTOM, BOTTOM_TOP -> 0f
            LEFT_RIGHT, RIGHT_LEFT -> size / 2f
        }

        fun endY(size: Float) = when (this) {
            TOP_BOTTOM -> size
            BOTTOM_TOP -> 0f
            LEFT_RIGHT, RIGHT_LEFT -> size / 2f
        }
    }
}
