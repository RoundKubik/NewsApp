package ru.roundkubik.news.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.roundkubik.news.data.cache.source.ArticleDataSource
import ru.roundkubik.news.data.local.source.CategoryDataSourceImpl
import ru.roundkubik.news.data.remote.source.NewsDataSourceImpl
import ru.roundkubik.news.data.repository.CategoryRepositoryImpl
import ru.roundkubik.news.data.repository.NewsRepositoryImpl
import ru.roundkubik.news.data.source.ArticleCacheDataSource
import ru.roundkubik.news.data.source.CategoryDataSource
import ru.roundkubik.news.data.source.NewsDataSource
import ru.roundkubik.news.domain.repository.CategoryRepository
import ru.roundkubik.news.domain.repository.NewsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {


    @Binds
    abstract fun buildNewsDataSource(newsDataSourceImpl: NewsDataSourceImpl): NewsDataSource


    @Binds
    abstract fun buildCategoryDataSource(categoryDataSourceImpl: CategoryDataSourceImpl): CategoryDataSource

    @Binds
    abstract fun buildArticleDataSource(articleDataSource: ArticleDataSource): ArticleCacheDataSource

    companion object {

        @Provides
        fun provideMaxPageSize(): Int = 25

        @Provides
        fun providesNewsRepository(
            maxPageSize: Int,
            newsDataSource: NewsDataSource,
            cacheDataSource: ArticleCacheDataSource
        ): NewsRepository = NewsRepositoryImpl(
            maxPageSize,
            newsDataSource,
            cacheDataSource
        )

        @Provides
        fun providesCategoryRepository(
            categoryDataSource: CategoryDataSource,
        ): CategoryRepository = CategoryRepositoryImpl(
            categoryDataSource
        )
    }
}