# Effective Celestia

## Description

Effective Celestia is a cross-platform educational application about celestial bodies. The application allows users to
take quizzes about space and planets, and based on their answers, recommends planets that match their preferences. It
provides detailed information about various celestial bodies with beautiful animations and a space-themed interface.

The application is built using Kotlin Multiplatform and Compose Multiplatform, allowing it to run on multiple platforms
including Android, iOS, Desktop (Windows, macOS, Linux), and Web browsers.

## Screenshots

### Android

*Screenshots for Android are available in the application.*

### iOS

*Screenshots for iOS are available in the application.*

### Desktop

*Screenshots for Desktop are available in the application.*

### Web

The web version of the application is available
at: [https://radch-enko.github.io/effectiive.hackathon.celestia/](https://radch-enko.github.io/effectiive.hackathon.celestia/)

## Tech Stack

- **Kotlin Multiplatform**: For sharing code across platforms
- **Compose Multiplatform**: For UI development
- **Koin**: For dependency injection
- **Kotlinx Serialization**: For JSON serialization/deserialization
- **Napier**: For logging
- **Navigation Compose**: For navigation between screens
- **YandexGPT API**: For generating quiz content and planet recommendations
- **Material3**: For UI design
- **Gradle**: For build automation

## How to Launch

### Prerequisites

- Check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
- Install JDK 17 or higher on your machine
- Add `local.properties` file to the project root and set a path to Android SDK there
- Configure YandexGPT API key in `local.properties`:
  ```
  gpt.api.key=your_api_key_here
  gpt.model.id=your_model_id_here
  ```

### Android

To run the application on an Android device/emulator:

- Open the project in Android Studio and run the imported Android run configuration

To build the application bundle:

- Run `./gradlew :composeApp:assembleDebug`
- Find the `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

Run Android UI tests on the connected device:

- `./gradlew :composeApp:connectedDebugAndroidTest`

### Desktop

Run the desktop application:

- `./gradlew :composeApp:run`

Run the desktop with hot reload:

- `./gradlew :composeApp:jvmRunHot`

Run desktop UI tests:

- `./gradlew :composeApp:jvmTest`

### iOS

To run the application on an iPhone device/simulator:

- Open `iosApp/iosApp.xcproject` in Xcode and run the standard configuration
- Or use
  the [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for
  Android Studio

Run iOS simulator UI tests:

- `./gradlew :composeApp:iosSimulatorArm64Test`

### Web Browser (Alpha)

Run the browser application:

- `./gradlew :composeApp:wasmJsBrowserDevelopmentRun --continue`

Run browser UI tests:

- `./gradlew :composeApp:wasmJsBrowserTest`

## Project Structure

### Modules

#### composeApp

The main application module that contains the entry points for all platforms and the main navigation logic.

#### core

Contains core functionality shared across features:

- **core:ui**: Common UI components, themes, and styles
- **core:domain**: Domain models and interfaces
- **core:data**: Data sources, repositories, and data models

#### feature

Contains feature modules, each implementing a specific part of the application:

- **feature:splash**: Splash screen shown when the app starts
- **feature:quiz**: Quiz functionality where users answer questions about space
- **feature:planet**: Planet information display based on quiz results
- **feature:test**: Test functionality
- **feature:aboutus**: Information about the application and its creators

### Architecture

The application follows a clean architecture approach with the following layers:

- **Presentation**: UI components, screens, and view models (MVI pattern)
- **Domain**: Business logic, use cases, and domain models
- **Data**: Data sources, repositories, and data models

Each feature module follows this architecture and is organized into corresponding packages.

### Key Components

#### Navigation

The application uses Compose Navigation for navigation between screens. The main navigation routes are defined in
`NavRoutes.kt`.

#### Dependency Injection

Koin is used for dependency injection throughout the application. Each module has its own Koin module defined in a `di`
package.

#### UI

The UI is built using Compose Multiplatform with Material3 design. The application has a space-themed background and
custom UI components.

#### API Integration

The application integrates with the YandexGPT API to generate quiz questions and planet recommendations based on user
answers.

## Contacts and Authors

### Authors

Software Engineer - [StanislavRadchenko](https://github.com/radch-enko)  
Telegram: [@StanislavRadchenko](https://t.me/StanislavRadchenko)

UI/UX Designer - Alexandra Korytova  
Telegram: [@alesrkt](https://t.me/alesrkt)
