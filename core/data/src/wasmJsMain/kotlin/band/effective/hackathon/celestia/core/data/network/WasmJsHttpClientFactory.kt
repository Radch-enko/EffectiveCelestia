package band.effective.hackathon.celestia.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js

/**
 * WasmJs-specific implementation of HttpClientFactory
 */
class WasmJsHttpClientFactory : HttpClientFactory {
    /**
     * Creates and configures a WasmJs-specific HttpClient instance using Js engine
     * @return Configured HttpClient instance
     */
    override fun create(): HttpClient {
        return HttpClientConfig.configureClient(
            HttpClient(Js) {
                engine {
                    // WasmJs-specific Js configuration if needed
                }
            }
        )
    }
}