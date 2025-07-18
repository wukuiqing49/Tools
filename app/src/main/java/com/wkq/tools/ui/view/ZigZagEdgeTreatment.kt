package com.wkq.tools.ui.view

/**
 *
 *@Author: wkq
 *
 *@Time: 2025/7/17 15:57
 *
 *@Desc:
 */
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * 自定义锯齿形边缘处理
 * @param segmentWidth 每个锯齿的宽度
 * @param segmentHeight 锯齿的高度
 */
class ZigZagEdgeTreatment(
    private val segmentWidth: Float,
    private val segmentHeight: Float
) : EdgeTreatment() {

    override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
        var currentX = 0f
        var goingDown = true

        while (currentX < length) {
            val nextX = currentX + segmentWidth
            val y = if (goingDown) segmentHeight else 0f
            shapePath.lineTo(nextX, y)
            currentX = nextX
            goingDown = !goingDown
        }

        if (currentX < length) {
            shapePath.lineTo(length, 0f)  // 补齐终点
        }
    }
}
