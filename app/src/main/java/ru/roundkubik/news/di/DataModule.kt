package ru.roundkubik.news.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.roundkubik.news.data.local.source.CategoryDataSourceImpl
import ru.roundkubik.news.data.remote.source.NewsDataSourceImpl
import ru.roundkubik.news.data.source.CategoryDataSource
import ru.roundkubik.news.data.source.NewsDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun buildNewsDataSource(newsDataSourceImpl: NewsDataSourceImpl): NewsDataSource


    @Binds
    abstract fun buildCategoryDataSource(categoryDataSourceImpl: CategoryDataSourceImpl): CategoryDataSource
}