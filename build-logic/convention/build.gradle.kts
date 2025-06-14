plugins {
    `kotlin-dsl`
}

group = "band.effective.template.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.androidGradlePlugin)
    compileOnly(libs.kotlinGradlePlugin)
    compileOnly(libs.kotlinMultiplatformPlugin)
    compileOnly(libs.composeMultiplatformPlugin)
}

gradlePlugin {
    plugins {
        create("mobileApplication") {
            id = "band.effective.hackaton.celestia.convention.application"
            implementationClass = "band.effective.hackaton.celestia.convention.MobileAppPlugin"
        }
        create("mobileFeatureConventions") {
            id = "band.effective.hackaton.celestia.convention.mobilefeature"
            implementationClass = "band.effective.hackaton.celestia.convention.MobileFeatureConventionPlugin"
        }
        create("mobileCoreMultiplatformConventions") {
            id = "band.effective.hackaton.celestia.convention.coremultiplatform"
            implementationClass = "band.effective.hackaton.celestia.convention.MobileCoreMultiplatformConventionPlugin"
        }
        create("mobileCoreUiConventions") {
            id = "band.effective.hackaton.celestia.convention.core.ui"
            implementationClass = "band.effective.hackaton.celestia.convention.MobileCoreUiConventionPlugin"
        }
    }
}
