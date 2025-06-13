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
            text = "Что ведёт тебя сквозь тьму?",
            answers = listOf(
                Answer(1, "Интуиция"),
                Answer(2, "Рациональность"),
                Answer(3, "Любовь"),
                Answer(4, "Любопытство")
            )
        ),
        Question(
            id = 2,
            text = "Какой твой ритм во Вселенной?",
            answers = listOf(
                Answer(1, "Молния"),
                Answer(2, "Течение"),
                Answer(3, "Пульс"),
                Answer(4, "Шёпот")
            )
        ),
        Question(
            id = 3,
            text = "Где тебе комфортнее?",
            answers = listOf(
                Answer(1, "Глубины космоса"),
                Answer(2, "Туманность мечты"),
                Answer(3, "Полюс света"),
                Answer(4, "Гравитация Земли")
            )
        ),
        Question(
            id = 4,
            text = "Где тебе комфортнее?",
            answers = listOf(
                Answer(1, "Глубины космоса"),
                Answer(2, "Туманность мечты"),
                Answer(3, "Полюс света"),
                Answer(4, "Гравитация Земли")
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