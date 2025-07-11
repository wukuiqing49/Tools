package com.wkq.tools.ui.view


import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.wkq.tools.R


class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var textStartColor: Int = 0
    private var textEndColor: Int = 0
    private var bgStartColor: Int = 0
    private var bgEndColor: Int = 0
    private var cornerRadius: Float = 0f

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.GradientTextView,
            defStyleAttr,
            0
        )
        textStartColor = typedArray.getColor(
            R.styleable.GradientTextView_textStartColor,
            ContextCompat.getColor(context, R.color.white)
        )
        textEndColor = typedArray.getColor(
            R.styleable.GradientTextView_textEndColor,
            ContextCompat.getColor(context, R.color.white)
        )
        bgStartColor = typedArray.getColor(
            R.styleable.GradientTextView_bgStartColor,
            ContextCompat.getColor(context, R.color.white)
        )
        bgEndColor = typedArray.getColor(
            R.styleable.GradientTextView_bgEndColor,
            ContextCompat.getColor(context, R.color.white)
        )
        cornerRadius = typedArray.getDimension(
            R.styleable.GradientTextView_cornerRadius,
            0f
        )
        // 设置文字默认居中显示
        gravity = Gravity.CENTER
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0) {
            // 设置文字渐变色
            val textShader = LinearGradient(
                0f, 0f, width.toFloat(), 0f,
                intArrayOf(textStartColor, textEndColor),
                null,
                Shader.TileMode.CLAMP
            )
            paint.shader = textShader

            // 设置背景渐变色
            val bgDrawable = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(bgStartColor, bgEndColor)
            )
            bgDrawable.cornerRadius = cornerRadius
            background = bgDrawable
        }
    }

    override fun onDraw(canvas: Canvas) {
        val textPaint = paint
        textPaint.color = currentTextColor
        textPaint.textSize = textSize
        textPaint.typeface = typeface
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER

        // 计算文字的宽度和高度
        val textWidth = textPaint.measureText(text.toString())
        val fontMetrics = textPaint.fontMetrics
        val textHeight = fontMetrics.bottom - fontMetrics.top

        // 计算文字的中心点
        val xPos = (width / 2).toFloat()
        val yPos = (height / 2 + (textHeight / 2 - fontMetrics.bottom)).toFloat()

        // 绘制文字
        canvas.drawText(text.toString(), xPos, yPos, textPaint)
    }

    fun setTextGradientColors(startColor: Int, endColor: Int) {
        this.textStartColor = startColor
        this.textEndColor = endColor
        invalidate()
    }

    fun setBgGradientColors(startColor: Int, endColor: Int) {
        this.bgStartColor = startColor
        this.bgEndColor = endColor
        invalidate()
    }

    fun setCornerRadius(radius: Float) {
        this.cornerRadius = radius
        invalidate()
    }
}
