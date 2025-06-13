plugins {
    id("band.effective.hackaton.celestia.convention.core.ui")
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

compose.resources {
    publicResClass = true
    packageOfResClass = "band.effective.hackathon.celestia.res"
    generateResClass = auto
}