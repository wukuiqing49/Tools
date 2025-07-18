package com.wkq.tools.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.view.View
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


/**
 *
 *@Author: wkq
 *
 *@Time: 2025/4/25 9:59
 *
 *@Desc:
 */


object ShapeDrawableUtil {

    /**
     * 创建背景填充 Drawable，支持纯色或渐变（渐变需要后续调用 applyLinearGradientForce）
     * @param solidColor 纯色填充（优先使用）
     * @param gradientColors 渐变色（如不为 null，调用方需后续调用 applyLinearGradientForce）
     */
    fun createFillDrawable(
        context: Context,
        cornerRadiusDp: Float = 12f,
        isCircle: Boolean = false,
        solidColor: Int? = null,
        gradientColors: IntArray? = null
    ): MaterialShapeDrawable {
        val radiusPx = dpToPx(context, cornerRadiusDp)
        val shapeModel = createShapeModel(isCircle, radiusPx)

        return MaterialShapeDrawable(shapeModel).apply {
            paintStyle = Paint.Style.FILL
            if (solidColor != null) {
                fillColor = ColorStateList.valueOf(solidColor)
            }
            // gradientColors 由调用者用 applyLinearGradientForce 设置
        }
    }

    /**
     * 创建边框 Drawable，支持纯色或渐变（渐变需要后续调用 applyLinearGradientForce）
     * @param strokeColor 纯色边框色（优先使用）
     * @param gradientColors 渐变边框色（不为 null 时需要调用 applyLinearGradientForce）
     */
    fun createStrokeDrawable(
        context: Context,
        cornerRadiusDp: Float = 12f,
        isCircle: Boolean = false,
        strokeWidthDp: Float = 2f,
        strokeColor: Int? = null,
        gradientColors: IntArray? = null
    ): MaterialShapeDrawable {
        val radiusPx = dpToPx(context, cornerRadiusDp)
        val strokePx = dpToPx(context, strokeWidthDp)
        val shapeModel = createShapeModel(isCircle, radiusPx)

        return MaterialShapeDrawable(shapeModel).apply {
            paintStyle = Paint.Style.STROKE
            strokeWidth = strokePx
            if (strokeColor != null) {
                setStrokeColor(ColorStateList.valueOf(strokeColor))
            }
            // gradientColors 由调用者用 applyLinearGradientForce 设置
        }
    }

    /**
     * 给 Drawable 设置渐变 shader（背景或边框均可）
     */
    fun applyLinearGradientForce(
        view: View,
        drawable: MaterialShapeDrawable,
        colors: IntArray,
        orientation: GradientOrientation = GradientOrientation.LEFT_RIGHT
    ) {
        view.post {
            val shader = LinearGradient(
                0f, 0f,
                orientation.endX(view.width), orientation.endY(view.height),
                colors, null, Shader.TileMode.CLAMP
            )
            try {
                val field = MaterialShapeDrawable::class.java.getDeclaredField("tintShader")
                field.isAccessible = true
                field.set(drawable, shader)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            view.invalidate()
        }
    }

    /**
     * 创建圆角模型
     */
    private fun createShapeModel(isCircle: Boolean, radiusPx: Float): ShapeAppearanceModel {
        return if (isCircle) {
            ShapeAppearanceModel.builder()
                .setAllCorners(CornerFamily.ROUNDED, 50f)
                .build()
        } else {
            ShapeAppearanceModel.builder()
                .setAllCorners(CornerFamily.ROUNDED, radiusPx)
                .build()
        }
    }

    /**
     * dp 转 px
     */
    private fun dpToPx(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    /**
     * 渐变方向枚举
     */
    enum class GradientOrientation {
        LEFT_RIGHT, TOP_BOTTOM, DIAGONAL_LEFT_RIGHT, DIAGONAL_RIGHT_LEFT;

        fun endX(width: Int): Float = when (this) {
            LEFT_RIGHT, DIAGONAL_LEFT_RIGHT -> width.toFloat()
            DIAGONAL_RIGHT_LEFT -> 0f
            else -> 0f
        }

        fun endY(height: Int): Float = when (this) {
            TOP_BOTTOM -> height.toFloat()
            DIAGONAL_LEFT_RIGHT -> height.toFloat()
            DIAGONAL_RIGHT_LEFT -> height.toFloat()
            else -> 0f
        }
    }
}
