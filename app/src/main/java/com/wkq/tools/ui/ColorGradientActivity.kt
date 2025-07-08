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
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.wkq.tools.databinding.ActivityColorBinding


/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/8 10:57
 *
 *@Desc:
 */
class ColorGradientActivity:AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, ColorGradientActivity::class.java))

        }
    }

    val  colors=intArrayOf(Color.parseColor("#FF4081"),Color.parseColor("#FF03DAC5"),Color.parseColor("#3F51B5"))

    val binding: ActivityColorBinding by lazy {
        ActivityColorBinding.inflate(LayoutInflater.from(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btFinish.setOnClickListener {
            finish()
        }

        val gradient = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,  // 渐变方向
            colors// 颜色数组
        )
        binding.view1.setBackgroundDrawable(gradient)


        var gradient2 = GradientDrawable()
        gradient2.setGradientType(GradientDrawable.RADIAL_GRADIENT)
        gradient2.setColors(colors) // 从中心到边缘的颜色
        gradient2.setGradientRadius(dp2px(70f).toFloat()) // 渐变半径（必须设置）
        gradient2.setGradientCenter(0.5f ,0.5f )// 中心点 X 坐标（0-1）
        binding.view2.setBackgroundDrawable(gradient2)

        val gradient3 = GradientDrawable()
        gradient3.setGradientType(GradientDrawable.SWEEP_GRADIENT)
        gradient3.setColors(colors) // 颜色需首尾闭合
        gradient3.setGradientCenter(0.5f ,0.5f )// 中心点 X 坐标（0-1）
        binding.view3.setBackgroundDrawable(gradient3)





    }

    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }
}