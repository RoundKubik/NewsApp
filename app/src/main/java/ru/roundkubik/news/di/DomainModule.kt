package ru.roundkubik.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.roundkubik.news.data.repository.NewsRepositoryImpl
import ru.roundkubik.news.data.source.NewsDataSource
import ru.roundkubik.news.domain.repository.NewsRepository
import ru.roundkubik.news.domain.usecase.GetHeadlinesUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {


    companion object {
        @Provides
        fun providesNewsRepository(
            newsDataSource: NewsDataSource,
        ): NewsRepository =  NewsRepositoryImpl(
            newsDataSource
        )

        @Provides
        fun provideGetHeadlinesUseCase(newsRepository: NewsRepository) =
            GetHeadlinesUseCase(newsRepository)

    }

}