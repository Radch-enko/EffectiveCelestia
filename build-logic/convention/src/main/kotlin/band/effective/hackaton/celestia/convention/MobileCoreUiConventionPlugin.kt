package band.effective.hackaton.celestia.convention

import band.effective.hackaton.celestia.convention.utils.getLibraryFromLibsToml
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MobileCoreUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("band.effective.hackaton.celestia.convention.coremultiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<KotlinMultiplatformExtension> {

                with(extensions.findByType<ComposePlugin.Dependencies>()!!) {
                    with(sourceSets) {
                        androidMain.dependencies {
                            implementation(preview)
                            implementation(getLibraryFromLibsToml("androidx.activityCompose"))
                        }
                        commonMain.dependencies {
                            implementation(runtime)
                            implementation(foundation)
                            implementation(material3)
                            implementation(ui)
                            implementation(components.resources)
                            implementation(components.uiToolingPreview)
                            implementation(getLibraryFromLibsToml("coil"))
                            implementation(getLibraryFromLibsToml("coil.network.ktor"))
                            implementation(getLibraryFromLibsToml("multiplatformSettings"))
                            implementation(getLibraryFromLibsToml("androidx.lifecycle.viewmodel"))
                            implementation(getLibraryFromLibsToml("androidx.lifecycle.viewmodel"))
                            implementation(getLibraryFromLibsToml("androidx.lifecycle.runtime"))
                        }

                        jvmMain.dependencies {
                            implementation(desktop.currentOs)
                        }
                    }
                }
            }
        }
    }
}