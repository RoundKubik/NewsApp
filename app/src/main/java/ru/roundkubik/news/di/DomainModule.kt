package ru.roundkubik.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.roundkubik.news.data.repository.CategoryRepositoryImpl
import ru.roundkubik.news.data.repository.NewsRepositoryImpl
import ru.roundkubik.news.data.source.CategoryDataSource
import ru.roundkubik.news.data.source.NewsDataSource
import ru.roundkubik.news.domain.repository.CategoryRepository
import ru.roundkubik.news.domain.repository.NewsRepository
import ru.roundkubik.news.domain.usecase.GetHeadlinesUseCase
import ru.roundkubik.news.domain.usecase.GetSortedCategoriesUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {


    companion object {

        @Provides
        fun provideGetHeadlinesUseCase(newsRepository: NewsRepository) =
            GetHeadlinesUseCase(newsRepository)


        @Provides
        fun provideGetSortedCategoriesUseCase(categoryRepository: CategoryRepository) =
            GetSortedCategoriesUseCase(categoryRepository)
    }

}