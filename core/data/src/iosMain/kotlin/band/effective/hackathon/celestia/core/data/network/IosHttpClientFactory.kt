package band.effective.hackathon.celestia.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

/**
 * iOS-specific implementation of HttpClientFactory
 */
class IosHttpClientFactory : HttpClientFactory {
    /**
     * Creates and configures an iOS-specific HttpClient instance using Darwin engine
     * @return Configured HttpClient instance
     */
    override fun create(): HttpClient {
        return HttpClientConfig.configureClient(
            HttpClient(Darwin) {
                engine {
                    // iOS-specific Darwin configuration if needed
                    configureRequest {
                        setAllowsCellularAccess(true)
                    }
                }
            }
        )
    }
}