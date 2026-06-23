package com.astmirzhan.cityflow.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.astmirzhan.cityflow.model.WalkingRoute

// CustomView that renders a schematic preview of the walking route.
class RoutePreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private data class Point(val x: Double, val y: Double, val label: String, val isStart: Boolean)

    private var points: List<Point> = emptyList()

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#4A6FA5")
        strokeWidth = 8f
        style = Paint.Style.STROKE
    }
    private val nodePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#4A6FA5")
        style = Paint.Style.FILL
    }
    private val startPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#E8743B")
        style = Paint.Style.FILL
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#333333")
        textSize = 32f
    }
    private val emptyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#999999")
        textSize = 38f
        textAlign = Paint.Align.CENTER
    }

    fun setRoute(route: WalkingRoute) {
        val raw = mutableListOf<Triple<Double, Double, String>>()
        raw.add(Triple(route.startLatitude, route.startLongitude, "Start"))
        route.stops.forEach { raw.add(Triple(it.place.latitude, it.place.longitude, "${it.order}. ${it.place.name}")) }
        points = normalize(raw)
        invalidate()
    }

    private fun normalize(raw: List<Triple<Double, Double, String>>): List<Point> {
        if (raw.isEmpty()) return emptyList()
        val minLat = raw.minOf { it.first }
        val maxLat = raw.maxOf { it.first }
        val minLng = raw.minOf { it.second }
        val maxLng = raw.maxOf { it.second }
        val latSpan = (maxLat - minLat).takeIf { it > 0 } ?: 1.0
        val lngSpan = (maxLng - minLng).takeIf { it > 0 } ?: 1.0
        return raw.mapIndexed { index, t ->
            val nx = (t.second - minLng) / lngSpan
            // Invert latitude so north is up.
            val ny = 1.0 - (t.first - minLat) / latSpan
            Point(nx, ny, t.third, index == 0)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = 80f
        val w = width - padding * 2
        val h = height - padding * 2

        if (points.isEmpty()) {
            canvas.drawText(
                "No route yet",
                width / 2f,
                height / 2f,
                emptyPaint
            )
            return
        }

        val screen = points.map {
            Pair((padding + it.x * w).toFloat(), (padding + it.y * h).toFloat())
        }

        for (i in 0 until screen.size - 1) {
            canvas.drawLine(screen[i].first, screen[i].second, screen[i + 1].first, screen[i + 1].second, linePaint)
        }

        points.forEachIndexed { i, point ->
            val (x, y) = screen[i]
            canvas.drawCircle(x, y, if (point.isStart) 18f else 14f, if (point.isStart) startPaint else nodePaint)
            canvas.drawText(point.label, x + 22f, y + 10f, textPaint)
        }
    }
}
