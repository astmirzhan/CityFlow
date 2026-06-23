# CityFlow — Smart Walking Route Planner

## Overview
**CityFlow** is an intelligent walking route planner designed for urban explorers who want a curated, time-optimized experience without the hassle of manual planning. By considering user preferences, available time, and the physical environment via device sensors, CityFlow generates logical walking paths through a city's best spots.

## Project Idea
Unlike traditional travel apps that merely list points of interest, CityFlow focuses on the *journey*. It solves the "what should I do for the next 2 hours?" problem by automatically constructing a sequence of stops that fit perfectly into the user's schedule. The app operates entirely offline, using a local curated dataset and local calculations to ensure privacy, speed, and reliability regardless of data connectivity.

## Key Features
*   **Time-Based Planning:** Specify how many minutes you have, and the app fills them efficiently.
*   **Interest Filtering:** Filter routes by categories such as Culture, Nature, Food, or Landmarks.
*   **Environment Preference:** Choose between Indoor, Outdoor, or Mixed routes.
*   **Intelligent Location:** Use real-time GPS coordinates or a stable city-center fallback.
*   **Live Route Visualization:** View the planned path on a custom `RoutePreviewView`.
*   **Context Awareness:** Adapts recommendations based on ambient light and movement.
*   **History & Exploration:** Save favorite routes and browse the entire local places dataset.

## Screens
*   **MainActivity:** The "Control Center" where users set their time budget, select interests, toggle GPS, and configure route preferences.
*   **RouteResultActivity:** Displays the generated route summary, the visual preview, a detailed list of stops, and provides options to save or rebuild the route.
*   **PlacesActivity:** A comprehensive list of all locations in the local curated dataset.
*   **HistoryActivity:** A gallery of previously saved walking routes for easy reference.

## How the Route Planner Works
The core logic resides in the `RoutePlanner` domain component:
1.  **Filtering:** Candidates are selected from the local dataset based on user-selected categories and indoor/outdoor preferences.
2.  **Distance Calculation:** Uses the **Haversine formula** to calculate the "as-the-crow-flies" distance between coordinates on the Earth's surface.
3.  **Time Estimation:** Assumes a standard walking speed of **5 km/h** and adds the predefined `visitTime` for each stop.
4.  **Greedy Optimization:** Employs a nearest-neighbour algorithm to pick the next logical stop that minimizes walking distance while staying within the remaining time budget.
5.  **Deterministic Logic:** Routes are built step-by-step until no more places can be added without exceeding the time limit.

## Sensors Used
CityFlow leverages hardware sensors to enhance the user experience:
*   **Light Sensor:** Reads ambient brightness (Lux). If the environment is very bright or dark, the `SensorContextAdvisor` provides context-aware suggestions for indoor or outdoor settings.
*   **Accelerometer:** Monitors for movement or sudden shakes. A shake gesture can be used to "shake up" the current plan and trigger a route rebuild, adding an interactive element to the discovery process.

## Location Handling
The app is designed to be robust:
*   **GPS Integration:** Requests `ACCESS_FINE_LOCATION` permission to start the route from the user's actual position.
*   **Smart Fallback:** If permissions are denied or the GPS signal is lost (common in emulators or deep indoors), the app seamlessly falls back to a default city-center coordinate. This ensures the app remains functional in all testing environments.

## Local Places Dataset
CityFlow relies on a **local curated places dataset**. This is an offline collection of high-quality city locations, including:
*   Precise GPS coordinates.
*   Category tags (Museums, Parks, etc.).
*   Average visit duration.
*   User ratings.
*   Indoor/Outdoor classification.

## Data Storage
*   **Places:** Encapsulated within the `PlaceRepository` as a static, curated list.
*   **Route History:** Saved routes are serialized into JSON and stored locally using `SharedPreferences`. This allows for lightweight, persistent storage without the need for a complex database or cloud synchronization.

## Tech Stack
*   **Language:** Kotlin
*   **UI Framework:** Android XML Views with AppCompat & Material Components
*   **Components:** RecyclerView (with custom adapters), CardView, ConstraintLayout
*   **Data Handling:** `org.json` for serialization
*   **Hardware APIs:** `LocationManager`, `SensorManager`
*   **Custom Views:** `RoutePreviewView` for canvas-based path drawing
*   **Testing:** JUnit 4 for domain logic validation

## Project Structure
*   **`model`**: Data classes like `Place`, `WalkingRoute`, and `RouteStop`.
*   **`data`**: Repositories managing the curated dataset and history storage.
*   **`domain`**: The "brain" of the app (Distance calculations, Route planning, Sensor logic).
*   **`ui`**: Custom RecyclerView adapters for places, stops, and history.
*   **`view`**: The `RoutePreviewView` custom component.
*   **`activities`**: Flow management and user interaction logic.

## University Requirements Coverage
This project fulfills the following technical requirements:
*   **Activities:** 4 distinct Activities with Intent-based data passing.
*   **Modern API Usage:** `ActivityResultLauncher` for permissions and result handling.
*   **Complex UI:** Extensive use of `RecyclerView` and various Material Design components (Chips, Switches, SeekBars).
*   **Custom Graphics:** Implementation of a `Custom View` using Android's `Canvas` API.
*   **Sensor Integration:** Multi-sensor usage (Light & Accelerometer).
*   **Persistence:** Local storage via `SharedPreferences`.
*   **Platform Support:** Fully compatible with Android API 34 through 36.

## How to Run
1.  Open the project in **Android Studio (Ladybug or newer)**.
2.  Sync Gradle files.
3.  Run the `app` module on an emulator (API 34+) or a physical device.
4.  Ensure Location permissions are granted when prompted for the best experience.

## How to Test
The project includes unit tests for the core algorithm:
1.  Navigate to `app/src/test/java`.
2.  Run `RoutePlannerTest` to verify route building logic and time budget constraints.
3.  Run `DistanceCalculatorTest` to verify the accuracy of the Haversine implementation.

## Limitations
*   **No Map SDK:** The app visualizes routes using a simplified custom view rather than Google Maps or Mapbox.
*   **Simplified Routing:** Distance is calculated as a straight line; actual street-level sidewalk geometry is not considered.
*   **Static Dataset:** Only includes the curated places provided in the local repository.
*   **GPS in Emulators:** May require manual coordinate input or rely on the city-center fallback.

## Future Improvements
*   **Expanded Data:** Adding more cities and a larger variety of local places.
*   **Onboarding:** A dedicated flow for selecting cities and setting initial preferences.
*   **Map Integration:** Transitioning from the custom preview to a real-world map provider.
*   **Advanced Optimization:** Implementing more complex algorithms like A* or Genetic Algorithms for better path optimization.
*   **Live Weather Integration:** Adjusting routes based on real-time weather forecasts.
