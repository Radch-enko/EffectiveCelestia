package band.effective.hackathon.celestia.di

import band.effective.hackathon.celestia.BuildKonfig
import band.effective.hackathon.celestia.core.data.di.getNetworkModules
import band.effective.hackathon.celestia.feature.quiz.di.quizModule
import band.effective.hackathon.celestia.feature.splash.di.splashDiModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initKoin() {
    // Initialize Napier logger
    Napier.base(DebugAntilog())

    startKoin {
        modules(
            buildKonfigModule,
            *getNetworkModules().toTypedArray(),
            splashDiModule,
            quizModule
        )
    }
}

val buildKonfigModule = module {
    single<String>(named("ChatGptKey")) { BuildKonfig.CHATGPT_API_KEY }
}