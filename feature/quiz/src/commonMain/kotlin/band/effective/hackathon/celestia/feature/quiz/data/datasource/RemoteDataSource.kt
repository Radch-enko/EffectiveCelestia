package band.effective.hackathon.celestia.feature.quiz.data.datasource

/**
 * Interface for remote data source operations
 */
interface RemoteDataSource {
    /**
     * Fetches data from a remote source
     * @param endpoint The API endpoint to fetch data from
     * @return The fetched data as a string
     */
    suspend fun fetchData(endpoint: String): String
    
    /**
     * Posts data to a remote source
     * @param endpoint The API endpoint to post data to
     * @param data The data to post
     * @return The response from the remote source as a string
     */
    suspend fun postData(endpoint: String, data: String): String
}