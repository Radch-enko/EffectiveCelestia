package band.effective.hackathon.celestia.core.data.di

import band.effective.hackathon.celestia.core.data.network.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Koin module for network-related dependencies
 */
val networkModule = module {
    // Platform-specific HttpClientFactory is provided by platform-specific modules
    single<HttpClient> { get<HttpClientFactory>().create() }
}

/**
 * Expected declaration for platform-specific network modules
 * @return List of platform-specific Koin modules
 */
expect fun getPlatformNetworkModules(): List<Module>

/**
 * Function to get all network-related Koin modules
 * @return List of Koin modules
 */
fun getNetworkModules(): List<Module> {
    return listOf(networkModule) + getPlatformNetworkModules()
}
