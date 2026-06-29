package com.astmirzhan.cityflow.data

import android.content.Context
import com.astmirzhan.cityflow.model.City

// Persists the user's selected city id in SharedPreferences.
class SelectedCityRepository(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun saveSelectedCity(cityId: String) {
        prefs.edit().putString(KEY_CITY_ID, cityId).apply()
    }

    fun getSelectedCityId(): String? = prefs.getString(KEY_CITY_ID, null)

    fun getSelectedCity(): City? {
        val cityId = getSelectedCityId() ?: return null
        return CityRepository.getCityById(cityId)
    }

    fun clearSelectedCity() {
        prefs.edit().remove(KEY_CITY_ID).apply()
    }

    companion object {
        private const val PREFS = "cityflow_selected_city"
        private const val KEY_CITY_ID = "selected_city_id"
    }
}
