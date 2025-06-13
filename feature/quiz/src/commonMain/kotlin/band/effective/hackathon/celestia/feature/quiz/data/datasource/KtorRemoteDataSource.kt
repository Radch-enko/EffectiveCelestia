package band.effective.hackathon.celestia.feature.quiz.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Implementation of RemoteDataSource using Ktor client
 */
class KtorRemoteDataSource(
    private val httpClient: HttpClient
) : RemoteDataSource {
    /**
     * Fetches data from a remote source using Ktor client
     * @param endpoint The API endpoint to fetch data from
     * @return The fetched data as a string
     */
    override suspend fun fetchData(endpoint: String): String {
        val response = httpClient.get(endpoint)
        return response.bodyAsText()
    }
    
    /**
     * Posts data to a remote source using Ktor client
     * @param endpoint The API endpoint to post data to
     * @param data The data to post
     * @return The response from the remote source as a string
     */
    override suspend fun postData(endpoint: String, data: String): String {
        val response = httpClient.post(endpoint) {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.bodyAsText()
    }
}