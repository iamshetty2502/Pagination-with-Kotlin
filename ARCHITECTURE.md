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

The application follows a predictable, unidirectional data flow (UDF):

1.  **User Action**: The user interacts with the UI (e.g., scrolling, clicking a restaurant, or adjusting search radius).
2.  **Event (Intent)**: The UI triggers an event in the `ViewModel`.
3.  **Business Logic**: The ViewModel processes the event and, if data is needed, requests it from the `Repository`.
4.  **Pagination Coordination**: The `Pager` uses the `RemoteMediator` to check the local database. If more data is needed, it triggers a network call.
5.  **Data Synchronization**: **Retrofit** fetches the data, and it is immediately cached in the **Room Database**.
6.  **Observation**: The UI observes a `Flow<PagingData>` directly from the database.
7.  **Recomposition**: As the database updates, the `Flow` emits new data, and **Jetpack Compose** re-renders the UI to reflect the changes.

## Data Flow (Offline-First)
1. The **ViewModel** requests a `Flow<PagingData>` from the **Repository**.
2. The **Repository** provides a stream of data directly from the **Room Database**.
3. If the database requires more data (as detected by the `RemoteMediator`), a network request is made via **Retrofit**.
4. The API response is saved into **Room**.
5. **Room** automatically triggers an update to the `Flow`, and the **UI** re-renders with the new data.

## Dependency Injection
- **Hilt**: Manages the dependency graph. It provides Singleton instances of the Database, Retrofit service, and Repositories to the ViewModels, reducing boilerplate and improving testability.
