package com.wkq.tools.ui

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SweepGradient
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.wkq.tools.R
import com.wkq.tools.databinding.ActivityColorBinding
import com.wkq.tools.databinding.ActivityColorUiBinding
import com.wkq.tools.ui.view.CircleNumberDrawableUtil
import com.wkq.tools.ui.view.CircleNumberDrawableUtil.GradientOrientation
import com.wkq.tools.ui.view.GradientBorderDrawable
import com.wkq.ui.util.span.SpanUtils
import com.wkq.ui.view.KtLinearGradientFontSpan


/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/8 10:57
 *
 *@Desc:
 */
class ColorUIGradientActivity: AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, ColorUIGradientActivity::class.java))
        }
    }
    var startColor= Color.parseColor("#FF4081")
    var endColor= Color.parseColor("#FF03DAC5")
    val  colors=intArrayOf(Color.parseColor("#FF4081"),Color.parseColor("#FF03DAC5"))

    val binding: ActivityColorUiBinding by lazy {
        ActivityColorUiBinding.inflate(LayoutInflater.from(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btFinish.setOnClickListener {
            finish()
        }
        val span = getGradientSpan("我是文字渐变",startColor,endColor,true)
        binding.tvText.setText(span, TextView.BufferType.SPANNABLE)

        val drawable = GradientBorderDrawable(borderWidth = dp2px(1f).toFloat(), cornerRadius = dp2px(15f).toFloat(), borderColors = colors, backgroundColor = Color.WHITE, orientation = GradientBorderDrawable.Orientation.TOP_BOTTOM)
       binding.tvText3.background = drawable

        binding. arcProgress.setBarBackgroundColor(Color.RED) // 75%
        binding. arcProgress.setProgress(0.75f) // 75%
        binding. arcProgress.setGradientColors(intArrayOf(Color.parseColor("#FF15EB92"), Color.parseColor("#FF0CC1EA")))

        val gradient = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,  colors) // 渐变方向 colors// 颜色数组
        binding.view2.setBackgroundDrawable(gradient)


        var tip =  CircleNumberDrawableUtil.createGradientCircleDrawable(this,
            dp2px(50f).toFloat() ,
            intArrayOf(startColor,endColor),
            GradientOrientation.TOP_BOTTOM,
            "文字",
            16f,
            Color.WHITE)
        binding.view3.setBackgroundDrawable(tip)
    }

    /**
    * content 内容
     * startColor 开始颜色
     * endColor 结束颜色
     * isLeftToRight 是否从左到右
     */
    private fun getGradientSpan(content: String, startColor: Int, endColor: Int, isLeftToRight: Boolean): SpannableStringBuilder {
        val spannableStringBuilder = SpannableStringBuilder(content)
        val span = KtLinearGradientFontSpan(startColor, endColor, isLeftToRight)
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableStringBuilder
    }

    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }
}