package ru.roundkubik.news.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.roundkubik.news.NewsApplication
import ru.roundkubik.news.core.resource_provider.BaseResourceProvider
import ru.roundkubik.news.core.resource_provider.ResourceProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        @Singleton
        @Provides
        fun provideApplication(@ApplicationContext app: Context): NewsApplication {
            return app as NewsApplication
        }

        @Singleton
        @Provides
        fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider =
            BaseResourceProvider(context)

    }


}