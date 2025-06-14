package band.effective.hackathon.celestia.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

/**
 * Android-specific implementation of HttpClientFactory
 */
class AndroidHttpClientFactory : HttpClientFactory {
    /**
     * Creates and configures an Android-specific HttpClient instance using OkHttp engine
     * @return Configured HttpClient instance
     */
    override fun create(): HttpClient {
        return HttpClientConfig.configureClient(
            HttpClient(OkHttp) {
                engine {
                    config {
                        // Android-specific OkHttp configuration if needed
                        retryOnConnectionFailure(true)
                    }
                }
            }
        )
    }
}