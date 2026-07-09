package com.astmirzhan.cityflow.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.astmirzhan.cityflow.model.WalkingRoute
import com.google.android.material.color.MaterialColors

class RoutePreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private data class Node(val number: String, val name: String, val isStart: Boolean)

    private var nodes: List<Node> = emptyList()

    private val primaryColor = MaterialColors.getColor(
        this, com.google.android.material.R.attr.colorPrimary, Color.parseColor("#3D5F94")
    )
    private val accentColor = MaterialColors.getColor(
        this, com.google.android.material.R.attr.colorSecondary, Color.parseColor("#E8743B")
    )
    private val onPrimaryColor = MaterialColors.getColor(
        this, com.google.android.material.R.attr.colorOnPrimary, Color.WHITE
    )
    private val textColor = MaterialColors.getColor(
        this, com.google.android.material.R.attr.colorOnSurface, Color.parseColor("#1B2430")
    )
    private val mutedColor = MaterialColors.getColor(
        this, com.google.android.material.R.attr.colorOnSurfaceVariant, Color.parseColor("#5C6B7E")
    )

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = primaryColor
        strokeWidth = 6f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        alpha = 120
    }
    private val nodePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = primaryColor
        style = Paint.Style.FILL
    }
    private val startPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = accentColor
        style = Paint.Style.FILL
    }
    private val numberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = onPrimaryColor
        textSize = 26f
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }
    private val labelPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        textSize = 24f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }
    private val startLabelPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = mutedColor
        textSize = 24f
        textAlign = Paint.Align.CENTER
    }
    private val emptyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = mutedColor
        textSize = 38f
        textAlign = Paint.Align.CENTER
    }

    fun setRoute(route: WalkingRoute, showStart: Boolean = false) {
        nodes = buildList {
            if (showStart && route.stops.isNotEmpty()) add(Node("", "You", isStart = true))
            route.stops.forEach { add(Node(it.order.toString(), it.place.name, isStart = false)) }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (nodes.isEmpty()) {
            canvas.drawText("No route yet", width / 2f, height / 2f + 12f, emptyPaint)
            return
        }

        val paddingH = 56f
        val usable = width - paddingH * 2
        val lineY = height * 0.42f
        val slot = if (nodes.size > 1) usable / (nodes.size - 1) else 0f
        val xs = nodes.indices.map { paddingH + it * slot }

        if (nodes.size > 1) {
            canvas.drawLine(xs.first(), lineY, xs.last(), lineY, linePaint)
        }

        val labelMaxWidth = if (nodes.size > 1) slot - 8f else usable
        nodes.forEachIndexed { i, node ->
            val x = xs[i]
            if (node.isStart) {
                canvas.drawCircle(x, lineY, 13f, startPaint)
            } else {
                canvas.drawCircle(x, lineY, 22f, nodePaint)
                canvas.drawText(node.number, x, lineY + 9f, numberPaint)
            }
            val paint = if (node.isStart) startLabelPaint else labelPaint
            val (align, labelX) = when (i) {
                0 -> Paint.Align.LEFT to (x - 13f)
                nodes.lastIndex -> Paint.Align.RIGHT to (x + 22f)
                else -> Paint.Align.CENTER to x
            }
            paint.textAlign = align
            val label = TextUtils.ellipsize(node.name, paint, labelMaxWidth, TextUtils.TruncateAt.END).toString()
            canvas.drawText(label, labelX, lineY + 56f, paint)
        }
    }
}
