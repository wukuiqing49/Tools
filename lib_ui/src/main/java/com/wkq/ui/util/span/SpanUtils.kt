package com.wkq.ui.util.span

/**
*
*@Author: wkq
*
*@Time: 2025/2/12 14:23
*
*@Desc:
*/

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.DynamicDrawableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.MaskFilterSpan
import android.text.style.RelativeSizeSpan
import android.text.style.ScaleXSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.TextAppearanceSpan
import android.text.style.TypefaceSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.wkq.ui.view.KtLinearGradientFontSpan

/**
 * 用于处理 Android 中 SpannableString 的各种样式设置的工具类。
 * 提供了丰富的方法来对文本进行多样化的样式定制，
 * 最后还能将处理好的 SpannableString 追加到 TextView 中。
 */
class SpanUtils private constructor(private val context: Context) {
    companion object {
        @Volatile
        private var instance: SpanUtils? = null

        /**
         * 获取 SpanUtils 的单例实例。
         *
         * @param context 应用程序上下文，用于访问资源等操作。
         * @return SpanUtils 的单例实例。
         */
        fun getInstance(context: Context): SpanUtils {
            return instance ?: synchronized(this) {
                instance ?: SpanUtils(context).also { instance = it }
            }
        }
    }

    /**
     * 处理可能为 null 的文本，若文本为 null 则返回空字符串。
     *
     * @param text 待处理的文本，可能为 null。
     * @return 处理后的非空文本，若原文本为 null 则返回 ""。
     */
    private fun handleNullText(text: String?): String {
        return text ?: ""
    }


    /**
     * 设置文字渐变色
     * @param startText String?
     * @param spanText String?
     * @param endText String?
     * @param startColor Int
     * @param endColor Int
     * @param isLeftToRight Boolean
     * @return SpannableString
     */
    fun setTextGradientColor(
        startText: String?,
        spanText: String?,
        endText: String?,
        startColor: Int = Color.GREEN,
        endColor: Int = Color.GREEN,
        isLeftToRight:Boolean=true
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        val span = KtLinearGradientFontSpan(startColor, endColor, isLeftToRight)
        spannable.setSpan(
            span, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    /**
     * 设置 spanText 的背景色。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param color 背景色，默认为绿色（Color.GREEN）。
     * @return 应用了背景色样式的 SpannableString。
     */
    fun setBackgroundColorSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        color: Int = Color.GREEN
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            BackgroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    /**
     * 设置 spanText 可点击及点击事件。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param clickListener 点击 spanText 时触发的回调函数。
     * @return 应用了可点击样式的 SpannableString。
     */
    fun setClickableSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        clickListener: (content:String) -> Unit
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: android.view.View) {
                clickListener(handleNullText(spanText))
            }
        }
        spannable.setSpan(clickableSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }

