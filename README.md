# CityFlow — Smart Walking Route Planner

## Overview
**CityFlow** is an offline walking-route assistant. The user picks a city, sets how much time they have and what they are interested in, and the app builds a logical walking route through the city's best spots that fits the time budget. Everything — the places dataset, the route calculation and the history — lives on the device: no internet connection is used or required (the manifest requests no `INTERNET` permission).

## Project Idea
Unlike travel apps that merely list points of interest, CityFlow solves the "what should I do for the next 2 hours?" problem: it automatically constructs a sequence of stops that fits the user's schedule. The offline-first design means privacy, instant results and full functionality without any data connectivity.

## Key Features
*   **City Onboarding:** A dedicated first-launch flow for picking a country and city (5 cities: Schmalkalden, Berlin, Munich, Astana, Almaty).
*   **Time-Based Planning:** Choose 30–240 minutes; the planner fills the budget efficiently.
*   **Interest Filtering:** Category chips (Museums, Parks, Cafes, …) — only categories that actually exist in the selected city are offered, so a filter can never produce an empty route.
*   **Environment Preference:** Indoor, Outdoor, or Any.
*   **GPS Start (optional):** With GPS enabled, the user's real position is used as the start of the walk — the nearest place is chosen first **and the walking time to it is counted**. Without GPS the walk simply begins at stop 1.
*   **Route Timeline:** A custom-drawn `RoutePreviewView` shows the route as a clean numbered timeline; with a GPS start an extra "You" node marks the real starting point.
*   **Sensor Context:** Shake to rebuild the route; ambient light produces an indoor/outdoor suggestion.
*   **History:** Save routes (with an optional custom name) and browse them later.

## Screens (5 Activities)
*   **CitySelectionActivity:** Onboarding — country and city selection, stored persistently.
*   **MainActivity:** The control center — route name, time budget, interests, indoor/outdoor, GPS toggle.
*   **RouteResultActivity:** The generated route — timeline preview, summary, sensor status, rebuild/save. Returns the saved route's title back to MainActivity via `setResult`.
*   **PlacesActivity:** The full curated place list for the selected city.
*   **HistoryActivity:** Saved routes with clear-history support.

All child screens include an explicit Back button in addition to system back navigation.

## How the Route Planner Works
The core logic lives in the `RoutePlanner` domain object:
1.  **Filtering:** Candidates are filtered by selected categories and indoor/outdoor preference.
2.  **Distance:** The **Haversine formula** computes distances between coordinates (`DistanceCalculator`).
3.  **Time model:** Walking speed **5 km/h** plus each place's predefined `visitMinutes`.
4.  **Greedy nearest-neighbour:** The next stop is always the closest remaining candidate that still fits into the time budget.
5.  **Start semantics:** Without GPS, the first selected place *is* the start of the walk (its approach time is 0). With a GPS start the first leg — from the user's position to the first stop — is real and is included in the budget.
6.  **Rebuild variety:** "Rebuild" (button or shake) picks the first stop randomly among the 3 nearest candidates, producing route variations.

## Sensors Used (3)
*   **Accelerometer:** Motion state display; a shake gesture rebuilds the route.
*   **Light Sensor:** Shows ambient lux and, when the preference is "Any", suggests indoor/outdoor ("try indoor") — a hint only, it never overrides the user's explicit choice.
*   **GPS / Location (`LocationManager`):** Optional start point. A sanity check rejects fixes further than 30 km from the selected city (e.g. the emulator's default Googleplex location) and falls back to the city centre with a notice.

## Local Places Dataset
A curated offline dataset (`CityRepository`, `PlaceRepository`): 5 cities, each with 6–12 real places — precise coordinates, category, indoor/outdoor flag, average visit duration and a short description.

## Data Storage
*   **Selected city & form state:** `SharedPreferences` (restored between launches; category filters reset on city change).
*   **Route history:** Routes serialized to JSON in `SharedPreferences` (up to 30 items); corrupt data is discarded safely instead of crashing.

## Design
*   Material 3 theme with a vibrant indigo→violet→pink gradient brand, orange accent.
*   Full **light and dark mode** support via semantic theme tokens; the custom view resolves its colors from the theme at runtime.
*   Vector icons only, card-based layout, 44dp+ touch targets.

## Tech Stack
*   **Language:** Kotlin
*   **UI:** Android XML Views, Material Components (Chips, MaterialSwitch, SeekBar, Spinner, Cards), RecyclerView with custom adapters
*   **Custom View:** `RoutePreviewView` — Canvas-drawn route timeline
*   **Hardware:** `SensorManager` (accelerometer, light), `LocationManager` (GPS)
*   **Persistence:** `SharedPreferences` + `org.json`
*   **Testing:** JUnit 4 — planner logic, distance math, per-city route scenarios

## University Requirements Coverage
*   **Activities:** 5 Activities with Intent-based data passing, including result data returned back (`ActivityResultLauncher` / `setResult`).
*   **CustomView:** Canvas-based `RoutePreviewView` inside RouteResultActivity.
*   **RecyclerView:** Three list screens with custom adapters.
*   **Sensors:** Three sensors read and used (accelerometer, light, GPS).
*   **UI variety:** Chips, Switch, SeekBar, Spinner, ProgressBar, Snackbar, Cards.
*   **Persistence:** SharedPreferences.
*   **Platform:** Kotlin, compile/target SDK 36 (runs on API 34–36 as required; minSdk 24).

## How to Run
1.  Open the project in **Android Studio** (2025.x or newer).
2.  Sync Gradle and run the `app` module on an emulator (API 34+) or a device.
3.  Optional: enable the GPS switch and grant the location permission. In the emulator, set a location near the selected city (Extended Controls → Location), otherwise the app falls back to the city centre.

## How to Test
Run the unit tests in `app/src/test`:
*   `RoutePlannerTest` — budget limits, ordering, filters, GPS-vs-centre start semantics.
*   `CityRouteScenariosTest` — every city must produce a non-empty route for 30/60/90/240 min and for indoor/outdoor preferences.
*   `DistanceCalculatorTest` — Haversine accuracy.

## Limitations
*   Straight-line (Haversine) distances; street geometry is not considered.
*   The schematic timeline replaces a real map SDK by design (offline-first).
*   The dataset is curated and static.

## Future Improvements
*   Real map rendering as an optional online mode.
*   Larger dataset and more cities.
*   Smarter optimization (2-opt / simulated annealing) over the greedy heuristic.
*   Weather-aware suggestions.
