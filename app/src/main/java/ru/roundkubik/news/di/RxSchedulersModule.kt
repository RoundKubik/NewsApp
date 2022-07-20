package ru.roundkubik.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
@InstallIn(SingletonComponent::class)
class RxSchedulersModule {

    companion object {
        @Provides
        fun provideSchedulerIo(): Scheduler = Schedulers.io()

        @Provides
        fun provideSchedulerComputation(): Scheduler = Schedulers.computation()

        @Provides
        fun provideMainThread(): Scheduler = AndroidSchedulers.mainThread()
    }
}
