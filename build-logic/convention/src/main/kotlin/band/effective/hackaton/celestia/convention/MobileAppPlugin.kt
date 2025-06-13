package band.effective.hackaton.celestia.convention

import band.effective.hackaton.celestia.convention.utils.getLibraryFromLibsToml
import band.effective.hackaton.celestia.convention.utils.getVersionFromLibsToml
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MobileAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.application")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget {
                    @OptIn(ExperimentalKotlinGradlePluginApi::class)
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_11)
                    }
                }

                jvm()

                wasmJs {
                    browser()
                    binaries.executable()
                }

                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach { iosTarget ->
                    iosTarget.binaries.framework {
                        baseName = "ComposeApp"
                        isStatic = true
                    }
                }

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

            extensions.configure<BaseAppModuleExtension> {
                namespace = "band.effective.template.mobile"

                compileSdk = getVersionFromLibsToml("android.compileSdk").toInt()

                defaultConfig {
                    applicationId = "band.effective.template.mobile"
                    minSdk = getVersionFromLibsToml("android.minSdk").toInt()
                    targetSdk = getVersionFromLibsToml("android.targetSdk").toInt()
                    versionCode = 1
                    versionName = "1.0"
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                    }
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }
        }
    }
}
