package com.astmirzhan.cityflow

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.astmirzhan.cityflow.data.PlaceRepository
import com.astmirzhan.cityflow.model.IndoorPreference
import com.astmirzhan.cityflow.model.PlaceCategory
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var root: View
    private lateinit var nameInput: EditText
    private lateinit var minutesSeek: SeekBar
    private lateinit var minutesLabel: TextView
    private lateinit var categoryGroup: ChipGroup
    private lateinit var indoorSpinner: Spinner
    private lateinit var progress: ProgressBar

    private val prefs by lazy { getSharedPreferences(PREFS, Context.MODE_PRIVATE) }

    private val routeLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val saved = result.data?.getStringExtra(RouteResultActivity.EXTRA_SAVED_TITLE)
        if (result.resultCode == RESULT_OK && saved != null) {
            Snackbar.make(root, getString(R.string.route_saved, saved), Snackbar.LENGTH_LONG).show()
        }
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        launchRoute(useGps = granted)
        if (!granted) {
            Snackbar.make(root, getString(R.string.location_fallback), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root = findViewById(R.id.main)
        nameInput = findViewById(R.id.routeNameInput)
        minutesSeek = findViewById(R.id.minutesSeek)
        minutesLabel = findViewById(R.id.minutesLabel)
        categoryGroup = findViewById(R.id.categoryChips)
        indoorSpinner = findViewById(R.id.indoorSpinner)
        progress = findViewById(R.id.mainProgress)

        setupCategories()
        setupSpinner()
        setupSeek()
        restorePrefs()

        findViewById<Button>(R.id.buildButton).setOnClickListener { onBuildClicked() }
        findViewById<Button>(R.id.placesButton).setOnClickListener {
            startActivity(Intent(this, PlacesActivity::class.java))
        }
        findViewById<Button>(R.id.historyButton).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun setupCategories() {
        PlaceCategory.entries.forEach { category ->
            val chip = Chip(this).apply {
                text = category.label
                isCheckable = true
                tag = category.name
            }
            categoryGroup.addView(chip)
        }
    }

    private fun setupSpinner() {
        val labels = listOf("Any setting", "Indoor only", "Outdoor only")
        indoorSpinner.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, labels
        )
    }

    private fun setupSeek() {
        minutesSeek.max = (MAX_MINUTES - MIN_MINUTES) / STEP_MINUTES
        minutesSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                minutesLabel.text = getString(R.string.minutes_value, selectedMinutes())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun selectedMinutes(): Int = MIN_MINUTES + minutesSeek.progress * STEP_MINUTES

    private fun selectedCategories(): Set<PlaceCategory> {
        val result = mutableSetOf<PlaceCategory>()
        for (i in 0 until categoryGroup.childCount) {
            val chip = categoryGroup.getChildAt(i) as Chip
            if (chip.isChecked) PlaceCategory.fromName(chip.tag as String)?.let { result.add(it) }
        }
        return result
    }

    private fun selectedIndoorPreference(): IndoorPreference = when (indoorSpinner.selectedItemPosition) {
        1 -> IndoorPreference.INDOOR
        2 -> IndoorPreference.OUTDOOR
        else -> IndoorPreference.ANY
    }

    private fun onBuildClicked() {
        savePrefs()
        val useGps = findViewById<com.google.android.material.materialswitch.MaterialSwitch>(R.id.gpsSwitch).isChecked
        if (useGps && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            launchRoute(useGps)
        }
    }

    private fun launchRoute(useGps: Boolean) {
        progress.visibility = View.VISIBLE
        val location = if (useGps) lastKnownLocation() else null
        val lat = location?.first ?: PlaceRepository.CITY_CENTER_LAT
        val lng = location?.second ?: PlaceRepository.CITY_CENTER_LNG

        val intent = Intent(this, RouteResultActivity::class.java).apply {
            putExtra(RouteResultActivity.EXTRA_MINUTES, selectedMinutes())
            putExtra(RouteResultActivity.EXTRA_CATEGORIES, selectedCategories().map { it.name }.toTypedArray())
            putExtra(RouteResultActivity.EXTRA_INDOOR, selectedIndoorPreference().name)
            putExtra(RouteResultActivity.EXTRA_LAT, lat)
            putExtra(RouteResultActivity.EXTRA_LNG, lng)
            putExtra(RouteResultActivity.EXTRA_FROM_GPS, location != null)
            putExtra(RouteResultActivity.EXTRA_NAME, nameInput.text.toString().trim())
        }
        progress.visibility = View.GONE
        routeLauncher.launch(intent)
    }

    private fun lastKnownLocation(): Pair<Double, Double>? {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) return null
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
        for (provider in providers) {
            try {
                val loc = manager.getLastKnownLocation(provider) ?: continue
                return loc.latitude to loc.longitude
            } catch (_: SecurityException) {
                return null
            }
        }
        return null
    }

    private fun savePrefs() {
        prefs.edit()
            .putInt(KEY_MINUTES, selectedMinutes())
            .putInt(KEY_INDOOR, indoorSpinner.selectedItemPosition)
            .putStringSet(KEY_CATEGORIES, selectedCategories().map { it.name }.toSet())
            .putString(KEY_NAME, nameInput.text.toString().trim())
            .apply()
    }

    private fun restorePrefs() {
        val minutes = prefs.getInt(KEY_MINUTES, 90)
        minutesSeek.progress = ((minutes - MIN_MINUTES) / STEP_MINUTES).coerceIn(0, minutesSeek.max)
        minutesLabel.text = getString(R.string.minutes_value, selectedMinutes())
        indoorSpinner.setSelection(prefs.getInt(KEY_INDOOR, 0))
        nameInput.setText(prefs.getString(KEY_NAME, ""))
        val saved = prefs.getStringSet(KEY_CATEGORIES, emptySet()) ?: emptySet()
        for (i in 0 until categoryGroup.childCount) {
            val chip = categoryGroup.getChildAt(i) as Chip
            chip.isChecked = saved.contains(chip.tag as String)
        }
    }

    companion object {
        private const val PREFS = "cityflow_prefs"
        private const val KEY_MINUTES = "minutes"
        private const val KEY_INDOOR = "indoor"
        private const val KEY_CATEGORIES = "categories"
        private const val KEY_NAME = "name"

        private const val MIN_MINUTES = 30
        private const val MAX_MINUTES = 240
        private const val STEP_MINUTES = 15
    }
}
