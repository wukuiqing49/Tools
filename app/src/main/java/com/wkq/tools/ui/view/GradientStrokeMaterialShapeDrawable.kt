import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.shape.*

class MaterialShapeDrawablePathView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val shapeDrawable: MaterialShapeDrawable
    private val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLUE
        strokeWidth = 6f
    }
    private var path: Path? = null

    init {
        val shapeModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, 40f)
            .build()

        shapeDrawable = MaterialShapeDrawable(shapeModel).apply {
            fillColor = ContextCompat.getColorStateList(context, android.R.color.holo_red_light)
            strokeColor = ContextCompat.getColorStateList(context, android.R.color.holo_blue_dark)
            strokeWidth = 6f
            paintStyle = Paint.Style.FILL_AND_STROKE
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        shapeDrawable.setBounds(0, 0, w, h)

        // 获取路径
        path = getMaterialShapeDrawablePath(shapeDrawable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 先绘制背景形状（带填充）
        shapeDrawable.draw(canvas)

        // 再绘制路径边框
        path?.let {
            canvas.drawPath(it, pathPaint)
        }
    }

    private fun getMaterialShapeDrawablePath(drawable: MaterialShapeDrawable): Path? {
        return try {
            val path = Path()
            val bounds = drawable.bounds
            val rectF = RectF(bounds)

            val method = MaterialShapeDrawable::class.java.getDeclaredMethod(
                "calculatePath",
                RectF::class.java,
                Path::class.java
            )
            method.isAccessible = true
            method.invoke(drawable, rectF, path)

            path
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
