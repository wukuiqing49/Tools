package com.wkq.tools.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wkq.tools.R
import com.wkq.tools.databinding.ActivityUiTextviewBinding
import com.wkq.ui.util.WebViewUtil
import com.wkq.ui.util.span.CustomClickableSpan
import com.wkq.ui.util.span.SpanBuilder
import com.wkq.ui.view.KtLinearGradientFontSpan

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/2/11 15:10
 *
 *@Desc:
 */
class TextViewUIActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, TextViewUIActivity::class.java))
        }
    }

    val binding: ActivityUiTextviewBinding by lazy {
        ActivityUiTextviewBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btFinish.setOnClickListener {
            finish()
        }
        val typeface = ResourcesCompat.getFont(this, R.font.alimama_shu_hei_ti_bold);
        binding.tv2.setTypeface(typeface)

        binding.tv3.setTypeface(typeface)
        binding.tv3.setText(
            getGradientSpan(
                "修改字体渐变色", getColor(R.color.color_start), getColor(R.color.color_end),
                false
            ), TextView.BufferType.SPANNABLE
        )

        binding.tv4.text="展示中横线"
        binding.tv4.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG)

        binding.tv5.text="展示下横线"
        binding.tv5.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG)

        binding.tv6.setEllipsize(TextUtils.TruncateAt.MARQUEE)
        //一次
//        binding.tv6.setMarqueeRepeatLimit(1)
        binding.tv6.setMarqueeRepeatLimit(-1)
        binding.tv6.setFocused(true)
        setSpan()

        setWeb();
    }

    private fun setWeb() {
        val picUrl="http://n.sinaimg.cn/ent/4_img/upload/1f0ce517/160/w1024h1536/20210413/f3cc-knqqqmv1022303.jpg"

// 生成包含图片的 <p> 标签字符串
        val pTagString = "<p>这是一个包含图片的段落：<img src='$picUrl' alt='示例图片' style='width: 200px; height: 150px; object-fit: cover ;'> 图片展示结束。</p>"

        WebViewUtil.setPtoHtmlData(pTagString,binding.web,22,object : WebViewUtil.WebEleClickListener {
            override fun onImageClick(src: String?, pos: Int, mImageUrls: Array<out String>?) {
                Toast.makeText(this@TextViewUIActivity,"点击了图片",Toast.LENGTH_SHORT).show()
            }
        } )
    }

    private fun getGradientSpan(content: String, startColor: Int, endColor: Int, isLeftToRight: Boolean): SpannableStringBuilder {
        val spannableStringBuilder = SpannableStringBuilder(content)
        val span = KtLinearGradientFontSpan(startColor, endColor, isLeftToRight)
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 若有需要可以在这里用SpanString系列的其他类，给文本添加下划线、超链接、删除线...等等效果
        return spannableStringBuilder
    }

    fun setSpan(){
        // 创建 SpanBuilder 实例
        val  spanBuilder = SpanBuilder()

        // 添加普通文字
        spanBuilder.appendText("这是一段普通文字，")

        // 添加变色文字并设置点击事件
        val coloredTextStart = spanBuilder.spannableStringBuilder.length
        spanBuilder.appendText("这里文字变色")
        val coloredTextEnd = spanBuilder.spannableStringBuilder.length
        val textColor = resources.getColor(android.R.color.holo_red_dark)
        spanBuilder.addCustomClickableSpan(
            textColor,
            0,
            null,
            coloredTextStart,
            coloredTextEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanBuilder.appendText("，")

        // 添加背景色文字并设置点击事件
        val bgColoredTextStart = spanBuilder.spannableStringBuilder.length
        spanBuilder.appendText("这里有背景色")
        val bgColoredTextEnd = spanBuilder.spannableStringBuilder.length
        val backgroundColor = resources.getColor(android.R.color.holo_red_light)
        spanBuilder.addCustomClickableSpan(
            0,
            backgroundColor,
            null,
            bgColoredTextStart,
            bgColoredTextEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanBuilder.appendText("，")

        // 添加图片文字并设置点击事件
        val imageTextStart = spanBuilder.spannableStringBuilder.length
        spanBuilder.appendText("这里有图片")
        val imageTextEnd = spanBuilder.spannableStringBuilder.length
        val drawable: Drawable = resources.getDrawable(android.R.drawable.btn_star_big_on)
        val backgroundColor2 = resources.getColor(android.R.color.holo_red_light)
        spanBuilder.addCustomClickableSpan(
            0,
            backgroundColor2,
            drawable,
            imageTextStart,
            imageTextEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,object :CustomClickableSpan.OnClickListener{
                override fun onClick(widget: View) {
                    Toast.makeText(this@TextViewUIActivity,"点击了图片",Toast.LENGTH_SHORT).show()
                }

            }
        )

        spanBuilder.appendText("，")

        // 添加粗体文字
        val boldTextStart = spanBuilder.spannableStringBuilder.length
        spanBuilder.appendText("这里是粗体")
        val boldTextEnd = spanBuilder.spannableStringBuilder.length
        spanBuilder.addStyleSpan(android.graphics.Typeface.BOLD, boldTextStart, boldTextEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spanBuilder.appendText("，")

        // 添加斜体文字
        val italicTextStart = spanBuilder.spannableStringBuilder.length
        spanBuilder.appendText("这里是斜体")
        val italicTextEnd = spanBuilder.spannableStringBuilder.length
        spanBuilder.addStyleSpan(android.graphics.Typeface.ITALIC, italicTextStart, italicTextEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spanBuilder.appendText("，")

        // 添加下划线文字
        val underlinedTextStart = spanBuilder.spannableStringBuilder.length
        spanBuilder.appendText("这里有下划线")
        val underlinedTextEnd = spanBuilder.spannableStringBuilder.length
        spanBuilder.addUnderlineSpan(underlinedTextStart, underlinedTextEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 添加下划线文字
        val indexStart = spanBuilder.spannableStringBuilder.length
        spanBuilder.appendText("  这里有中划线")
        val indexEnd = spanBuilder.spannableStringBuilder.length
        spanBuilder.withStrikethroughSpan(indexStart, indexEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 构建 SpannableStringBuilder
        val spannableStringBuilder = spanBuilder.build()

        // 设置到 TextView
        binding.tv7.text = spannableStringBuilder
        binding.tv7.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.tv7.highlightColor = Color.TRANSPARENT
    }
}