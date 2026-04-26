package dam.pmdm.spyrothedragon.ui

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class RiptoCetroEffectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        maskFilter = BlurMaskFilter(35f, BlurMaskFilter.Blur.NORMAL)
    }

    private val ringPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }

    private val sparkPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        strokeCap = Paint.Cap.ROUND
        color = Color.WHITE
    }

    private val fondoPaint = Paint().apply {
        color = Color.argb(80, 0, 0, 0)
    }

    private val startTime = System.currentTimeMillis()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), fondoPaint)

        val t = (System.currentTimeMillis() - startTime) / 1000f
        val pulso = ((kotlin.math.sin(t * 3f) + 1f) / 2f)

        val centroX = width * 0.11f
        val centroY = height * 0.80f

        val colorActual = when (((t * 2).toInt()) % 3) {
            0 -> Color.CYAN
            1 -> Color.MAGENTA
            else -> Color.YELLOW
        }

        glowPaint.color = colorActual
        glowPaint.alpha = (120 + 100 * pulso).toInt()
        canvas.drawCircle(centroX, centroY, 18f + 20f * pulso, glowPaint)
        canvas.drawCircle(centroX, centroY, 8f + 8f * pulso, glowPaint)

        ringPaint.color = colorActual

        val radio1 = 28f + ((t * 70f) % 80f)
        val radio2 = 18f + (((t + 0.5f) * 70f) % 80f)

        ringPaint.alpha = (150 - radio1).toInt().coerceAtLeast(0)
        canvas.drawCircle(centroX, centroY, radio1, ringPaint)

        ringPaint.alpha = (150 - radio2).toInt().coerceAtLeast(0)
        canvas.drawCircle(centroX, centroY, radio2, ringPaint)

        sparkPaint.alpha = (120 + 100 * pulso).toInt()
        for (i in 0 until 6) {
            val angulo = i * (Math.PI / 3).toFloat() + t
            val x1 = centroX + cos(angulo) * 18f
            val y1 = centroY + sin(angulo) * 18f
            val x2 = centroX + cos(angulo) * (34f + 10f * pulso)
            val y2 = centroY + sin(angulo) * (34f + 10f * pulso)
            canvas.drawLine(x1, y1, x2, y2, sparkPaint)
        }

        postInvalidateOnAnimation()
    }
}