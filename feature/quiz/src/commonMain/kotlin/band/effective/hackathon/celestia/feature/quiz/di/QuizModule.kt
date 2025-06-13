package band.effective.hackathon.celestia.feature.quiz.di

import band.effective.hackathon.celestia.feature.quiz.data.datasource.KtorRemoteDataSource
import band.effective.hackathon.celestia.feature.quiz.data.datasource.RemoteDataSource
import band.effective.hackathon.celestia.feature.quiz.data.repository.ChatGPTRepositoryImpl
import band.effective.hackathon.celestia.feature.quiz.data.repository.QuizRepositoryImpl
import band.effective.hackathon.celestia.feature.quiz.domain.repository.ChatGPTRepository
import band.effective.hackathon.celestia.feature.quiz.domain.repository.QuizRepository
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.GenerateWhichPlanetUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.GetQuestionByIdUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.GetQuestionsUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.HandleAnswerSelectionUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.LoadFirstQuestionUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.NavigateToPreviousQuestionUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl.GenerateWhichPlanetUseCaseImpl
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl.GetQuestionByIdUseCaseImpl
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl.GetQuestionsUseCaseImpl
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl.HandleAnswerSelectionUseCaseImpl
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl.LoadFirstQuestionUseCaseImpl
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl.NavigateToPreviousQuestionUseCaseImpl
import band.effective.hackathon.celestia.feature.quiz.presentation.QuizViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module for the Quiz feature.
 */
val quizModule = module {
    // Data Sources
    single<RemoteDataSource> { KtorRemoteDataSource(get(), get(named("ChatGptKey"))) }

    // Repositories
    single<QuizRepository> { QuizRepositoryImpl() }
    single<ChatGPTRepository> { ChatGPTRepositoryImpl(get()) }

    // Use Cases
    single<GetQuestionsUseCase> { GetQuestionsUseCaseImpl(get()) }
    single<GetQuestionByIdUseCase> { GetQuestionByIdUseCaseImpl(get()) }
    single<LoadFirstQuestionUseCase> { LoadFirstQuestionUseCaseImpl(get()) }
    single<NavigateToPreviousQuestionUseCase> { NavigateToPreviousQuestionUseCaseImpl(get()) }
    single<HandleAnswerSelectionUseCase> { HandleAnswerSelectionUseCaseImpl(get()) }
    single<GenerateWhichPlanetUseCase> { GenerateWhichPlanetUseCaseImpl(get()) }

    // ViewModel
    viewModel {
        QuizViewModel(
            get(),
            get(),
            get(),
            get(),
        )
    }
}
