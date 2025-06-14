package band.effective.hackathon.celestia.core.data.network

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.core.domain.functional.error
import band.effective.hackathon.celestia.core.domain.functional.success
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.isSuccess

/**
 * Extension function to safely execute a request and wrap the response in Either
 */
suspend inline fun <reified T> HttpClient.requestAsEither(
    crossinline block: suspend HttpClient.() -> HttpResponse
): Either<Throwable, T> {
    return try {
        val response = this.block()

        if (response.status.isSuccess()) {
            try {
                // Parse successful response
                val body = response.body<T>()
                success(body)
            } catch (e: Exception) {
                // Handle parsing errors
                Napier.e("Failed to parse response body", e)
                error(e)
            }
        } else {
            // Handle HTTP error
            val errorBody = try {
                response.bodyAsText()
            } catch (e: Exception) {
                "Error ${response.status.value}: ${response.status.description}"
            }

            val httpException = HttpException(
                status = response.status.value,
                message = errorBody,
                url = response.request.url.toString()
            )

            Napier.e("HTTP error: ${httpException.status} - ${httpException.message}")
            error(httpException)
        }
    } catch (e: Exception) {
        // Handle network or other errors
        Napier.e("Request failed", e)
        error(e)
    }
}

/**
 * Extension function to safely execute a GET request and wrap the response in Either
 */
suspend inline fun <reified T> HttpClient.getAsEither(
    urlString: String,
    crossinline block: suspend HttpClient.() -> HttpResponse
): Either<Throwable, T> {
    Napier.d("Making GET request to $urlString")
    return requestAsEither(block)
}

/**
 * Extension function to safely execute a POST request and wrap the response in Either
 */
suspend inline fun <reified T> HttpClient.postAsEither(
    crossinline block: suspend HttpClient.() -> HttpResponse
): Either<Throwable, T> {
    return requestAsEither(block)
}

/**
 * Extension function to safely execute a PUT request and wrap the response in Either
 */
suspend inline fun <reified T> HttpClient.putAsEither(
    urlString: String,
    crossinline block: suspend HttpClient.() -> HttpResponse
): Either<Throwable, T> {
    Napier.d("Making PUT request to $urlString")
    return requestAsEither(block)
}

/**
 * Extension function to safely execute a DELETE request and wrap the response in Either
 */
suspend inline fun <reified T> HttpClient.deleteAsEither(
    urlString: String,
    crossinline block: suspend HttpClient.() -> HttpResponse
): Either<Throwable, T> {
    Napier.d("Making DELETE request to $urlString")
    return requestAsEither(block)
}
