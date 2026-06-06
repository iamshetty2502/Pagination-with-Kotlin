# Technology Stack & Implementation Details

This project utilizes modern Android development tools and libraries to deliver a high-quality experience.

## 1. Jetpack Compose
- **Use**: Modern toolkit for building native UI.
- **Implementation**: Used for the entire UI layer (`MainScreen`, `DetailScreen`). It allows for a declarative approach where the UI reacts to state changes automatically.

## 2. Retrofit & Gson
- **Use**: Type-safe HTTP client for network requests and JSON parsing.
- **Implementation**: Defined in `INetwork.kt`. It fetches restaurant data from the Yelp API. `RetrofitModule.kt` provides the Singleton instance injected via Hilt.

## 3. Room Database
- **Use**: Local persistence and offline support.
- **Implementation**: 
    - `RestaurantEntity`: Defines the table for restaurants.
    - `RestaurantDao`: Provides methods for database operations.
    - `RemoteKeys`: Stores pagination keys to manage Paging 3 state.
    - Acts as the Single Source of Truth for the UI.

## 4. Paging 3 (RemoteMediator)
- **Use**: Loading large datasets in chunks efficiently.
- **Implementation**: `RestaurantRemoteMediator.kt` handles the logic of when to fetch from the network versus when to load from the local database. It ensures a smooth scrolling experience with minimal memory overhead.

## 5. Hilt (Dependency Injection)
- **Use**: Simplifying dependency management and promoting modularity.
- **Implementation**: Used to provide instances of the Database, Retrofit, and Repository across the app. It makes the code more testable and organized.

## 6. Coil
- **Use**: Image loading library for Android.
- **Implementation**: `AsyncImage` is used to load restaurant images from URLs with support for placeholders and error handling.

## 7. Kotlin Coroutines & Flow
- **Use**: Managing background tasks and reactive data streams.
- **Implementation**: Used throughout the data layer for asynchronous operations and in the ViewModel to expose data streams (`Flow`) to the UI.
