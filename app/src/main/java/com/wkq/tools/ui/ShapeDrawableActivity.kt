package com.wkq.tools.ui

import DashEdgeTreatment
import android.R.attr.elevation
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.OffsetEdgeTreatment
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.TriangleEdgeTreatment
import com.wkq.tools.databinding.ActivityShapeDrawableBinding
import com.wkq.tools.ui.view.ZigZagEdgeTreatment

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/8 10:57
 *
 *@Desc:
 */
class ShapeDrawableActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, ShapeDrawableActivity::class.java))

        }
    }

    val colors = intArrayOf(
        Color.parseColor("#FF4081"), Color.parseColor("#FF03DAC5"), Color.parseColor("#3F51B5")
    )

    val binding: ActivityShapeDrawableBinding by lazy {
        ActivityShapeDrawableBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btFinish.setOnClickListener {
            finish()
        }

        //圆角

        val shapeModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(CornerFamily.ROUNDED, 30f) // 角度设置
            .setTopRightCorner(CornerFamily.ROUNDED, 30f)
            .setBottomLeftCorner(CornerFamily.ROUNDED, 30f)
            .setBottomRightCorner(CornerFamily.ROUNDED, 30f).build()
        binding.view1.background = MaterialShapeDrawable(shapeModel).apply {
            paintStyle = Paint.Style.FILL // 填充格式是填充内容
            fillColor = ColorStateList.valueOf(Color.parseColor("#FF4081"))
        }
        //圆形
        val shapeModel2 =
            ShapeAppearanceModel.builder().setAllCornerSizes(RelativeCornerSize(0.5f)) // 左上圆角 30dp
                .build()
        binding.view2.background = MaterialShapeDrawable(shapeModel2).apply {
            paintStyle = Paint.Style.FILL
            fillColor = ColorStateList.valueOf(Color.parseColor("#FF4081"))
        }
        // 切角
        val shapeModelCut =
            ShapeAppearanceModel.builder().setTopLeftCorner(CornerFamily.CUT, 30f)   // 左上圆角 30dp
                .setTopRightCorner(CornerFamily.CUT, 20f)      // 右上切角 20dp
                .setBottomLeftCorner(CornerFamily.CUT, 10f)
                .setBottomRightCorner(CornerFamily.CUT, 15f).build()
        binding.view3.background = MaterialShapeDrawable(shapeModelCut).apply {
            paintStyle = Paint.Style.FILL
            fillColor = ColorStateList.valueOf(Color.parseColor("#FF4081"))
        }

        // CornerTreatment （边角处理） RoundedCornerTreatment 标准圆角处理。 CutCornerTreatment ：切角（斜角）处理。 OvalCornerTreatment 椭圆角处理
        // EdgeTreatment （边缘处理）  LineEdgeTreatment：默认直线边缘 MarkerEdgeTreatment：带标记（如三角形）的边缘。 TriangleEdgeTreatment：带锯齿的边缘（多个三角形）。


//        val shapePathModel = ShapeAppearanceModel.builder().setAllCorners()

        // 外三角

        val shapeModel4 =
            ShapeAppearanceModel.builder()
                .setAllCorners(RoundedCornerTreatment())
                .setAllCornerSizes(15f) // 设置圆角
                .setAllEdges(TriangleEdgeTreatment(20f, false))
                .build()
        // 注意需要父布局开启
        (binding.view4.parent as ViewGroup).clipChildren = false
        binding.view4.background = MaterialShapeDrawable(shapeModel4).apply {
            paintStyle = Paint.Style.FILL
            fillColor = ColorStateList.valueOf(Color.parseColor("#FF4081"))
            elevation = 8f                                                    // 设置阴影高度
            initializeElevationOverlay(this@ShapeDrawableActivity)
        }

        // 内三角
        val shapeModel5 =
            ShapeAppearanceModel.builder()
                .setAllCorners(RoundedCornerTreatment())
                .setAllCornerSizes(15f) // 设置圆角
                .setAllEdges(TriangleEdgeTreatment(20f, true))
                .build()

        binding.view5.background = MaterialShapeDrawable(shapeModel5).apply {
            paintStyle = Paint.Style.FILL
            fillColor = ColorStateList.valueOf(Color.parseColor("#FF4081"))
            elevation = 8f                                                    // 设置阴影高度
            initializeElevationOverlay(this@ShapeDrawableActivity)

        }

        // 指定位置外三角
        (binding.view6.parent as ViewGroup).clipChildren = false
        val shapeModel6 =
            ShapeAppearanceModel.builder()
                .setAllCorners(RoundedCornerTreatment())
                .setAllCornerSizes(15f) // 设置圆角
                .setBottomEdge(OffsetEdgeTreatment(TriangleEdgeTreatment(20f, false), 10f))
                .build()

        binding.view6.background = MaterialShapeDrawable(shapeModel6).apply {
            paintStyle = Paint.Style.FILL
            fillColor = ColorStateList.valueOf(Color.parseColor("#FF4081"))
            elevation = 8f                                                    // 设置阴影高度
            initializeElevationOverlay(this@ShapeDrawableActivity)
        }


        // 边框
        val shapeModel7 = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(15f) // 设置圆角
            .setBottomEdge(OffsetEdgeTreatment(TriangleEdgeTreatment(20f, false), 10f))
            .build()
        (binding.view7.parent as ViewGroup).clipChildren = false
        binding.view7.background = MaterialShapeDrawable(shapeModel7).apply {
            paintStyle = Paint.Style.FILL_AND_STROKE
            strokeWidth = 3f
            fillColor =  ColorStateList.valueOf(Color.GREEN)
            strokeColor = ColorStateList.valueOf(Color.parseColor("#FF4081"))
            setTint(Color.GREEN)                                              // 设置阴影高度
            initializeElevationOverlay(this@ShapeDrawableActivity)
        }

        val shapeModel8 = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(15f) // 设置圆角
            .build()
        var d = MaterialShapeDrawable(shapeModel8).apply {
            paintStyle = Paint.Style.FILL_AND_STROKE
            strokeWidth = 3f
            strokeColor = ColorStateList.valueOf(Color.parseColor("#FF4081"))
            setTint(Color.GREEN)                                              // 设置阴影高度
            initializeElevationOverlay(this@ShapeDrawableActivity)

        }
        binding.view8.background = d

        // 阴影效果
        val shapeModel10 = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, 24f)
            .build()

        val materialShapeDrawable = MaterialShapeDrawable(shapeModel10).apply {
            fillColor =  ColorStateList.valueOf(Color.parseColor("#FF4081")) //可以设置各个状态颜色（默认颜色、点击颜色等）
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS //阴影兼容模式
            initializeElevationOverlay(this@ShapeDrawableActivity)
            elevation = 10f// 设置阴影高度
            setShadowColor( Color.GRAY) // 阴影颜色（半透明黑）
        }
        (binding.view10.parent as ViewGroup).clipChildren = false
        binding.view10.apply {
            background = materialShapeDrawable
        }


        // 动态设置
        val shapeModelStart = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, 0f)
            .build()
        val drawable = MaterialShapeDrawable(shapeModelStart).apply {
            fillColor = ColorStateList.valueOf(Color.parseColor("#FF4081"))
            initializeElevationOverlay(this@ShapeDrawableActivity)
        }
        binding.view11.apply {
            background = drawable
            elevation = 0f
            clipToOutline = false
        }

            // 动态圆角和阴影动画
        val animator = ValueAnimator.ofFloat(0f, 48f).apply {
            duration = 2000L
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE

            addUpdateListener { animation ->
                val radius = animation.animatedValue as Float

                // 动态圆角
                drawable.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(CornerFamily.ROUNDED, radius)
                    .build()
                drawable.invalidateSelf()

                // 动态阴影，高度跟圆角同步变化（示例）
                binding.view11.elevation = (radius / 2f)
            }
        }
        animator.start()


    }
    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }


}


