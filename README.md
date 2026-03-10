# PortfolioCompose

A modern, responsive, and adaptive personal portfolio application built with **Compose Multiplatform**. This project showcases a single-codebase approach to building high-quality UIs for **Android**, **iOS**, **Desktop (JVM)**, and **Web (Wasm/JS)**.

## 🏗️ Architecture

The project follows a multi-module Kotlin Multiplatform (KMP) architecture:

*   **`composeApp`**: The heart of the application. Contains all shared UI logic, themes, and screen implementations using Compose Multiplatform.
    *   `commonMain`: Shared UI and navigation.
    *   `androidMain`, `iosMain`, `jvmMain`, `wasmJsMain`, `webMain`: Platform-specific entry points and configurations.
*   **`shared`**: Contains pure Kotlin logic shared between the client (`composeApp`) and the backend (`server`).
*   **`server`**: A Ktor-based backend server.
*   **`iosApp`**: The native iOS wrapper required for running the shared Compose UI on Apple devices.

## ✨ Key Features

-   **Adaptive Navigation**: Intelligent layout switching based on screen size:
    -   **Compact**: Bottom Navigation Bar (Mobile)
    -   **Medium**: Navigation Rail (Tablets/Foldables)
    -   **Expanded**: Permanent Navigation Drawer (Desktop/Web)
-   **Dynamic Data**: Portfolio content (About, Skills, Projects, Experience) is managed via a centralized JSON data source.
-   **Dark Mode Support**: Built-in theme switching capability.
-   **Seamless Navigation**: Uses the modern Jetpack Navigation for Compose in a multiplatform context.

## 🚀 Getting Started

### Prerequisites
- JDK 17 or higher
- Android Studio (latest stable version)
- Xcode (for iOS development)

### Run the Application

| Platform | Command |
| :--- | :--- |
| **Android** | `./gradlew :composeApp:assembleDebug` |
| **Desktop** | `./gradlew :composeApp:run` |
| **Web (Wasm)** | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` |
| **Server** | `./gradlew :server:run` |

For **iOS**, open the `iosApp` directory in Xcode or use the Android Studio KMP plugin.

## 🛠️ Tech Stack

-   **Language**: Kotlin
-   **UI Framework**: Compose Multiplatform
-   **Navigation**: Navigation Compose Multiplatform
-   **Serialization**: Kotlinx Serialization
-   **Network/Backend**: Ktor
-   **Resources**: MOKO Resources / Compose Resources

---
*Built with ❤️ using Kotlin Multiplatform.*
