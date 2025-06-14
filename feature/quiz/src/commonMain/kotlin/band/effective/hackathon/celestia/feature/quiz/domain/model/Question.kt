package band.effective.hackathon.celestia.feature.quiz.domain.model

/**
 * Represents a quiz question with multiple answer options.
 *
 * @property id Unique identifier for the question
 * @property text The text of the question
 * @property answers List of possible answers for the question
 */
data class Question(
    val id: Int,
    val text: String,
    val answers: List<Answer>
)