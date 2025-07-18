package com.wkq.tools.ui.view

import android.graphics.Path
import com.google.android.material.shape.EdgeTreatment
import kotlin.math.PI
import kotlin.math.sin

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/17 14:13
 *
 *@Desc:
 */
// 自定义波浪边处理
class CustomWaveEdgeTreatment(
    private val amplitude: Float,  // 波浪高度
    private val frequency: Float   // 波浪密度（波长的倒数）
) : EdgeTreatment() {

     fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        path: Path
    ) {
        val effectiveAmplitude = amplitude * interpolation
        val waveLength = length * frequency

        path.lineTo(0f, 0f)

        // 使用 while 循环替代步长浮点数范围
        var x = 0f
        val step = 5f // 采样精度

        while (x <= length) {
            val y = effectiveAmplitude * kotlin.math.sin((x * 2 * Math.PI) / waveLength).toFloat()
            path.lineTo(x, y)
            x += step
        }

        // 确保终点精确落在边缘末尾
        path.lineTo(length, 0f)
    }
}