    /**
     * 设置 spanText 的文本颜色（前景色）。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param color 前景色，默认为蓝色（Color.BLUE）。
     * @return 应用了前景色样式的 SpannableString。
     */
    fun setForegroundColorSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        color: Int = Color.BLUE
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            ForegroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    /**
     * 设置 spanText 的修饰效果，如模糊、浮雕。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @return 应用了模糊和浮雕修饰效果的 SpannableString。
     */
    fun setMaskFilterSpan(
        startText: String?,
        spanText: String?,
        endText: String?
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        // 模糊(BlurMaskFilter)

        val blurMaskFilterSpan = MaskFilterSpan(BlurMaskFilter(3f, BlurMaskFilter.Blur.OUTER))
        spannable.setSpan(
            blurMaskFilterSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        // 浮雕(EmbossMaskFilter)
//        val embossMaskFilterSpan =
//            MaskFilterSpan(EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.2f, 28f, 5f))
//        spannable.setSpan(
//            embossMaskFilterSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
//        )
        return spannable
    }

    /**
     * 设置 spanText 的删除线（中划线）。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @return 应用了删除线样式的 SpannableString。
     */
    fun setStrikethroughSpan(
        startText: String?,
        spanText: String?,
        endText: String?
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            StrikethroughSpan(), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    /**
     * 设置 spanText 的下划线。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @return 应用了下划线样式的 SpannableString。
     */
    fun setUnderlineSpan(
        startText: String?,
        spanText: String?,
        endText: String?
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(UnderlineSpan(), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }

    /**
     * 设置 spanText 的绝对大小（文本字体）。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param size 字体的绝对大小。
     * @param dip 若为 true，表示大小以 dip 为单位；若为 false，表示大小以像素为单位，默认为 true。
     * @return 应用了绝对大小样式的 SpannableString。
     */
    fun setAbsoluteSizeSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        size: Int,
        dip: Boolean = true
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            AbsoluteSizeSpan(size, dip), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    /**
     * 设置 spanText 中的图片，基于文本基线或底部对齐。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param drawableResId 图片资源的 ID。
     * @param width 图片的宽度。
     * @param height 图片的高度。
     * @return 应用了图片样式（基线和底部对齐）的 SpannableString。
     */
    fun setDynamicDrawableSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        drawableResId: Int,
        width: Int,
        height: Int
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        val drawableSpanBaseline =
            object : DynamicDrawableSpan(DynamicDrawableSpan.ALIGN_BASELINE) {
                override fun getDrawable(): Drawable {
                    val d = context.resources.getDrawable(drawableResId)
                    d.setBounds(0, 0, width, height)
                    return d
                }
            }
        spannable.setSpan(
            drawableSpanBaseline, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        val drawableSpanBottom = object : DynamicDrawableSpan(DynamicDrawableSpan.ALIGN_BOTTOM) {
            override fun getDrawable(): Drawable {
                val d = context.resources.getDrawable(drawableResId)
                d.setBounds(0, 0, width, height)
                return d
            }
        }
        spannable.setSpan(
            drawableSpanBottom, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    /**
     * 设置 spanText 中的图片。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param drawableResId 图片资源的 ID。
     * @param width 图片的宽度。
     * @param height 图片的高度。
     * @return 应用了图片样式的 SpannableString。
     */
    fun setImageSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        drawableResId: Int,
        width: Int,
        height: Int
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        val d = context.resources.getDrawable(drawableResId)
        d.setBounds(0, 0, width, height)
        spannable.setSpan(ImageSpan(d), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }

    /**
     * 设置 spanText 的相对大小（文本字体）。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param proportion 字体的相对大小比例。
     * @return 应用了相对大小样式的 SpannableString。
     */
    fun setRelativeSizeSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        proportion: Float
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            RelativeSizeSpan(proportion), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    /**
     * 基于 x 轴缩放 spanText。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param proportion x 轴的缩放比例。
     * @return 应用了 x 轴缩放样式的 SpannableString。
     */
    fun setScaleXSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        proportion: Float
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            ScaleXSpan(proportion), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    /**
     * 设置 spanText 的字体样式：粗体、斜体等。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param style 字体样式，默认为粗斜体（Typeface.BOLD_ITALIC）。
     * @return 应用了字体样式的 SpannableString。
     */
    fun setStyleSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        style: Int = Typeface.BOLD_ITALIC
    ): SpannableString {
        val combinedText =
            handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            StyleSpan(style), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }


    /**
     * 设置 spanText 的下标。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @return 应用了下标样式的 SpannableString。
     */
    fun setSubscriptSpan(
        startText: String?,
        spanText: String?,
        endText: String?
    ): SpannableString {
        val combinedText = handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(SubscriptSpan(), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }
    /**
     * 设置 spanText 的上标。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @return 应用了上标样式的 SpannableString。
     */
    fun setSuperscriptSpan(
        startText: String?,
        spanText: String?,
        endText: String?
    ): SpannableString {
        val combinedText = handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(SuperscriptSpan(), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }
    /**
     * 设置 spanText 的外貌（包括字体、大小、样式和颜色）。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param appearanceStyle 文本外貌的样式资源 ID，默认为 android.R.style.TextAppearance_Medium。
     * @return 应用了文本外貌样式的 SpannableString。
     */
    fun setTextAppearanceSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        appearanceStyle: Int = android.R.style.TextAppearance_Medium
    ): SpannableString {
        val combinedText = handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            TextAppearanceSpan(context, appearanceStyle),
            startIndex,
            endIndex,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }
    /**
     * 设置 spanText 的字体。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param typeface 字体名称，默认为 "monospace"。
     * @return 应用了指定字体样式的 SpannableString。
     */

    @RequiresApi(Build.VERSION_CODES.P)
    fun setTypefaceSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        typeface: Typeface
    ): SpannableString {
        val combinedText = handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            TypefaceSpan(typeface),
            startIndex,
            endIndex,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    fun setTypefaceSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        typeface: String = "monospace"
    ): SpannableString {
        val combinedText = handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(
            TypefaceSpan(typeface),
            startIndex,
            endIndex,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannable
    }


    /**
     * 设置 spanText 的超链接。
     *
     * @param startText 起始文本，若为 null 会被处理为空字符串。
     * @param spanText 要设置样式的中间文本，若为 null 会被处理为空字符串。
     * @param endText 结束文本，若为 null 会被处理为空字符串。
     * @param url 超链接的 URL 地址。
     * @return 应用了超链接样式的 SpannableString。
     */
    fun setURLSpan(
        startText: String?,
        spanText: String?,
        endText: String?,
        url: String
    ): SpannableString {
        val combinedText = handleNullText(startText) + handleNullText(spanText) + handleNullText(endText)
        val startIndex = handleNullText(startText).length
        val endIndex = startIndex + handleNullText(spanText).length
        val spannable = SpannableString(combinedText)
        spannable.setSpan(URLSpan(url), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }
    /**
     * 将 SpannableString 追加到 TextView 并设置可点击（针对 URLSpan 等）。
     *
     * @param textView 要追加文本的 TextView。
     * @param spannable 要追加的 SpannableString。
     */
    fun appendSpannableToTextView(textView: TextView, spannable: SpannableString) {
        val builder = SpannableStringBuilder(textView.text)
        builder.append("\n")
        builder.append(spannable)
        textView.text = builder
        textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }
}

