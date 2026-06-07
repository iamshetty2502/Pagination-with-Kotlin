# Project Architecture - Pagination

This project implements a robust and scalable architecture based on **MVVM (Model-View-ViewModel)** and **Clean Architecture** principles, ensuring strict separation of concerns and adherence to **SOLID** principles.

## Architectural Layers

### 1. Presentation Layer (UI)
- **Jetpack Compose**: A modern, declarative toolkit for building native UI.
- **ViewModels**: Manage UI state and handle business logic. They use `StateFlow` to expose observable state to the Compose UI.
- **MVI Pattern**: User actions are processed as `Events`, and the UI observes a single `UiState`.
- **Hilt Integration**: ViewModels are annotated with `@HiltViewModel` and receive use cases via constructor injection.

### 2. Domain Layer (Business Logic)
- **Entities**: Plain Kotlin data classes (e.g., `Restaurant`) that are framework-independent.
- **Use Cases**: Encapsulate specific business rules (e.g., `GetNearbyRestaurantsUseCase`). They depend only on repository interfaces.
- **Repository Interfaces**: Define the contract for data operations, allowing the domain layer to remain agnostic of the data source (Dependency Inversion).

### 3. Data Layer (Implementation)
- **Repository Implementation**: `RestaurantRepositoryImpl` coordinates between the remote API and local database.
- **Local Source (Room)**: Provides offline caching. Includes `AppDatabase`, DAOs, and `RestaurantEntity`.
- **Remote Source (Retrofit)**: Handles communication with the Yelp API.
- **Paging 3 (RemoteMediator)**: Synchronizes network data into the local database for a seamless offline-first experience.
- **Mappers**: Convert Data Transfer Objects (DTOs) and Database Entities into Domain Models.

## SOLID Principles in Practice
- **S (Single Responsibility)**: Each class has a focused role (e.g., UseCases handle logic, DAOs handle DB).
- **D (Dependency Inversion)**: High-level Domain modules depend on abstractions (Interfaces), not low-level Data implementations. Hilt injects the implementations at runtime.

## Design Flow (UDF)
1. **Event**: UI triggers a `MainEvent`.
2. **UseCase**: ViewModel calls the relevant Use Case.
3. **Repository**: Use Case calls the Repository interface.
4. **Data Sync**: The Repository implementation uses `Pager` + `RemoteMediator` to fetch from `YelpApi` and cache in `Room`.
5. **Observation**: Room serves as the **Single Source of Truth**. The UI observes database changes via `Flow<PagingData>`.
6. **State Update**: ViewModel updates the `MainUiState`.
7. **Recomposition**: Compose re-renders the UI based on the new state.

## Dependency Injection
- **Hilt**: Centralizes dependency management through specialized modules:
    - `NetworkModule`: Provides Retrofit and API services.
    - `DatabaseModule`: Provides Room database and DAOs.
    - `RepositoryModule`: Binds Repository implementations to their interfaces.
