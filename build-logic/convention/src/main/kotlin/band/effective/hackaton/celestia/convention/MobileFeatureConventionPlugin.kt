package band.effective.hackaton.celestia.convention

import band.effective.hackaton.celestia.convention.utils.getLibraryFromLibsToml
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MobileFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("band.effective.hackaton.celestia.convention.coremultiplatform")
                apply("band.effective.hackaton.celestia.convention.core.ui")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                with(extensions.findByType<ComposePlugin.Dependencies>()!!) {
                    with(sourceSets) {
                        commonMain.dependencies {
                            implementation(project(":core:data"))
                            implementation(project(":core:domain"))
                            implementation(project(":core:ui"))
                            implementation(getLibraryFromLibsToml("androidx.lifecycle.viewmodel"))
                            implementation(getLibraryFromLibsToml("androidx.lifecycle.runtime"))
                            implementation(foundation)
                            implementation(getLibraryFromLibsToml("coroutines-core"))
                            implementation(getLibraryFromLibsToml("ktor.serialization.json"))

                        }
                        androidMain.dependencies {
                            implementation(getLibraryFromLibsToml("coroutines-android"))
                        }
                    }
                }
            }
        }
    }
}
