package com.astmirzhan.cityflow.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astmirzhan.cityflow.R
import com.astmirzhan.cityflow.model.Place

class PlaceAdapter(
    private val places: List<Place>
) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.placeName)
        val category: TextView = view.findViewById(R.id.placeCategory)
        val description: TextView = view.findViewById(R.id.placeDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.name.text = place.name
        val setting = if (place.indoor) "Indoor" else "Outdoor"
        holder.category.text = holder.itemView.context.getString(
            R.string.place_category, place.category.label, setting
        )
        holder.description.text = place.description
    }

    override fun getItemCount(): Int = places.size
}
