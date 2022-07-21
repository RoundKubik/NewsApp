package ru.roundkubik.news.domain.usecase

import android.util.Log
import io.reactivex.Single
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.domain.model.Headlines
import ru.roundkubik.news.domain.repository.NewsRepository
import javax.inject.Inject

class CacheHeadlinesUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(headlines: Headlines) :  Single<NewsResult<List<Long>>>  {
        Log.d("ArticleDataSource", "invoke: ${headlines.articles}")
        return repository.addArticlesInDb(headlines)
    }
}