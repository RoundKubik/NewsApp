package ru.roundkubik.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.roundkubik.news.core.schedulers.BaseSchedulerProvider
import ru.roundkubik.news.core.schedulers.SchedulerProvider

@Module
@InstallIn(SingletonComponent::class)
class SchedulersModule {

    companion object {
        @Provides
        fun provideSchedulerProvider(): SchedulerProvider = BaseSchedulerProvider()
    }
}