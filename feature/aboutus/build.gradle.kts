plugins {
    id("band.effective.hackaton.celestia.convention.mobilefeature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.koin)
            implementation(libs.napier)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = auto
}