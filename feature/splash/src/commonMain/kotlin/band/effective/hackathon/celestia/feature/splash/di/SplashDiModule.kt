package band.effective.hackathon.celestia.feature.splash.di

import band.effective.hackathon.celestia.feature.splash.presentation.SplashScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashDiModule = module {
    viewModelOf(::SplashScreenViewModel)
}