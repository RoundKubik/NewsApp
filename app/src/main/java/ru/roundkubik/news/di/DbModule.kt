package ru.roundkubik.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.roundkubik.news.R
import ru.roundkubik.news.core.resource_provider.ResourceProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    companion object {

        @Singleton
        @Provides
        fun provideDbName(resourceProvider: ResourceProvider): String =
            resourceProvider.string(R.string.db_name)
    }
}
