package com.astmirzhan.cityflow.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astmirzhan.cityflow.R
import com.astmirzhan.cityflow.model.RouteHistoryItem
import java.text.DateFormat
import java.util.Date

class RouteHistoryAdapter(
    private val items: List<RouteHistoryItem>
) : RecyclerView.Adapter<RouteHistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.historyTitle)
        val meta: TextView = view.findViewById(R.id.historyMeta)
        val places: TextView = view.findViewById(R.id.historyPlaces)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.title.text = item.title
        val date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
            .format(Date(item.createdAt))
        holder.meta.text = context.getString(
            R.string.history_meta,
            item.stopCount,
            (item.totalDistanceMeters / 1000.0),
            item.totalMinutes,
            date
        )
        holder.places.text = item.placeNames.joinToString(" → ")
    }

    override fun getItemCount(): Int = items.size
}
