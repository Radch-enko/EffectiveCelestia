package band.effective.hackathon.celestia.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

/**
 * JVM-specific implementation of HttpClientFactory
 */
class JvmHttpClientFactory : HttpClientFactory {
    /**
     * Creates and configures a JVM-specific HttpClient instance using OkHttp engine
     * @return Configured HttpClient instance
     */
    override fun create(): HttpClient {
        return HttpClientConfig.configureClient(
            HttpClient(OkHttp) {
                engine {
                    config {
                        // JVM-specific OkHttp configuration if needed
                        retryOnConnectionFailure(true)
                    }
                }
            }
        )
    }
}