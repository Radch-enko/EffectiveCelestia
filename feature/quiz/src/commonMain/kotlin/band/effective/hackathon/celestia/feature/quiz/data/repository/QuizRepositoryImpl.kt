package band.effective.hackathon.celestia.feature.quiz.data.repository

import band.effective.hackathon.celestia.feature.quiz.domain.model.Answer
import band.effective.hackathon.celestia.feature.quiz.domain.model.Question
import band.effective.hackathon.celestia.feature.quiz.domain.repository.QuizRepository

/**
 * Implementation of [QuizRepository] that provides quiz data.
 */
class QuizRepositoryImpl : QuizRepository {
    
    // In-memory cache of questions
    private val questions = listOf(
        Question(
            id = 1,
            text = "What is Kotlin?",
            answers = listOf(
                Answer(1, "A programming language"),
                Answer(2, "A database system"),
                Answer(3, "A design pattern"),
                Answer(4, "A mobile device")
            )
        ),
        Question(
            id = 2,
            text = "What is Jetpack Compose?",
            answers = listOf(
                Answer(1, "A UI toolkit for Android"),
                Answer(2, "A database library"),
                Answer(3, "A testing framework"),
                Answer(4, "A networking library")
            )
        ),
        Question(
            id = 3,
            text = "What is Kotlin Multiplatform?",
            answers = listOf(
                Answer(1, "A way to share code between platforms"),
                Answer(2, "A game engine"),
                Answer(3, "A cloud service"),
                Answer(4, "A version control system")
            )
        ),
        Question(
            id = 4,
            text = "What is Koin?",
            answers = listOf(
                Answer(1, "A dependency injection framework"),
                Answer(2, "A cryptocurrency"),
                Answer(3, "A UI component library"),
                Answer(4, "A build system")
            )
        )
    )
    
    override suspend fun getQuestions(): List<Question> {
        return questions
    }
    
    override suspend fun getQuestionById(questionId: Int): Question? {
        return questions.find { it.id == questionId }
    }
}