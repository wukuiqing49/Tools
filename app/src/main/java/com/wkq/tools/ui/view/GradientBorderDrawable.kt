package com.wkq.tools.ui.view

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.IntDef

/**
 * 渐变色包边的 Drawable，支持设置背景色或背景渐变
 */
class GradientBorderDrawable(
    private val borderWidth: Float,
    private val cornerRadius: Float,
    private val borderColors: IntArray,
    @Orientation private val orientation: Int = Orientation.LEFT_RIGHT,
    private val locations: FloatArray? = null,
    private val backgroundColor: Int = Color.TRANSPARENT, // 新增：默认背景透明
    private val backgroundColors: IntArray? = null, // 新增：背景渐变颜色
    private val backgroundOrientation: Int = orientation // 新增：背景渐变方向
) : Drawable() {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = borderWidth
    }

    // 新增：背景填充画笔
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    // 绘制区域
    private val rectF = RectF()
    // 边框路径
    private val borderPath = Path()
    // 背景路径（考虑边框宽度）
    private val backgroundPath = Path()

    override fun draw(canvas: Canvas) {
        // 计算背景绘制区域（考虑边框宽度）
        val backgroundRect = RectF(
            borderWidth,
            borderWidth,
            bounds.width().toFloat() - borderWidth,
            bounds.height().toFloat() - borderWidth
        )

        // 绘制背景
        if (backgroundColors != null && backgroundColors.size > 1) {
            // 使用渐变背景
            val backgroundShader = createLinearGradient(
                backgroundRect,
                backgroundColors,
                locations,
                backgroundOrientation
            )
            backgroundPaint.shader = backgroundShader
            backgroundPaint.color = Color.TRANSPARENT // 清除可能的纯色设置
        } else {
            // 使用纯色背景
            backgroundPaint.shader = null
            backgroundPaint.color = backgroundColor
        }

        // 创建背景路径（带圆角）
        backgroundPath.reset()
        backgroundPath.addRoundRect(
            backgroundRect,
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )

        // 绘制背景
        canvas.drawPath(backgroundPath, backgroundPaint)

        // 计算边框绘制区域
        rectF.set(
            borderWidth / 2,
            borderWidth / 2,
            bounds.width().toFloat() - borderWidth / 2,
            bounds.height().toFloat() - borderWidth / 2
        )

        // 创建边框渐变
        val shader = createLinearGradient(rectF, borderColors, locations, orientation)
        borderPaint.shader = shader

        // 创建边框路径
        borderPath.reset()
        borderPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)

        // 绘制边框
        canvas.drawPath(borderPath, borderPaint)
    }

    // 辅助方法：创建线性渐变
    private fun createLinearGradient(
        rect: RectF,
        colors: IntArray,
        locations: FloatArray?,
        orientation: Int
    ): Shader {
        val startX: Float
        val startY: Float
        val endX: Float
        val endY: Float

        when (orientation) {
            Orientation.LEFT_RIGHT -> {
                startX = rect.left
                startY = rect.centerY()
                endX = rect.right
                endY = rect.centerY()
            }
            Orientation.RIGHT_LEFT -> {
                startX = rect.right
                startY = rect.centerY()
                endX = rect.left
                endY = rect.centerY()
            }
            Orientation.TOP_BOTTOM -> {
                startX = rect.centerX()
                startY = rect.top
                endX = rect.centerX()
                endY = rect.bottom
            }
            Orientation.BOTTOM_TOP -> {
                startX = rect.centerX()
                startY = rect.bottom
                endX = rect.centerX()
                endY = rect.top
            }
            Orientation.DIAGONAL_LEFT_TOP_TO_RIGHT_BOTTOM -> {
                startX = rect.left
                startY = rect.top
                endX = rect.right
                endY = rect.bottom
            }
            Orientation.DIAGONAL_LEFT_BOTTOM_TO_RIGHT_TOP -> {
                startX = rect.left
                startY = rect.bottom
                endX = rect.right
                endY = rect.top
            }
            else -> {
                // 默认从左到右
                startX = rect.left
                startY = rect.centerY()
                endX = rect.right
                endY = rect.centerY()
            }
        }

        return LinearGradient(startX, startY, endX, endY, colors, locations, Shader.TileMode.CLAMP)
    }

    override fun setAlpha(alpha: Int) {
        borderPaint.alpha = alpha
        backgroundPaint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        borderPaint.colorFilter = colorFilter
        backgroundPaint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int =
        if (backgroundColor == Color.TRANSPARENT && (backgroundColors == null || backgroundColors.isEmpty()))
            PixelFormat.TRANSLUCENT
        else
            PixelFormat.OPAQUE

    @IntDef(
        Orientation.LEFT_RIGHT, Orientation.RIGHT_LEFT,
        Orientation.TOP_BOTTOM, Orientation.BOTTOM_TOP,
        Orientation.DIAGONAL_LEFT_TOP_TO_RIGHT_BOTTOM, Orientation.DIAGONAL_LEFT_BOTTOM_TO_RIGHT_TOP
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class Orientation {
        companion object {
            const val LEFT_RIGHT = 0
            const val RIGHT_LEFT = 1
            const val TOP_BOTTOM = 2
            const val BOTTOM_TOP = 3
            const val DIAGONAL_LEFT_TOP_TO_RIGHT_BOTTOM = 4
            const val DIAGONAL_LEFT_BOTTOM_TO_RIGHT_TOP = 5
        }
    }
}