package band.effective.hackathon.celestia.core.data.network

import io.ktor.client.HttpClient

/**
 * Factory interface for creating platform-specific HttpClient instances
 */
interface HttpClientFactory {
    /**
     * Creates and configures a platform-specific HttpClient instance
     * @return Configured HttpClient instance
     */
    fun create(): HttpClient
}