# Nexus Android — White Label Banking Platform

> Two banking products. One codebase. Zero compromises.

| Flavor | App Name | Product | Color Identity |
|--------|----------|---------|----------------|
| `bankCorp` | **NexusBank** | Conta Corrente | Navy + Gold |
| `bankDigital` | **NexusPay** | Conta Digital | Purple + Teal |

---

## Quick Start

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34

### Run a flavor

```bash
# NexusBank (Conta Corrente)
./gradlew :app:installBankCorpDebug

# NexusPay (Conta Digital)
./gradlew :app:installBankDigitalDebug
```

Or in Android Studio: switch the **Build Variant** dropdown to `bankCorpDebug` / `bankDigitalDebug` and hit ▶.

---

## Module Graph

```
:app
 ├── :core:ui          ← Design system, NexusTheme, FlavorConfig interface
 ├── :core:network     ← Retrofit, OkHttp, AuthInterceptor, NetworkConfig
 ├── :core:domain      ← Result<T>, UseCase, NoParamsUseCase
 └── :feature:home     ← Home dashboard (balance, quick actions, transactions)
```

Each `:feature:*` module is independent — it knows nothing about which flavor is running.

---

## White Label Architecture

The key to the white label system is the `FlavorConfig` interface in `:core:ui`:

```kotlin
interface FlavorConfig {
    val appName: String
    val accountType: AccountType
    val colorScheme: ColorScheme
    val gradientStart: Color
    val gradientEnd: Color
}
```

Each flavor provides its own implementation at `app/src/<flavor>/kotlin/.../FlavorConfigImpl.kt`.

At compile time Gradle includes **only one** `FlavorConfigImpl`, which is registered as a Koin singleton. The entire app — theme, colors, typography — reacts to it automatically through `NexusTheme` and `LocalFlavorConfig`.

**To add a new flavor:**
1. Add a new entry in `productFlavors` in `app/build.gradle.kts`
2. Create `app/src/<newFlavor>/kotlin/com/nexus/app/FlavorConfigImpl.kt`
3. Create `app/src/<newFlavor>/res/values/strings.xml`
4. Done — no other files need changing

---

## Adding a New Feature

```
feature/
└── <featureName>/
    ├── data/
    │   ├── remote/       ← Retrofit API + DTOs
    │   ├── mapper/       ← DTO → Domain model mappers
    │   └── repository/   ← Repository implementation
    ├── domain/
    │   ├── model/        ← Pure Kotlin data classes
    │   ├── repository/   ← Repository interface (contract)
    │   └── usecase/      ← Business logic, extends UseCase/NoParamsUseCase
    ├── presentation/
    │   ├── <Name>UiState.kt
    │   ├── <Name>ViewModel.kt
    │   └── <Name>Screen.kt
    └── di/
        └── <Name>Module.kt
```

1. Create the module and add it to `settings.gradle.kts`
2. Write domain → data → presentation following the pattern in `:feature:home`
3. Add the Koin module to the module list in `NexusApplication`

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| UI | Jetpack Compose + Material3 |
| Architecture | Clean Architecture (Domain / Data / Presentation) |
| DI | Koin 3.5 |
| Networking | Retrofit 2 + OkHttp 4 + Gson |
| Async | Kotlin Coroutines + StateFlow |
| Navigation | Jetpack Navigation Compose |
| White Label | Android Product Flavors |

---

## Project Conventions

- All monetary values use `BigDecimal` — never `Double`
- Repository implementations catch exceptions and return `Result.Error`
- ViewModels expose `StateFlow<UiState>` — never `LiveData`
- Composables consume state, never call use cases directly
- One Koin module per feature, registered in `NexusApplication`
