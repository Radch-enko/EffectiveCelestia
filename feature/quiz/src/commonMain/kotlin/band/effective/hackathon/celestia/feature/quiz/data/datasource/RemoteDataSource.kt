package band.effective.hackathon.celestia.feature.quiz.data.datasource

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptError

/**
 * Interface for remote data source operations
 */
interface RemoteDataSource {
    /**
     * Sends user answers to ChatGPT for analysis
     * 
     * @param userAnswers Map of question IDs to selected answers
     * @return Either containing the analysis from ChatGPT or an error
     */
    suspend fun sendAnswersToChatGpt(userAnswers: Map<Int, String>): Either<ChatGptError, String>
}
