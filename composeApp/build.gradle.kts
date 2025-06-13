import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    id("band.effective.hackaton.celestia.convention.application")
    alias(libs.plugins.hotReload)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ui"))
            implementation(project(":core:domain"))
            implementation(project(":core:data"))
            implementation(project(":feature:splash"))
            implementation(project(":feature:test"))
            implementation(project(":feature:quiz"))
            implementation(libs.bundles.koin)
            implementation(libs.androidx.navigation.compose)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
        jvmMain.dependencies {
            runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.0") // TODO to libs.version.toml
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "EffectiveCelestia"
            packageVersion = "1.0.0"

            linux {
                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
            }
            windows {
                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
            }
            macOS {
                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
                bundleID = "band.effective.hackathon.celestia.desktopApp"
            }
        }
    }
}

//https://github.com/JetBrains/compose-hot-reload
composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}
tasks.withType<ComposeHotRun>().configureEach {
    mainClass.set("MainKt")
}
