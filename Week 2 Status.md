# Portfolio Compose — Week 2 Implementation Status

## Build Status

| Target | Status |
|---|---|
| `./gradlew :composeApp:assembleDebug` | ✅ Passing |
| `./gradlew :composeApp:compileKotlinJvm` | ✅ Passing |
| `./gradlew projects` (`:server` removed) | ✅ Confirmed |

---

## Completed — Week 2
 
### Phase 0 — Gradle Cleanup
- [x] `settings.gradle.kts` — removed `include(":server")`
- [x] `composeApp/build.gradle.kts` — removed `projects.shared`, added `kotlinSerialization` plugin, added `navigation-compose`, `kotlinx-serialization-json`, `coil-compose`, `compose.materialIconsExtended`

### Phase 1 — Version Catalog
- [x] `gradle/libs.versions.toml` — added versions: `kotlinx-serialization`, `navigation-compose`, `coil`
- [x] Added libraries: `navigation-compose`, `kotlinx-serialization-json`, `coil-compose`
- [x] Added plugin: `kotlinSerialization`

### Phase 2 — Theme Layer
- [x] `theme/Color.kt` — `PortfolioColors`, `DarkColorScheme`, `LightColorScheme`
- [x] `theme/Typography.kt` — `PortfolioTypography` (DisplayLarge → LabelSmall)
- [x] `theme/Theme.kt` — `PortfolioTheme(darkTheme, content)`

### Phase 3 — Domain Models + Data
- [x] `data/model/PortfolioData.kt` — `@Serializable` models: `Profile`, `Stat`, `Skill`, `Project`, `Experience`, `PortfolioData`
- [x] `data/repository/PortfolioRepository.kt` — loads JSON via `Res.readBytes`, exposes `StateFlow<PortfolioData?>`
- [x] `composeResources/files/portfolio_data.json` — placeholder content with `TODO:` markers throughout

### Phase 4 — Navigation + Window Size
- [x] `util/WindowSize.kt` — `WindowWidthClass` enum + `windowWidthClassFor(Dp)` (uses `BoxWithConstraints`, avoids Android Activity requirement)
- [x] `navigation/AppNavigation.kt` — type-safe `Screen` routes (`@Serializable`), `AdaptivePortfolioApp` composable with:
  - `Compact` (<600dp) → `Scaffold` + `NavigationBar` (bottom)
  - `Medium` (600–840dp) → `Scaffold` + `NavigationRail` (side)
  - `Expanded` (>840dp) → `PermanentNavigationDrawer` (sidebar)
  - `ComingSoon` placeholder for unbuilt screens

### Phase 5 — App.kt
- [x] Replaced demo scaffold with `PortfolioTheme` + `BoxWithConstraints` window sizing + `AdaptivePortfolioApp`
- [x] Dark/light toggle state wired through to `HeroScreen`

### Phase 6 — Screens
- [x] `ui/hero/HeroScreen.kt`
  - Gradient background (`radialGradient`)
  - Profile photo placeholder (`Icons.Default.AccountCircle`)
  - Typewriter `AnimatedContent` greeting: "Hi, I'm **[Name]**"
  - Subtitle from `profile.title`
  - "View Projects" + "Download Resume" CTA buttons
  - GitHub / LinkedIn `IconButton` stubs
  - Theme toggle icon (top-right)
- [x] `ui/about/AboutScreen.kt`
  - `LazyColumn` layout
  - Bio paragraph from `profile.bio`
  - `StatCard` composables (label + value)
  - Adaptive: Expanded → `Row` of cards; Compact/Medium → `Column`

---

## Remaining — Week 3+

### Screens (stub → real)

| Screen | File to Create | Priority |
|---|---|---|
| Skills | `ui/skills/SkillsScreen.kt` | Week 3 |
| Projects | `ui/projects/ProjectsScreen.kt` | Week 3 |
| Experience | `ui/experience/ExperienceScreen.kt` | Week 3 |
| Contact | `ui/contact/ContactScreen.kt` | Week 3 |

### Content — Replace Placeholder JSON

Edit `composeResources/files/portfolio_data.json`:

- [ ] `profile.name` — your real name
- [ ] `profile.title` / `profile.subtitle` — your title
- [ ] `profile.bio` — your bio paragraph
- [ ] `profile.email` / `profile.github` / `profile.linkedin` — real URLs
- [ ] `stats` — real years / projects / companies counts
- [ ] `skills` — replace `TODO: Skill 9–12`, fill `iconName` values
- [ ] `projects` — replace all 3 placeholder project entries
- [ ] `experiences` — replace all 3 placeholder company entries

### Hero Screen — Real Photo

In `ui/hero/HeroScreen.kt`, replace:
```kotlin
Icon(
    imageVector = Icons.Default.AccountCircle,
    ...
)
```
with a Coil `AsyncImage` pointing to your actual photo (local resource or URL).

### Hero Screen — URL Opening

Wire up the social link `IconButton`s (currently no-op):
```kotlin
// TODO: open github URL
// TODO: open linkedin URL
```
Requires a `UriHandler` or platform-specific URL launcher.

### Hero Screen — Download Resume

Wire up the "Download Resume" `OutlinedButton` (currently no-op):
```kotlin
onClick = { /* TODO: Download Resume */ }
```

### Week 5 — Dark/Light Persistence

Persist `darkTheme` state via DataStore so preference survives app restarts.
Currently uses ephemeral `remember { mutableStateOf(true) }` in `App.kt`.

### Optional — Custom Fonts

`Typography.kt` uses the system default font. To swap in Inter or DM Sans:
1. Add font files to `composeResources/font/`
2. Load with `FontFamily` in `Typography.kt`

### Build Verification — Remaining Platforms

| Platform | Status |
|---|---|
| Android | ✅ Verified |
| Desktop JVM | ✅ Verified |
| iOS | ⬜ Not yet verified |
| Web (JS) | ⬜ Not yet verified |
| Web (WasmJS) | ⬜ Not yet verified |

---

## Quick Verification Commands

```bash
# Android
./gradlew :composeApp:assembleDebug

# Desktop — opens window
./gradlew :composeApp:run

# Confirm :server is gone
./gradlew projects

# iOS (requires Xcode)
./gradlew :composeApp:iosSimulatorArm64Binaries
```

---

## File Map

```
composeApp/src/commonMain/
├── composeResources/
│   └── files/
│       └── portfolio_data.json          ← fill in TODO fields
└── kotlin/com/ptut/portfolio/
    ├── App.kt                           ← entry point
    ├── data/
    │   ├── model/PortfolioData.kt
    │   └── repository/PortfolioRepository.kt
    ├── navigation/
    │   └── AppNavigation.kt             ← adaptive scaffold + NavHost
    ├── theme/
    │   ├── Color.kt
    │   ├── Typography.kt
    │   └── Theme.kt
    ├── ui/
    │   ├── about/AboutScreen.kt         ✅ built
    │   ├── hero/HeroScreen.kt           ✅ built
    │   ├── contact/                     ← Week 3
    │   ├── experience/                  ← Week 3
    │   ├── projects/                    ← Week 3
    │   └── skills/                      ← Week 3
    └── util/
        └── WindowSize.kt
```
