# Project Architecture - Pagination

This project implements a robust and scalable architecture based on **MVVM (Model-View-ViewModel)** and **Clean Architecture** principles, ensuring separation of concerns and ease of testing.

## Architectural Layers

### 1. Presentation Layer (UI)
- **Jetpack Compose**: A modern, declarative toolkit for building native UI.
- **ViewModels**: Manage UI state and handle business logic. They use `StateFlow` to expose observable state to the Compose UI.
- **UI State**: Encapsulated in data classes (e.g., `DetailUiState`) to represent different screen states like Loading, Success, and Error.

### 2. Domain / Repository Layer
- **Repository Pattern**: Acts as a mediator between different data sources. It abstracts the complexities of data fetching from the ViewModel.
- **Single Source of Truth**: The local database (Room) serves as the primary source for the UI, ensuring offline availability.

### 3. Data Layer
- **Local Source (Room)**: Persists data locally. It includes Entities, DAOs, and the Database definition.
- **Remote Source (Retrofit)**: Handles API communication with the Yelp backend.
- **Paging 3 (RemoteMediator)**: Coordinates between the network and local database. It fetches data from the API when the local cache is exhausted and saves it to the database.

## Design Flow

The application follows a predictable, unidirectional data flow (UDF) inspired by MVI (Model-View-Intent):

1.  **App Start (Splash)**: The application starts with a `SplashScreen`, which is displayed for 2 seconds.
2.  **Authentication (Login)**: After the splash, the user is navigated to the `LoginScreen`. Users can "log in" via traditional credentials or social mechanisms (Google/Facebook). For now, this is a visual implementation without functional validation.
3.  **User Action (Main)**: Once logged in, the user reaches the `MainScreen` where they can scroll the list or adjust the search radius.
4.  **Event (Intent)**: The UI sends a specific `MainEvent` to the `MainViewModel`.
5.  **Business Logic**: The ViewModel processes the event. If new data is required, it triggers the Repository.
6.  **Pagination Request**: The Repository uses the `Pager` and `RemoteMediator` to decide if a network call is needed.
7.  **Data Sync**: If required, **Retrofit** fetches data from the API and saves it into the **Room Database**.
8.  **Observation**: The **Room Database** acts as the single source of truth; the UI observes the database changes via a `Flow`.
9.  **State Update**: The ViewModel emits a new `MainUiState`.
10. **Recomposition**: **Jetpack Compose** observes the state and re-renders only the necessary parts of the screen.

## Data Flow (Offline-First)
1. The **ViewModel** requests a `Flow<PagingData>` from the **Repository**.
2. The **Repository** provides a stream of data directly from the **Room Database**.
3. If the database requires more data (as detected by the `RemoteMediator`), a network request is made via **Retrofit**.
4. The API response is saved into **Room**.
5. **Room** automatically triggers an update to the `Flow`, and the **UI** re-renders with the new data.

## Dependency Injection
- **Hilt**: Manages the dependency graph. It provides Singleton instances of the Database, Retrofit service, and Repositories to the ViewModels, reducing boilerplate and improving testability.
