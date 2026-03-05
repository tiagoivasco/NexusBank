# Architecture Decision Records — Nexus Android

## Overview

Nexus Android follows **Clean Architecture** with a strict separation of concerns
across three horizontal layers (Domain, Data, Presentation) and a vertical feature
module structure. The white-label capability is layered on top via Android Product Flavors.

---

## Layer Responsibilities

### Domain Layer
- Pure Kotlin — no Android dependencies
- Contains: domain models, repository interfaces, use cases
- Business rules live here and nowhere else
- `UseCase<Params, T>` and `NoParamsUseCase<T>` run on `Dispatchers.IO` and wrap
  all exceptions in `Result.Error` — callers never need try/catch

### Data Layer
- Implements repository interfaces defined in domain
- Contains: DTOs, Retrofit API interfaces, mappers, repository implementations
- Mappers are pure functions: `FooDto.toDomain(): Foo`
- The repository is the only class that touches the network/database

### Presentation Layer
- `ViewModel` holds `MutableStateFlow<UiState>` and exposes it as `StateFlow`
- Composables are stateless — they receive state and emit events
- `koinViewModel()` injects ViewModels via Koin
- `collectAsStateWithLifecycle()` is preferred over `collectAsState()`

---

## Module Dependency Rules

```
:feature:* → :core:ui, :core:network, :core:domain   ✓
:core:*    → other :core:* modules                    ✗ (no cross-core deps)
:app       → all modules                              ✓
:feature:* → :feature:*                               ✗ (features are independent)
```

Features communicate with each other only through navigation events and shared
domain models in `:core:domain` (if needed).

---

## White Label Flow

```
Build time
  Gradle picks flavor source set (bankCorp OR bankDigital)
  → Compiles FlavorConfigImpl with that flavor's values
  → Injects BASE_URL via BuildConfig

Runtime
  NexusApplication.onCreate()
  → Koin starts
  → appModule provides FlavorConfig (= FlavorConfigImpl) as singleton

  setContent {
    NexusTheme {          ← koinInject<FlavorConfig>()
      MaterialTheme(      ← colorScheme = flavorConfig.colorScheme
        ...               ← all child composables inherit the theme
      )
    }
  }
```

Any composable that needs flavor-specific data accesses it via:
```kotlin
val flavorConfig = LocalFlavorConfig.current
```

---

## State Management

```
ViewModel
  _uiState: MutableStateFlow<HomeUiState>
  uiState:  StateFlow<HomeUiState>  ← exposed (read-only)

Composable
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  // recomposes only when state changes
```

Error handling follows the same pattern across all features:
```kotlin
when (val result = someUseCase()) {
    is Result.Success -> _uiState.update { it.copy(data = result.data) }
    is Result.Error   -> _uiState.update { it.copy(error = result.message) }
    is Result.Loading -> _uiState.update { it.copy(isLoading = true) }
}
```

---

## Dependency Injection (Koin)

Each feature module exposes a single `val <feature>Module = module { ... }`.

Registration order in `NexusApplication`:
```
appModule      → FlavorConfig, NetworkConfig
networkModule  → OkHttpClient, Retrofit, AuthInterceptor
homeModule     → HomeApi, HomeRepository, UseCases, HomeViewModel
```

**Convention**: feature modules use `singleOf` and `viewModelOf` DSL helpers
to keep DI declarations compact and refactor-safe.

---

## Networking

- `AuthInterceptor` is a singleton that holds the Bearer token in memory
- Token is set after login via `authInterceptor.setToken(token)`
- Each flavor has a different `BASE_URL` (from `BuildConfig`) and should
  have a different SSL certificate pin configured in `OkHttpClient`
- Logging is `BODY` level on debug builds and `NONE` on release

---

## Security Checklist (to implement before production)

- [ ] Certificate pinning per flavor in `OkHttpClient`
- [ ] Root / emulator detection (RootBeer or similar)
- [ ] Biometric authentication with `BiometricPrompt`
- [ ] R8 / ProGuard obfuscation (rules in `proguard-rules.pro`)
- [ ] Separate Keystore per flavor for APK signing
- [ ] No sensitive data in `BuildConfig` beyond URLs (use secrets manager)
- [ ] `android:allowBackup="false"` in manifest
