plugins {
    id("band.effective.hackaton.celestia.convention.coremultiplatform")
    id(libs.plugins.kotlinx.serialization.get().pluginId)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.logging)
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}