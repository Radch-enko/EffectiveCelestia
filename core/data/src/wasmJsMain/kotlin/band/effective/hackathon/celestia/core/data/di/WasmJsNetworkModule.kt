package band.effective.hackathon.celestia.core.data.di

import band.effective.hackathon.celestia.core.data.network.HttpClientFactory
import band.effective.hackathon.celestia.core.data.network.WasmJsHttpClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * WasmJs-specific network module for Koin DI
 */
val wasmJsNetworkModule = module {
    single<HttpClientFactory> { WasmJsHttpClientFactory() }
}

/**
 * Function to get WasmJs-specific network modules
 * @return List of WasmJs-specific Koin modules
 */
actual fun getPlatformNetworkModules(): List<Module> {
    return listOf(wasmJsNetworkModule)
}