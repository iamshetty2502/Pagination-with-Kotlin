# Technology Stack & Technical Deep Dive

This document provides a comprehensive analysis of the technologies, architectural patterns, and engineering principles implemented in this project.

---

## 1. Jetpack Compose (Modern Declarative UI)
Jetpack Compose is Android's modern toolkit for building native UI using a declarative approach.
- **Declarative UI**: The UI is a function of the state. When state changes, Compose re-executes the functions (Recomposition).
- **State Management**: Uses `StateFlow` from ViewModels, collected in a lifecycle-aware manner using `collectAsStateWithLifecycle()`.

---

## 2. Paging 3 (Efficient Data Loading)
Paging 3 handles loading large datasets in chunks, reducing memory and network usage.
- **RemoteMediator**: Implements an **Offline-First** strategy. It fetches data from the network when the local database is out of data and saves it to Room.
- **Pager**: Coordinates the data loading from `RemoteMediator` and `PagingSource` (Room).
- **RemoteKeys**: Metadata stored in the database to track pagination state (next/prev keys) for each radius.

---

## 3. Asynchronous Programming (Coroutines & Flow)
The project uses Kotlin Coroutines for non-blocking asynchronous code, following the principle of **Main-Safety**.
- **viewModelScope**: Used in the ViewModel with `.cachedIn(viewModelScope)` to keep the paging data stream alive across configuration changes.
- **Main-Safe Libraries**: **Retrofit** and **Room** handle threading internally. Their `suspend` functions move execution off the main thread automatically, removing the need for explicit `withContext(Dispatchers.IO)` in most cases.
- **Implicit Collection**: Jetpack Compose collects data flows using `collectAsLazyPagingItems()`, which manages the coroutine lifecycle and launching internally.
- **Structured Concurrency**: All background work is tied to specific lifecycles (ViewModel or UI), preventing memory leaks.

---

## 4. Room Persistence (Local Cache)
Room serves as the **Single Source of Truth (SSOT)**.
- **Offline Support**: The UI observes the database directly. New network data is saved to Room, which automatically triggers UI updates.
- **Entities & DAOs**: Structured data access with compile-time SQL verification.

---

## 5. Retrofit & OkHttp (Network Layer)
- **REST API**: Communicates with the Yelp Fusion API.
- **Interceptors**: An OkHttp Interceptor handles the `Authorization` header injection using the API Key from `BuildConfig`.

---

## 6. Hilt (Dependency Injection)
The project uses Hilt to manage dependencies and follow the **Dependency Inversion Principle**.
- **Modules**:
    - `NetworkModule`: Provides Retrofit and API services using `@Provides`.
    - `DatabaseModule`: Provides the Room database and DAOs.
    - `RepositoryModule`: Uses `@Binds` to link the `RestaurantRepository` interface to its `RestaurantRepositoryImpl` implementation.

---

## 7. Clean Architecture & SOLID
- **Domain Layer**: Contains Use Cases and Repository interfaces. It is purely Kotlin-based.
- **Data Layer**: Implements Repository interfaces and handles API/Database logic.
- **Presentation Layer**: ViewModels and Compose screens.
- **SOLID**: High-level logic (Use Cases) is decoupled from low-level implementation details (Retrofit/Room) via interfaces.

---

## 8. MVI (Model-View-Intent)
- **Intent**: User actions (e.g., `UpdateRadius`) are captured as `MainEvent`.
- **State**: The UI observes a single immutable `MainUiState`.
- **Flow**: Unidirectional data flow ensures the UI is predictable and easy to debug.
