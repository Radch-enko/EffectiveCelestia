plugins {
    id("band.effective.hackaton.celestia.convention.coremultiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.logging)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}