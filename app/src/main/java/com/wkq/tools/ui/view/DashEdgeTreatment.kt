import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

class DashEdgeTreatment(
    private val dashLength: Float,
    private val dashGap: Float
) : EdgeTreatment() {

    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {
        var start = 0f
        shapePath.reset(0f, 0f)  // 起点

        while (start < length) {
            val segment = Math.min(dashLength, length - start)
            shapePath.lineTo(start + segment, 0f)
            start += segment + dashGap
            if (start < length) {
                shapePath.lineTo(start, 0f)  // 重新设置起点，相当于跳过dashGap的空白
            }
        }
    }
}
