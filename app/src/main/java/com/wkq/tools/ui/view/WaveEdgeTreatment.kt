import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * 自定义波浪边缘处理类，用于绘制边缘上的波浪线。
 * @param waveLength 单个波浪的宽度
 * @param waveHeight 波峰的高度
 */
class WaveEdgeTreatment(
    private val waveLength: Float,
    private val waveHeight: Float
) : EdgeTreatment() {

    /**
     * MaterialShapeDrawable 会调用此方法绘制边缘
     * @param length 边的长度（不包含圆角所占的部分）
     */
    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {
        var currentX = 0f
        var waveToggle = true

        // 循环绘制多个波浪
        while (currentX < length) {
            val nextX = currentX + waveLength / 2
            val controlY = if (waveToggle) waveHeight else -waveHeight
            val midX = currentX + waveLength / 4

            shapePath.quadToPoint(midX, controlY, nextX, 0f)

            currentX = nextX
            waveToggle = !waveToggle
        }

        // 如果最后一段没满，补齐
        if (currentX < length) {
            shapePath.lineTo(length, 0f)
        }
    }
}
