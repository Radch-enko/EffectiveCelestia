package band.effective.hackathon.celestia.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules()
    }
}