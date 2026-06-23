package com.astmirzhan.cityflow.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astmirzhan.cityflow.R
import com.astmirzhan.cityflow.model.RouteStop

class RouteStopAdapter(
    private val stops: List<RouteStop>
) : RecyclerView.Adapter<RouteStopAdapter.StopViewHolder>() {

    class StopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val order: TextView = view.findViewById(R.id.stopOrder)
        val name: TextView = view.findViewById(R.id.stopName)
        val meta: TextView = view.findViewById(R.id.stopMeta)
        val description: TextView = view.findViewById(R.id.stopDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_route_stop, parent, false)
        return StopViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        val stop = stops[position]
        holder.order.text = stop.order.toString()
        holder.name.text = stop.place.name
        holder.meta.text = holder.itemView.context.getString(
            R.string.stop_meta,
            stop.place.category.label,
            stop.walkMinutesFromPrevious,
            stop.place.visitMinutes
        )
        holder.description.text = stop.place.description
    }

    override fun getItemCount(): Int = stops.size
}
