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

/**
 * Extension function to install the EitherPlugin
 */
fun HttpClient.wrapWithEither(): HttpClient {
    return config {
        install(EitherPlugin)
    }
}

/**
 * Extension function for HttpResponse to handle Either responses
 */
suspend inline fun <reified T> HttpResponse.bodyAsEither(): Either<Throwable, T> {
    return try {
        if (status.isSuccess()) {
            try {
                // Parse successful response
                val body = body<T>()
                success(body)
            } catch (e: Exception) {
                // Handle parsing errors
                Napier.e("Failed to parse response body", e, tag = "EitherPlugin")
                error(e)
            }
        } else {
            // Handle HTTP error
            val errorBody = try {
                bodyAsText()
            } catch (e: Exception) {
                "Error ${status.value}: ${status.description}"
            }

            val httpException = HttpException(
                status = status.value,
                message = errorBody,
                url = request.url.toString()
            )

            Napier.e("HTTP error: ${httpException.status} - ${httpException.message}", tag = "EitherPlugin")
            error(httpException)
        }
    } catch (e: Exception) {
        // Handle network or other errors
        Napier.e("Request failed", e, tag = "EitherPlugin")
        error(e)
    }
}

/**
 * Extension function to execute a request and wrap the response in Either
 */
suspend inline fun <reified T> HttpClient.executeWithEither(
    block: () -> HttpRequestBuilder
): Either<Throwable, T> {
    return try {
        val response = this.plugin(EitherPlugin).let { 
            this.request(block())
        }
        response.bodyAsEither()
    } catch (e: Exception) {
        Napier.e("Request execution failed", e, tag = "EitherPlugin")
        error(e)
    }
}
