# Technology Stack & Technical Deep Dive

This document provides a comprehensive analysis of the technologies, architectural patterns, and engineering principles implemented in this project. It is designed to serve as a high-quality resource for technical understanding and interview preparation.

---

## 1. Jetpack Compose (Modern Declarative UI)
Jetpack Compose is Android's modern toolkit for building native UI using a declarative approach.

### Core Concepts:
- **Declarative vs. Imperative**: In the legacy XML system (imperative), you manually update view properties (e.g., `view.setText()`). In Compose, you describe what the UI should look like for a given state, and the framework handles the updates automatically.
- **Recomposition**: This is the process of re-executing Composable functions when their inputs change. Compose uses **Intelligent Recomposition** to only update the specific components affected by a state change, skipping others to optimize performance.
- **State Management**:
    - `remember`: Stores an object in memory during initial composition and returns the stored value during recomposition.
    - `mutableStateOf`: Returns a `MutableState` object which is an observable type that the Compose engine tracks.
    - `rememberSaveable`: Similar to `remember`, but survives configuration changes (like screen rotation).
- **State Hoisting**: A pattern of moving state to a Composable's caller to make the Composable stateless, making it more reusable and easier to test.
- **Side Effects**:
    - `LaunchedEffect`: Used for UI-triggered actions like navigation or showing snackbars. Runs in the scope of the composition.
    - `DisposableEffect`: Used for side effects that need cleanup (e.g., observers or listeners) when the Composable leaves the composition.

---

## 2. Paging 3 (Efficient Data Loading)
Paging 3 helps load and display large datasets in small chunks, significantly reducing memory and network overhead.

### Implementation Details:
- **RemoteMediator**: This is the core component for **Offline-First** functionality. It acts as a signal to the Paging library when the local database (Room) has run out of data to display. It then fetches more data from the Network and saves it to the Database.
- **PagingSource**: Responsible for loading data for a specific source (e.g., a Room database query).
- **Pager**: The primary entry point that coordinates between the `PagingSource` (or `RemoteMediator`) and the UI.
- **RemoteKeys**: A specialized table in Room used to track pagination metadata (like next/previous page keys) returned by the API, ensuring sequential loading without duplicates or gaps.

---

## 3. Room Persistence Library
Room is an abstraction layer over SQLite that provides compile-time verification of SQL queries and easier database access.

### Engineering Principles:
- **Single Source of Truth (SSOT)**: The core principle implemented here is that the UI always observes the Room database. The Network layer updates the database, and the database automatically updates the UI. This ensures data consistency across the app, even during offline transitions.
- **DAOs (Data Access Objects)**: Defined as interfaces to separate database interactions from the rest of the application. Room validates these at compile-time, preventing runtime SQL errors.
- **Entities**: Data classes representing the table schema in SQLite, decorated with annotations like `@Entity` and `@PrimaryKey`.

---

## 4. Retrofit & OkHttp (Network Layer)
Retrofit is a type-safe HTTP client for Android.

### Technical Highlights:
- **Interface-based API**: You define API endpoints using Kotlin interfaces, and Retrofit generates the implementation using Dynamic Proxies.
- **Gson Converter**: Automatically handles JSON serialization and deserialization into Kotlin Data Classes.
- **OkHttp Interceptors**: Used for logging (debugging network calls) and adding authentication headers (Yelp API Key) globally via `HeaderInterceptor`.

---

## 5. Hilt (Dependency Injection)
Hilt is a DI library for Android that reduces the boilerplate of manual Dagger implementation.

### Architectural Benefits:
- **Inversion of Control (IoC)**: Instead of a class creating its own dependencies, they are "injected" from the outside. This makes code modular, easy to refactor, and highly testable.
- **Scoping**: Dependencies are scoped to specific components (e.g., `@Singleton`, `@ActivityScoped`, `@ViewModelScoped`), ensuring they live only as long as needed.
- **Constructor Injection**: The preferred method for receiving dependencies, making the class requirements explicit and facilitating easier unit testing with mock objects.

---

## 6. Kotlin Coroutines & Flow
Coroutines provide a way to perform asynchronous tasks without blocking the main thread.

### Reactive Streams:
- **Structured Concurrency**: Ensures that coroutines are launched within a specific scope (like `viewModelScope`) and are automatically cancelled when that scope is destroyed, preventing memory leaks and orphaned background tasks.
- **StateFlow vs. SharedFlow**:
    - `StateFlow`: A state-holder observable flow that emits the current and new state updates. Always has an initial value and "replays" the last state to new collectors. Ideal for UI State.
    - `SharedFlow`: Used for "one-time" events (like navigation or Toast messages) that shouldn't be re-emitted on configuration changes.
- **collectAsStateWithLifecycle**: A lifecycle-aware way to collect flows in Compose, ensuring data is only collected when the UI is in an active state, saving resources.

---

## 7. MVI (Model-View-Intent) Architectural Pattern
The project leverages MVI/UDF (Unidirectional Data Flow) principles for predictable state management.

### The Cycle:
- **Intent (Event)**: User actions are captured as sealed class `Events` (e.g., `UpdateRadius`).
- **Model (State)**: The ViewModel processes events and produces a single immutable `UiState` object.
- **View**: The Composable UI observes the `UiState` and renders itself.
- **Benefits**: Centralized state makes debugging easier (reproducible states) and makes UI logic highly predictable.

---

## 8. App Entry Flow: Splash & Login
- **Navigation Compose**: Used a `NavHost` to manage screen transitions declaratively.
- **Splash Screen**: Implemented using a 2-second delay in a `LaunchedEffect`, serving as a visual brand entry point.
- **Authentication Visuals**: Demonstration of Login UI with social authentication (Google/Facebook) and credential fields. It follows the pattern of navigating to the core app only after a "success" state.

---

## 9. Coil (Image Loading)
- **Why Coil?**: It's a Kotlin-first library that's lightweight and integrates seamlessly with Compose and Coroutines.
- **Implementation**: Uses `AsyncImage` for asynchronous image fetching, caching, and placeholder/error state management.

---

### Interview "Cheat Sheet" - Common Questions
1.  **Explain the Offline-First flow**: How Room and Paging 3 work together via `RemoteMediator` to synchronize data.
2.  **Why Hilt over Dagger?**: Reduced boilerplate, standard Android scopes, and easier setup for ViewModel injection.
3.  **Difference between `launch` and `async`?**: `launch` is for "fire and forget" tasks, while `async` returns a `Deferred` result that you can `await`.
4.  **How does Compose handle configuration changes?**: State hoisted to a `ViewModel` survives rotations, and `rememberSaveable` can be used for local UI state.
5.  **Role of `Dispatchers.IO`**: Optimized for blocking I/O tasks (Network/DB) by using a shared pool of threads, keeping the Main thread free for UI rendering.
