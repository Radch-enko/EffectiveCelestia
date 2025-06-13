package band.effective.hackathon.celestia.core.data.di

import band.effective.hackathon.celestia.core.data.network.HttpClientFactory
import band.effective.hackathon.celestia.core.data.network.JvmHttpClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * JVM-specific network module for Koin DI
 */
val jvmNetworkModule = module {
    single<HttpClientFactory> { JvmHttpClientFactory() }
}

/**
 * Function to get JVM-specific network modules
 * @return List of JVM-specific Koin modules
 */
actual fun getPlatformNetworkModules(): List<Module> {
    return listOf(jvmNetworkModule)
}