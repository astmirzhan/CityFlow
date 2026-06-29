package com.astmirzhan.cityflow

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astmirzhan.cityflow.data.PlaceRepository
import com.astmirzhan.cityflow.data.RouteHistoryRepository
import com.astmirzhan.cityflow.data.SelectedCityRepository
import com.astmirzhan.cityflow.domain.RoutePlanner
import com.astmirzhan.cityflow.domain.SensorContextAdvisor
import com.astmirzhan.cityflow.model.City
import com.astmirzhan.cityflow.model.IndoorPreference
import com.astmirzhan.cityflow.model.PlaceCategory
import com.astmirzhan.cityflow.model.RoutePreferences
import com.astmirzhan.cityflow.model.WalkingRoute
import com.astmirzhan.cityflow.ui.RouteStopAdapter
import com.astmirzhan.cityflow.view.RoutePreviewView
import com.google.android.material.snackbar.Snackbar

class RouteResultActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var preview: RoutePreviewView
    private lateinit var summary: TextView
    private lateinit var motionStatus: TextView
    private lateinit var lightStatus: TextView
    private lateinit var stopsList: RecyclerView
    private lateinit var emptyLabel: TextView

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lightSensor: Sensor? = null

    private lateinit var basePreferences: RoutePreferences
    private lateinit var selectedCity: City
    private var startLat = PlaceRepository.CITY_CENTER_LAT
    private var startLng = PlaceRepository.CITY_CENTER_LNG
    private var currentLux = SensorContextAdvisor.OUTDOOR_LUX_THRESHOLD
    private var lastShakeAt = 0L

    private var route: WalkingRoute? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val city = SelectedCityRepository(this).getSelectedCity()
        if (city == null) {
            startActivity(Intent(this, CitySelectionActivity::class.java))
            finish()
            return
        }
        selectedCity = city

        setContentView(R.layout.activity_route_result)

        preview = findViewById(R.id.routePreview)
        summary = findViewById(R.id.routeSummary)
        motionStatus = findViewById(R.id.motionStatus)
        lightStatus = findViewById(R.id.lightStatus)
        stopsList = findViewById(R.id.stopsList)
        emptyLabel = findViewById(R.id.emptyLabel)
        stopsList.layoutManager = LinearLayoutManager(this)

        readIntent()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        findViewById<Button>(R.id.rebuildButton).setOnClickListener { rebuild() }
        findViewById<Button>(R.id.saveButton).setOnClickListener { saveRoute() }

        buildRoute(firstStopChoices = 1)
    }

    private fun readIntent() {
        val minutes = intent.getIntExtra(EXTRA_MINUTES, 90)
        val categoryNames = intent.getStringArrayExtra(EXTRA_CATEGORIES) ?: emptyArray()
        val categories = categoryNames.mapNotNull { PlaceCategory.fromName(it) }.toSet()
        val indoor = IndoorPreference.valueOf(intent.getStringExtra(EXTRA_INDOOR) ?: IndoorPreference.ANY.name)
        startLat = intent.getDoubleExtra(EXTRA_LAT, PlaceRepository.CITY_CENTER_LAT)
        startLng = intent.getDoubleExtra(EXTRA_LNG, PlaceRepository.CITY_CENTER_LNG)
        basePreferences = RoutePreferences(minutes, categories, indoor)

        val fromGps = intent.getBooleanExtra(EXTRA_FROM_GPS, false)
        val source = if (fromGps) getString(R.string.source_gps) else getString(R.string.source_fallback)
        title = getString(R.string.route_result_title)
        findViewById<TextView>(R.id.sourceLabel).text = getString(R.string.start_source, source)
    }

    private fun effectivePreferences(): RoutePreferences {
        val refined = SensorContextAdvisor.refinePreference(basePreferences.indoorPreference, currentLux)
        return basePreferences.copy(indoorPreference = refined)
    }

    private fun buildRoute(firstStopChoices: Int) {
        val built = RoutePlanner.build(
            startLat = startLat,
            startLng = startLng,
            places = PlaceRepository.getPlacesForCity(selectedCity.id),
            preferences = effectivePreferences(),
            firstStopChoices = firstStopChoices
        )
        route = built
        renderRoute(built)
    }

    private fun rebuild() {
        buildRoute(firstStopChoices = 3)
        Snackbar.make(preview, getString(R.string.route_rebuilt), Snackbar.LENGTH_SHORT).show()
    }

    private fun renderRoute(built: WalkingRoute) {
        preview.setRoute(built)
        stopsList.adapter = RouteStopAdapter(built.stops)
        if (built.isEmpty) {
            emptyLabel.visibility = View.VISIBLE
            stopsList.visibility = View.GONE
        } else {
            emptyLabel.visibility = View.GONE
            stopsList.visibility = View.VISIBLE
        }
        summary.text = getString(
            R.string.route_summary_city,
            selectedCity.name,
            built.stops.size,
            built.totalDistanceMeters / 1000.0,
            built.totalMinutes,
            effectivePreferences().indoorPreference.name.lowercase()
        )
    }

    private fun saveRoute() {
        val current = route
        if (current == null || current.isEmpty) {
            Snackbar.make(preview, getString(R.string.nothing_to_save), Snackbar.LENGTH_SHORT).show()
            return
        }
        val item = RouteHistoryRepository(this).saveRoute(current)
        val data = Intent().putExtra(EXTRA_SAVED_TITLE, item.title)
        setResult(RESULT_OK, data)
        Snackbar.make(preview, getString(R.string.route_saved, item.title), Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        lightSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> handleAccelerometer(event)
            Sensor.TYPE_LIGHT -> handleLight(event.values[0])
        }
    }

    private fun handleAccelerometer(event: SensorEvent) {
        val magnitude = SensorContextAdvisor.linearMagnitude(
            event.values[0], event.values[1], event.values[2]
        )
        val state = SensorContextAdvisor.motionState(magnitude)
        motionStatus.text = getString(R.string.motion_status, state.name.lowercase())

        if (SensorContextAdvisor.isShake(magnitude)) {
            val now = System.currentTimeMillis()
            if (now - lastShakeAt > SHAKE_COOLDOWN_MS) {
                lastShakeAt = now
                rebuild()
            }
        }
    }

    private fun handleLight(lux: Float) {
        currentLux = lux
        lightStatus.text = getString(
            R.string.light_status, lux.toInt(), SensorContextAdvisor.lightLabel(lux)
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    companion object {
        const val EXTRA_MINUTES = "extra_minutes"
        const val EXTRA_CATEGORIES = "extra_categories"
        const val EXTRA_INDOOR = "extra_indoor"
        const val EXTRA_LAT = "extra_lat"
        const val EXTRA_LNG = "extra_lng"
        const val EXTRA_FROM_GPS = "extra_from_gps"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_CITY_ID = "extra_city_id"
        const val EXTRA_SAVED_TITLE = "extra_saved_title"

        private const val SHAKE_COOLDOWN_MS = 1500L
    }
}
