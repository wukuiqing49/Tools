package com.wkq.ui.util.span

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.DrawableMarginSpan
import android.text.style.DynamicDrawableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.IconMarginSpan
import android.text.style.ImageSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/2/12 10:16
 *
 *@Desc:
 *
 * 1、BackgroundColorSpan 背景色
 * 2、ClickableSpan 文本可点击，有点击事件
 * 3、ForegroundColorSpan 文本颜色（前景色）
 * 4、MaskFilterSpan 修饰效果，如模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)
 * 5、MetricAffectingSpan 父类，一般不用
 * 6、RasterizerSpan 光栅效果
 * 7、StrikethroughSpan 删除线（中划线）
 * 8、SuggestionSpan 相当于占位符
 * 9、UnderlineSpan 下划线
 * 10、AbsoluteSizeSpan 绝对大小（文本字体）
 * 11、DynamicDrawableSpan 设置图片，基于文本基线或底部对齐。
 * 12、ImageSpan 图片
 * 13、RelativeSizeSpan 相对大小（文本字体）
 * 14、ReplacementSpan 父类，一般不用
 * 15、ScaleXSpan 基于x轴缩放
 * 16、StyleSpan 字体样式：粗体、斜体等
 * 17、SubscriptSpan 下标（数学公式会用到）
 * 18、SuperscriptSpan 上标（数学公式会用到）
 * 19、TextAppearanceSpan 文本外貌（包括字体、大小、样式和颜色）
 * 20、TypefaceSpan 文本字体
 * 21、URLSpan 文本超链接
 *
 */

// 工具类，用于构建包含不同 Span 的 SpannableStringBuilder
class SpanBuilder {
    public val spannableStringBuilder = SpannableStringBuilder()
    private val spanInfoList = mutableListOf<SpanInfo>()

    // 内部类，用于存储 Span 信息
    private data class SpanInfo(
        val span: Any,
        val start: Int,
        val end: Int,
        val flags: Int
    )

    // 动态添加文字
    fun appendText(text: CharSequence): SpanBuilder {
        spannableStringBuilder.append(text)
        return this
    }

    // 添加 Span 信息到列表
    fun addSpan(span: Any, start: Int, end: Int, flags: Int): SpanBuilder {
        spanInfoList.add(SpanInfo(span, start, end, flags))
        return this
    }

    // 应用所有 Span 到 SpannableStringBuilder 并返回结果
    fun build(): SpannableStringBuilder {
        for (info in spanInfoList) {
            spannableStringBuilder.setSpan(info.span, info.start, info.end, info.flags)
            if (info.span is CustomClickableSpan) {
                val drawable = info.span.getDrawable()
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                    val imageSpan = ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE )
                    spannableStringBuilder.setSpan(imageSpan, info.start, info.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
        return spannableStringBuilder
    }

    // 以下是一些常用 Span 的便捷添加方法

    // 添加字体大小 Span
    fun addSizeSpan(size: Int, start: Int, end: Int, flags: Int): SpanBuilder {
        return addSpan(AbsoluteSizeSpan(size), start, end, flags)
    }

    // 添加前景色 Span
    fun addForegroundColorSpan(color: Int, start: Int, end: Int, flags: Int): SpanBuilder {
        return addSpan(ForegroundColorSpan(color), start, end, flags)
    }

    // 添加背景色 Span
    fun addBackgroundColorSpan(color: Int, start: Int, end: Int, flags: Int): SpanBuilder {
        return addSpan(BackgroundColorSpan(color), start, end, flags)
    }

    // 添加样式 Span（如粗体、斜体）
    fun addStyleSpan(style: Int, start: Int, end: Int, flags: Int): SpanBuilder {
        return addSpan(StyleSpan(style), start, end, flags)
    }

    // 添加下划线 Span
    fun addUnderlineSpan(start: Int, end: Int, flags: Int): SpanBuilder {
        return addSpan(UnderlineSpan(), start, end, flags)
    }

    // 添加中划线 Span
    fun withStrikethroughSpan(start: Int, end: Int, flags: Int = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE): SpanBuilder {
        return addSpan(StrikethroughSpan(), start, end, flags)
    }
    // 添加 ImageSpan
    fun addImageSpan(imageSpan: ImageSpan, start: Int, end: Int, flags: Int): SpanBuilder {
        return addSpan(imageSpan, start, end, flags)
    }

    // 添加 IconMarginSpan
    fun addIconMarginSpan(iconMarginSpan: IconMarginSpan, start: Int, end: Int, flags: Int): SpanBuilder {
        return addSpan(iconMarginSpan, start, end, flags)
    }

    // 添加 DrawableMarginSpan
    fun addDrawableMarginSpan(drawableMarginSpan: DrawableMarginSpan, start: Int, end: Int, flags: Int): SpanBuilder {
        return addSpan(drawableMarginSpan, start, end, flags)
    }

    // 添加可点击的 CustomClickableSpan
    fun addCustomClickableSpan(
        textColor: Int,
        backgroundColor: Int,
        drawable: Drawable?,
        start: Int,
        end: Int,
        flags: Int,
        onClickListener: CustomClickableSpan.OnClickListener? = null
    ): SpanBuilder {
        return addSpan(
            CustomClickableSpan(textColor,backgroundColor,  drawable, onClickListener),
            start,
            end,
            flags
        )
    }

    fun recycle() {
        for (info in spanInfoList) {
            if (info.span is CustomClickableSpan) {
                info.span.recycle()
            }
        }
        spanInfoList.clear()
        spannableStringBuilder.clear()
        spannableStringBuilder.clearSpans()
    }
}