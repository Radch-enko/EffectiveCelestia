package band.effective.hackathon.celestia.feature.quiz.di

import band.effective.hackathon.celestia.feature.quiz.data.repository.QuizRepositoryImpl
import band.effective.hackathon.celestia.feature.quiz.domain.repository.QuizRepository
import band.effective.hackathon.celestia.feature.quiz.presentation.QuizViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for the Quiz feature.
 */
val quizModule = module {
    // Repository
    single<QuizRepository> { QuizRepositoryImpl() }
    
    // ViewModel
    viewModel { QuizViewModel(get()) }
}