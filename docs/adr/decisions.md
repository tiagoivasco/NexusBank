# ADR-001: Project Modularization Strategy

**Date:** 2025-03  
**Status:** Accepted

## Context

A white-label banking app needs to scale across multiple features and products
without the codebase becoming a monolith.

## Decision

Adopt a **multi-module** structure with three layers:

- `:core/*` — shared infrastructure (no business logic)
- `:feature/*` — vertical feature slices (self-contained)
- `:app` — thin shell (DI wiring + flavor config)

## Consequences

✅ Features compile independently → faster incremental builds  
✅ Strict dependency boundaries enforced by Gradle  
✅ Features can be enabled/disabled per flavor  
✅ Easier to onboard new developers to isolated areas  
⚠️ More `build.gradle.kts` files to maintain  
⚠️ Initial setup complexity is higher than a monolith

---

# ADR-002: White Label via Product Flavors

**Date:** 2025-03  
**Status:** Accepted

## Context

Two separate banking products (Conta Corrente, Conta Digital) must be distributed
as separate apps on the Play Store, with different package names, visual identities,
and backend URLs — but sharing 95%+ of the codebase.

## Decision

Use **Android Product Flavors** with a `FlavorConfig` interface.

Each flavor provides:

- A `FlavorConfigImpl.kt` in its source set (`app/src/<flavor>/kotlin/...`)
- A `strings.xml` with `app_name`
- `buildConfigField` entries for `BASE_URL` and `FLAVOR_NAME`

The `FlavorConfig` interface is registered as a Koin singleton in `AppModule`
and injected into `NexusTheme` via `CompositionLocalProvider`.

## Alternatives Considered

| Approach                     | Rejected Because                        |
|------------------------------|-----------------------------------------|
| Runtime config (JSON/remote) | Theme must be available at first frame  |
| Separate repositories        | Code duplication, divergence risk       |
| Feature flags only           | Can't change package ID or signing cert |

## Consequences

✅ Each APK is hermetic — BASE_URL is hardcoded at compile time  
✅ No runtime branching on flavor name in feature code  
✅ Adding a 3rd flavor requires touching only 3 files  
⚠️ Requires separate CI lane per flavor

---

# ADR-003: Clean Architecture Approach

**Date:** 2025-03  
**Status:** Accepted

## Context

A banking app handles sensitive operations (transfers, authentication, card management).
The business logic must be independently testable, isolated from Android/UI concerns,
and stable across framework version upgrades.

## Decision

Adopt **Clean Architecture** with three layers per feature:

```
Presentation → Domain ← Data
```

- Domain layer has **zero** Android or library dependencies
- `UseCase` base class handles dispatcher, exception wrapping, and coroutine context
- `Result<T>` is the universal return type — never throws, never returns null on error
- Repository interface lives in Domain; implementation lives in Data

## Consequences

✅ Use cases are unit-testable with pure JUnit (no Robolectric)  
✅ Swapping data sources (mock → real API → Room) requires zero changes in domain/presentation  
✅ `Result<T>` enforces explicit error handling at every call site  
⚠️ More boilerplate than MVVM-only approach  
⚠️ Discipline required to keep domain layer Android-free  
