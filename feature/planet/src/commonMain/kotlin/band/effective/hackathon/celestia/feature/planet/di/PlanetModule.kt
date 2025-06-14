package band.effective.hackathon.celestia.feature.planet.di

import band.effective.hackathon.celestia.feature.planet.presentation.PlanetViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for the Planet feature.
 */
val planetModule = module {
    viewModelOf(::PlanetViewModel)
}