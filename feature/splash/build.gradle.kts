plugins {
    id("band.effective.hackaton.celestia.convention.mobilefeature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.koin)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}