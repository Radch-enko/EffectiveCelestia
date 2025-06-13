plugins {
    id("band.effective.hackaton.celestia.convention.mobilefeature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}