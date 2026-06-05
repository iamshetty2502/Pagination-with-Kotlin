# Restaurant Pagination with Jetpack Compose

A modern Android application demonstrating how to implement server-side pagination using **Paging 3** with **Jetpack Compose**, **Hilt**, **Retrofit**, and **Coroutines**.

## 🚀 Recent Update: XML to Jetpack Compose & MVI Architecture
The project has been fully migrated from a traditional XML View-based architecture to **Jetpack Compose** with a focus on **MVI (Model-View-Intent)** principles.

### Key Changes:
- **UI Layer:** Replaced `RecyclerView`, `Adapter`, and XML layouts with Compose `LazyColumn` and custom Composables.
- **State Management (MVI):**
    - **`MainUiState` (Sealed Class):** Encapsulates the screen state (Success, Error, Idle) in a type-safe way.
    - **`MainEvent` (Sealed Class):** Represents user intentions (like updating the search radius), ensuring a single source of truth for UI events.
- **Paging Integration:** Updated the data flow to use `Flow<PagingData>` which is seamlessly collected in Compose using `collectAsLazyPagingItems()`.
- **Image Loading:** Migrated from Glide to **Coil** for native Compose image loading support.

## 🛠️ Tech Stack
- **Jetpack Compose:** Modern toolkit for building native UI.
- **Paging 3:** Loads and displays pages of data from the network.
- **Hilt:** Dependency injection library for Android.
- **Retrofit & OKHttp:** For network requests and API communication.
- **Kotlin Coroutines & Flow:** For asynchronous programming and reactive data streams.
- **Coil:** Image loading library for Android backed by Kotlin Coroutines.

## 📌 Features
- **Dynamic Radius Search:** Use the slider to select a search radius (up to 5km).
- **Infinite Scrolling:** Automatically fetches the next page of restaurants as you scroll.
- **Predictable State:** Leveraging sealed classes for UI state makes the app more robust and easier to debug.

## 🔑 Setup
The app uses the **Yelp Fusion API**. To run the project:
1. Obtain an API Key by signing up at [Yelp Developers](https://docs.developer.yelp.com/reference/v3_business_search).
2. Open `app/build.gradle.kts`.
3. Locate the `AUTH_TOKEN` field in `defaultConfig` and replace `"YOUR_YELP_TOKEN"` with your actual API key.

```kotlin
buildConfigField("String", "AUTH_TOKEN", "\"YOUR_ACTUAL_API_KEY\"")
```

## Credits
Inspired by the pagination tutorials from [CheezyCode](https://www.youtube.com/@CheezyCode).
