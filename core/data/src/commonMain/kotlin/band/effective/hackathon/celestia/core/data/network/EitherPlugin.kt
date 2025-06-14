package band.effective.hackathon.celestia.core.data.network

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.core.domain.functional.error
import band.effective.hackathon.celestia.core.domain.functional.success
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.isSuccess
import io.ktor.util.AttributeKey

/**
 * Ktor client plugin that wraps all responses in [Either]
 */
class EitherPlugin {
    /**
     * Configuration for the [EitherPlugin]
     */
    class Config

    companion object Plugin : HttpClientPlugin<Config, EitherPlugin> {
        override val key: AttributeKey<EitherPlugin> = AttributeKey("EitherPlugin")

        override fun prepare(block: Config.() -> Unit): EitherPlugin {
            return EitherPlugin()
        }

        override fun install(plugin: EitherPlugin, client: HttpClient) {
            Napier.d("Installing EitherPlugin", tag = "EitherPlugin")
            // No need to modify the pipeline, we'll use extension functions instead
        }
    }
}

/**
 * Custom exception for HTTP errors
 */
class HttpException(
    val status: Int,
    override val message: String,
    val url: String
) : Exception(message)
