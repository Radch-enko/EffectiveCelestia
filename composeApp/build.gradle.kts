import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    id("band.effective.hackaton.celestia.convention.application")
    alias(libs.plugins.hotReload)
    alias(libs.plugins.buildkonfig)
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
            implementation(project(":feature:planet"))
            implementation(libs.bundles.koin)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.napier)
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

val chatGPTApiKey: String = gradleLocalProperties(rootDir, providers).getProperty("gpt.api.key")
val gptModelId: String = gradleLocalProperties(rootDir, providers).getProperty("gpt.model.id")

buildkonfig {
    packageName = "band.effective.hackathon.celestia"
    exposeObjectWithName = "BuildKonfig"
    defaultConfigs {
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "CHATGPT_API_KEY",
            chatGPTApiKey,
        )

        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "GPT_MODEL_ID",
            gptModelId,
        )
    }
}