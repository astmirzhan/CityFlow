package com.astmirzhan.cityflow.data

import android.content.Context
import com.astmirzhan.cityflow.model.RouteHistoryItem
import com.astmirzhan.cityflow.model.WalkingRoute
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

// Local persistence for generated routes/history via SharedPreferences.
class RouteHistoryRepository(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveRoute(route: WalkingRoute): RouteHistoryItem {
        val item = RouteHistoryItem(
            id = UUID.randomUUID().toString(),
            createdAt = System.currentTimeMillis(),
            title = buildTitle(route),
            stopCount = route.stops.size,
            totalDistanceMeters = route.totalDistanceMeters,
            totalMinutes = route.totalMinutes,
            placeNames = route.stops.map { it.place.name }
        )
        val items = loadHistory().toMutableList()
        items.add(0, item)
        persist(items)
        return item
    }

    fun loadHistory(): List<RouteHistoryItem> {
        val raw = prefs.getString(KEY_HISTORY, null) ?: return emptyList()
        val array = JSONArray(raw)
        val result = ArrayList<RouteHistoryItem>(array.length())
        for (i in 0 until array.length()) {
            result.add(fromJson(array.getJSONObject(i)))
        }
        return result
    }

    fun clear() {
        prefs.edit().remove(KEY_HISTORY).apply()
    }

    private fun persist(items: List<RouteHistoryItem>) {
        val array = JSONArray()
        items.take(MAX_ITEMS).forEach { array.put(toJson(it)) }
        prefs.edit().putString(KEY_HISTORY, array.toString()).apply()
    }

    private fun buildTitle(route: WalkingRoute): String {
        val first = route.stops.firstOrNull()?.place?.name ?: "Empty route"
        return "$first +${(route.stops.size - 1).coerceAtLeast(0)} stops"
    }

    private fun toJson(item: RouteHistoryItem): JSONObject = JSONObject().apply {
        put("id", item.id)
        put("createdAt", item.createdAt)
        put("title", item.title)
        put("stopCount", item.stopCount)
        put("totalDistanceMeters", item.totalDistanceMeters)
        put("totalMinutes", item.totalMinutes)
        put("placeNames", JSONArray(item.placeNames))
    }

    private fun fromJson(json: JSONObject): RouteHistoryItem {
        val names = json.optJSONArray("placeNames") ?: JSONArray()
        val list = ArrayList<String>(names.length())
        for (i in 0 until names.length()) list.add(names.getString(i))
        return RouteHistoryItem(
            id = json.getString("id"),
            createdAt = json.getLong("createdAt"),
            title = json.getString("title"),
            stopCount = json.getInt("stopCount"),
            totalDistanceMeters = json.getDouble("totalDistanceMeters"),
            totalMinutes = json.getInt("totalMinutes"),
            placeNames = list
        )
    }

    companion object {
        private const val PREFS_NAME = "cityflow_history"
        private const val KEY_HISTORY = "routes"
        private const val MAX_ITEMS = 30
    }
}
