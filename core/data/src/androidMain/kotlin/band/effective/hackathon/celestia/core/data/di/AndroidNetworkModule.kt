package band.effective.hackathon.celestia.core.data.di

import band.effective.hackathon.celestia.core.data.network.AndroidHttpClientFactory
import band.effective.hackathon.celestia.core.data.network.HttpClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android-specific network module for Koin DI
 */
val androidNetworkModule = module {
    single<HttpClientFactory> { AndroidHttpClientFactory() }
}

/**
 * Function to get Android-specific network modules
 * @return List of Android-specific Koin modules
 */
actual fun getPlatformNetworkModules(): List<Module> {
    return listOf(androidNetworkModule)
}
