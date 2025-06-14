package band.effective.hackathon.celestia.core.data.di

import band.effective.hackathon.celestia.core.data.network.HttpClientFactory
import band.effective.hackathon.celestia.core.data.network.IosHttpClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * iOS-specific network module for Koin DI
 */
val iosNetworkModule = module {
    single<HttpClientFactory> { IosHttpClientFactory() }
}

/**
 * Function to get iOS-specific network modules
 * @return List of iOS-specific Koin modules
 */
actual fun getPlatformNetworkModules(): List<Module> {
    return listOf(iosNetworkModule)
}