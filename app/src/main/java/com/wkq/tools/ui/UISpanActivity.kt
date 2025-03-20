package com.wkq.tools.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wkq.tools.R
import com.wkq.tools.databinding.ActivityUiSpanBinding
import com.wkq.tools.databinding.ActivityUiTextviewBinding
import com.wkq.ui.util.WebViewUtil
import com.wkq.ui.util.span.CustomClickableSpan
import com.wkq.ui.util.span.SpanBuilder
import com.wkq.ui.util.span.SpanUtils
import com.wkq.ui.view.KtLinearGradientFontSpan

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/2/11 15:10
 *
 *@Desc:
 *
 *  * 1、BackgroundColorSpan 背景色
 *  * 2、ClickableSpan 文本可点击，有点击事件
 *  * 3、ForegroundColorSpan 文本颜色（前景色）
 *  * 4、MaskFilterSpan 修饰效果，如模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)
 *  * 5、MetricAffectingSpan 父类，一般不用
 *  * 6、RasterizerSpan 光栅效果
 *  * 7、StrikethroughSpan 删除线（中划线）
 *  * 8、SuggestionSpan 相当于占位符
 *  * 9、UnderlineSpan 下划线
 *  * 10、AbsoluteSizeSpan 绝对大小（文本字体）
 *  * 11、DynamicDrawableSpan 设置图片，基于文本基线或底部对齐。
 *  * 12、ImageSpan 图片
 *  * 13、RelativeSizeSpan 相对大小（文本字体）
 *  * 14、ReplacementSpan 父类，一般不用
 *  * 15、ScaleXSpan 基于x轴缩放
 *  * 16、StyleSpan 字体样式：粗体、斜体等
 *  * 17、SubscriptSpan 下标（数学公式会用到）
 *  * 18、SuperscriptSpan 上标（数学公式会用到）
 *  * 19、TextAppearanceSpan 文本外貌（包括字体、大小、样式和颜色）
 *  * 20、TypefaceSpan 文本字体
 *  * 21、URLSpan 文本超链接
 *  *
 *
 */
class UISpanActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, UISpanActivity::class.java))
        }
    }

    val binding: ActivityUiSpanBinding by lazy {
        ActivityUiSpanBinding.inflate(LayoutInflater.from(this))
    }


    private fun getGradientSpan(content: String, startColor: Int, endColor: Int, isLeftToRight: Boolean): SpannableStringBuilder {
        val spannableStringBuilder = SpannableStringBuilder(content)
        val span = KtLinearGradientFontSpan(startColor, endColor, isLeftToRight)
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 若有需要可以在这里用SpanString系列的其他类，给文本添加下划线、超链接、删除线...等等效果
        return spannableStringBuilder
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btFinish.setOnClickListener {
            finish()
        }
        // 自定义颜色
        val customColor = Color.parseColor("#FF5722")
        // 自定义图片资源，假设你在 res/drawable 目录下有一张名为 ic_example 的图片
        val customDrawableResId = android.R.drawable.star_big_on
        val spanUtils = SpanUtils.getInstance(this)

        val typeface = ResourcesCompat.getFont(this, R.font.alimama_shu_hei_ti_bold);
        binding.tv01.text="设置字体"
        binding.tv01.setTypeface(typeface)

        binding.tv02.setTypeface(typeface)
        binding.tv02.setText(
            getGradientSpan(
                "修改字体渐变色", getColor(R.color.color_start), getColor(R.color.color_end),
                true
            ), TextView.BufferType.SPANNABLE
        )


        spanUtils.appendSpannableToTextView(binding.tv1, spanUtils.setTextGradientColor( "起始文本 ",
            "设置字体渐变色",
            " 结束文本",getColor(R.color.color_start), getColor(R.color.color_end),true))

        // 1. 设置背景色
        val backgroundColorSpannable = spanUtils.setBackgroundColorSpan(
            "起始文本 ",
            "背景色文本",
            " 结束文本",
            customColor
        )
        spanUtils.appendSpannableToTextView(binding.tv1, backgroundColorSpannable)

        // 2. 设置可点击文本
        val clickableSpannable = spanUtils.setClickableSpan(
            "起始文本",
            "点击我弹出吐司",
            "起始文本",getColor(R.color.color_end),
            { content ->
                Toast.makeText(this@UISpanActivity, content, Toast.LENGTH_SHORT).show()
            }
        )
        spanUtils.appendSpannableToTextView(binding.tv2, clickableSpannable)
        // 开启 TextView 的可点击和可触摸事件
        binding.tv2.isClickable = true
        binding.tv2.isFocusable = true
        // 设置 MovementMethod 以处理点击事件
        binding.tv2.movementMethod = android.text.method.LinkMovementMethod.getInstance()

        //设置去除高亮按压颜色
        binding.tv2.setHighlightColor(Color.TRANSPARENT)

        // 3. 设置前景色
        val foregroundColorSpannable = spanUtils.setForegroundColorSpan(
            "起始文本 ",
            "前景色文本",
            " 结束文本",
            customColor
        )
        spanUtils.appendSpannableToTextView(binding.tv3, foregroundColorSpannable)

        // 4. 设置修饰效果（模糊和浮雕）
        binding.tv4.setLayerType(TextView.LAYER_TYPE_SOFTWARE, null)
        val maskFilterSpannable = spanUtils.setMaskFilterSpan(
            "起始文本 ",
            "修饰效果文本",
            " 结束文本"
        )
        spanUtils.appendSpannableToTextView(binding.tv4, maskFilterSpannable)

        // 5. 设置删除线
        val strikethroughSpannable = spanUtils.setStrikethroughSpan(
            "起始文本 ",
            "删除线文本",
            " 结束文本"
        )
        spanUtils.appendSpannableToTextView(binding.tv5, strikethroughSpannable)

        // 6. 设置下划线
        val underlineSpannable = spanUtils.setUnderlineSpan(
            "起始文本 ",
            "下划线文本",
            " 结束文本"
        )
        spanUtils.appendSpannableToTextView(binding.tv6, underlineSpannable)

        // 7. 设置绝对大小
        val absoluteSizeSpannable = spanUtils.setAbsoluteSizeSpan(
            "起始文本 ",
            "绝对大小文本",
            " 结束文本",
            24
        )
        spanUtils.appendSpannableToTextView(binding.tv7, absoluteSizeSpannable)

        // 8. 设置动态图片（基于文本基线或底部对齐）
        val dynamicDrawableSpannable = spanUtils.setDynamicDrawableSpan(
            "起始文本 ",
            "动态图片文本",
            " 结束文本",
            customDrawableResId,
            100,
            100
        )
        spanUtils.appendSpannableToTextView(binding.tv8, dynamicDrawableSpannable)

        // 9. 设置图片
        val imageSpannable = spanUtils.setImageSpan(
            "起始文本 ",
            "图片文本",
            " 结束文本",
            customDrawableResId,
            100,
            100
        )
        spanUtils.appendSpannableToTextView(binding.tv9, imageSpannable)

        // 10. 设置相对大小
        val relativeSizeSpannable = spanUtils.setRelativeSizeSpan(
            "起始文本 ",
            "相对大小文本",
            " 结束文本",
            1.5f
        )
        spanUtils.appendSpannableToTextView(binding.tv10, relativeSizeSpannable)

        // 11. 基于 x 轴缩放
        val scaleXSpannable = spanUtils.setScaleXSpan(
            "起始文本 ",
            "x 轴缩放文本",
            " 结束文本",
            2f
        )
        spanUtils.appendSpannableToTextView(binding.tv11, scaleXSpannable)

        // 12. 设置字体样式
        val styleSpannable = spanUtils.setStyleSpan(
            "起始文本 ",
            "字体样式文本",
            " 结束文本"
        )
        spanUtils.appendSpannableToTextView(binding.tv12, styleSpannable)

        // 13. 设置下标
        val subscriptSpannable = spanUtils.setSubscriptSpan(
            "起始文本 ",
            "下标文本",
            " 结束文本"
        )
        spanUtils.appendSpannableToTextView(binding.tv13, subscriptSpannable)

        // 14. 设置上标
        val superscriptSpannable = spanUtils.setSuperscriptSpan(
            "起始文本 ",
            "上标文本",
            " 结束文本"
        )
        spanUtils.appendSpannableToTextView(binding.tv14, superscriptSpannable)

        // 15. 设置文本外貌
        val textAppearanceSpannable = spanUtils.setTextAppearanceSpan(
            "起始文本 ",
            "文本外貌文本",
            " 结束文本"
        )
        spanUtils.appendSpannableToTextView(binding.tv15, textAppearanceSpannable)
      val tp=  ResourcesCompat.getFont(this, R.font.alimama_shu_hei_ti_bold)
        if (tp!=null){
            // 16. 设置字体
            val typefaceSpannable = spanUtils.setTypefaceSpan(
                "起始文本 ",
                "字体文本",
                " 结束文本",tp
            )
            spanUtils.appendSpannableToTextView(binding.tv16, typefaceSpannable)
        }


        // 17. 设置超链接
        val urlSpannable = spanUtils.setURLSpan(
            "起始文本 ",
            "超链接文本",
            " 结束文本",
            "https://www.example.com"
        )
        spanUtils.appendSpannableToTextView(binding.tv17, urlSpannable)


        binding.tv1.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv2.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv3.movementMethod = android.text.method.LinkMovementMethod.getInstance()
//        binding.tv4.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv5.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv6.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv7.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv8.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv9.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv10.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv11.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv12.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv13.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv14.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv15.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv16.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv17.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv18.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv19.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }

    }


