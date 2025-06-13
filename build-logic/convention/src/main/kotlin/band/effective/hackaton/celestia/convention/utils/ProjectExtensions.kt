package band.effective.hackaton.celestia.convention.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.getVersionFromLibsToml(versionName: String): String =
    libs.findVersion(versionName).get().toString()

fun Project.getLibraryFromLibsToml(libraryName: String): String {
    return with(libs.findLibrary(libraryName).get().get()) {
        "${group}:${name}:${version}"
    }
}