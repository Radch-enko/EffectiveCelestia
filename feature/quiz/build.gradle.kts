plugins {
    id("band.effective.hackaton.celestia.convention.mobilefeature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.koin)
            implementation(libs.ktor.client.core)
            implementation(libs.napier)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}
