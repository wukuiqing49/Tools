package com.wkq.tools.ui.view

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/17 15:54
 *
 *@Desc:
 */
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * 创建分段锯齿状（半圆）边缘
 * @param scallopRadius 每个半圆的半径
 */
class ScallopEdgeTreatment(private val scallopRadius: Float) : EdgeTreatment() {

    override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
        val diameter = scallopRadius * 2
        val count = (length / diameter).toInt()

        var currentX = 0f

        for (i in 0 until count) {
            // 半圆从 currentX 开始，到 currentX + diameter 结束
            shapePath.addArc(
                currentX,
                -scallopRadius,
                currentX + diameter,
                scallopRadius,
                180f,
                -180f  // 往下绘制
            )
            currentX += diameter
        }

        // 补齐最后一点
        if (currentX < length) {
            shapePath.lineTo(length, 0f)
        }
    }
}
