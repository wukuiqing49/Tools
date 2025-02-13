package com.wkq.ui.util.span
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

// 自定义 ClickableSpan 类，支持设置文字颜色、背景颜色和图片
class CustomClickableSpan(
    private val textColor: Int,
    private val backgroundColor: Int,
    private var drawable: Drawable?,
    private var onClickListener: OnClickListener? = null
) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        if (textColor != 0) {
            ds.color = textColor
        }
        if (backgroundColor != 0) {
            ds.bgColor = backgroundColor
        }
        ds.isUnderlineText = false
    }

    override fun onClick(widget: View) {
        onClickListener?.onClick(widget)
    }

    fun getDrawable(): Drawable? = drawable

    fun recycle() {
        drawable?.callback = null
        drawable = null
        onClickListener = null
    }

    interface OnClickListener {
        fun onClick(widget: View)
    }
}