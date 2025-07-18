import android.graphics.*
import android.graphics.drawable.Drawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.ShapeAppearancePathProvider

class GradientShapeDrawable(
    private val shapeAppearanceModel: ShapeAppearanceModel
) : Drawable() {

    var fillGradientColors: IntArray? = null
    var strokeGradientColors: IntArray? = null
    var strokeWidth: Float = 0f

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val path = Path()
    private val strokePath = Path()

    private val pathProvider = ShapeAppearancePathProvider()

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        if (bounds.width() <= 0 || bounds.height() <= 0) return

        updatePaths(bounds)

        fillGradientColors?.let {
            fillPaint.shader = LinearGradient(
                0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(),
                it, null, Shader.TileMode.CLAMP
            )
        } ?: run {
            fillPaint.shader = null
            fillPaint.color = Color.TRANSPARENT
        }

        strokeGradientColors?.let {
            strokePaint.shader = LinearGradient(
                0f, 0f, bounds.width().toFloat(), 0f,
                it, null, Shader.TileMode.CLAMP
            )
        } ?: run {
            strokePaint.shader = null
            strokePaint.color = Color.TRANSPARENT
        }
        strokePaint.strokeWidth = strokeWidth

        canvas.drawPath(path, fillPaint)

        if (strokeWidth > 0) {
            canvas.drawPath(strokePath, strokePaint)
        }
    }

    private fun updatePaths(bounds: Rect) {
        path.reset()
        strokePath.reset()

        val rectF = RectF(bounds)
        // 填充路径
        pathProvider.calculatePath(shapeAppearanceModel, 1f, rectF, path)

        // 边框路径，缩进半个描边宽度，防止边框被裁剪
        val halfStroke = strokeWidth / 2f
        val insetRect = RectF(
            rectF.left + halfStroke,
            rectF.top + halfStroke,
            rectF.right - halfStroke,
            rectF.bottom - halfStroke
        )
        pathProvider.calculatePath(shapeAppearanceModel, 1f, insetRect, strokePath)
    }

    override fun setAlpha(alpha: Int) {
        fillPaint.alpha = alpha
        strokePaint.alpha = alpha
        invalidateSelf()
    }

    override fun getAlpha(): Int = fillPaint.alpha

    override fun setColorFilter(colorFilter: ColorFilter?) {
        fillPaint.colorFilter = colorFilter
        strokePaint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}
