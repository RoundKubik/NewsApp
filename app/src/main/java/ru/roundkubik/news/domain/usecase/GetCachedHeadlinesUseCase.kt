package ru.roundkubik.news.domain.usecase

import io.reactivex.Single
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import ru.roundkubik.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetCachedHeadlinesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(category: Category): Single<NewsResult<Headlines>> {
        return repository.getCachedHeadlines(category)
    }
}