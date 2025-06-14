
# 🌌 Effective Celestia

**Effective Celestia** is a ✨ cross-platform educational app ✨ that brings the wonders of space to your fingertips. Designed as an **open-source sample project using Compose Multiplatform**, it aims to showcase **modern UI/UX, scalable architecture, and best practices** for quickly bootstrapping production-ready apps across Android, iOS, Desktop, and Web.

---

## 🚀 Project Goal

This project serves as a **starter template** for developers building Kotlin Multiplatform apps. It demonstrates:

- Clean, scalable architecture (Clean Architecture)
- State management, DI, navigation, and platform-specific patterns
- A modern, polished user interface
- Compose Multiplatform’s power across all major targets

---

## 📸 Screenshots

<table>
  <tr>
    <td align="center"><strong>Android</strong><br/><img src="docs/media/android_demo.gif" width="250"/></td>
    <td align="center"><strong>iOS</strong><br/><img src="docs/media/ios_demo.gif" width="250"/></td>
    <td align="center"><strong>Desktop</strong><br/><img src="docs/media/macos_demo.gif" width="250"/></td>
  </tr>
  <tr>
    <td colspan="3" align="center">
      <strong>Web</strong><br/>
      <a href="https://radch-enko.github.io/effectiive.hackathon.celestia/">
        <img src="docs/media/web_demo.gif" width="500"/>
      </a>
    </td>
  </tr>
</table>

---

## 🛠️ Tech Stack

- **Kotlin Multiplatform** – shared logic across platforms  
- **Jetpack Compose Multiplatform** – unified declarative UI  
- **Koin** – lightweight dependency injection  
- **Kotlinx Serialization** – JSON handling  
- **Napier** – multiplatform logging  
- **Navigation Compose** – declarative screen transitions  
- **Material 3** – modern UI styling  
- **YandexGPT API** – AI-powered content generation  
- **Gradle (Kotlin DSL)** – build automation

---

## ▶️ Getting Started

### 🔧 Prerequisites

- Run [KDoctor](https://github.com/Kotlin/kdoctor) to validate environment
- Install **JDK 17+**
- Create a `local.properties` file with your Android SDK path
- Add YandexGPT credentials:
  ```
  gpt.api.key=your_api_key_here
  gpt.model.id=your_model_id_here
  ```

---

### 📱 Android

To run:

- Open in Android Studio, select Android run config

To build APK:

```bash
./gradlew :composeApp:assembleDebug
```

APK will be located at `composeApp/build/outputs/apk/debug/`

To run UI tests:

```bash
./gradlew :composeApp:connectedDebugAndroidTest
```

---

### 💻 Desktop

Run the desktop app:

```bash
./gradlew :composeApp:run
```

With hot reload:

```bash
./gradlew :composeApp:jvmRunHot
```

UI tests:

```bash
./gradlew :composeApp:jvmTest
```

---

### 🍏 iOS

To run:

- Open `iosApp/iosApp.xcproject` in Xcode  
- Or use the [KMM Plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)

iOS simulator tests:

```bash
./gradlew :composeApp:iosSimulatorArm64Test
```

---

### 🌐 Web (Alpha)

Run in browser:

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun --continue
```

Browser UI tests:

```bash
./gradlew :composeApp:wasmJsBrowserTest
```

---

## 🧱 Project Structure

### Modules

- `composeApp` – multiplatform entry points and navigation  
- `core:*` – shared core logic  
  - `core:ui` – common components and theming  
  - `core:domain` – models and interfaces  
  - `core:data` – repositories and data sources  
- `feature:*` – modular features  
  - `splash`, `quiz`, `planet`, `test`, `aboutus`

---

### 🧭 Architecture

The project follows **Clean Architecture** with these layers:

- **Presentation** – UI, ViewModels (MVI pattern)
- **Domain** – business logic, use cases
- **Data** – repositories, data models, APIs

Each feature module respects this structure for clarity and reusability.

---

### 🔑 Key Components

- **Navigation** – Compose Navigation with centralized route definitions (`NavRoutes.kt`)  
- **Dependency Injection** – via `Koin`, structured per module  
- **UI Layer** – Compose + Material 3 + custom space-themed components  
- **AI Integration** – using YandexGPT to generate quizzes and recommendations

---

## 📈 Roadmap

- [ ] Add test coverage  
- [ ] CI/CD pipeline setup  
- [ ] Code linting configuration

---

## 👥 Authors

**Software Engineer** – [Stanislav Radchenko](https://github.com/radch-enko)  
📬 Telegram: [@StanislavRadchenko](https://t.me/StanislavRadchenko)

**UI/UX Designer** – Alexandra Korytova  
🎨 Telegram: [@alesrkt](https://t.me/alesrkt)
