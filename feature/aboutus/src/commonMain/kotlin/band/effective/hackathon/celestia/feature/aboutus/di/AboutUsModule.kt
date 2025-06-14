package band.effective.hackathon.celestia.feature.aboutus.di

import band.effective.hackathon.celestia.feature.aboutus.presentation.AboutUsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for the About Us feature.
 */
val aboutUsModule = module {
    viewModelOf(::AboutUsViewModel)
}