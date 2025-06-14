package band.effective.hackathon.celestia.feature.quiz.domain.model

/**
 * Represents an answer option for a quiz question.
 *
 * @property id Unique identifier for the answer
 * @property text The text of the answer
 */
data class Answer(
    val id: Int,
    val text: String
)