package band.effective.hackathon.celestia.di

import band.effective.hackathon.celestia.feature.quiz.di.quizModule
import band.effective.hackathon.celestia.feature.splash.di.splashDiModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            splashDiModule,
            quizModule
        )
    }
}
