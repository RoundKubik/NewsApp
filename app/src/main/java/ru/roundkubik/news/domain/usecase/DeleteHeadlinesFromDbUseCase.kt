package ru.roundkubik.news.domain.usecase

import ru.roundkubik.news.domain.model.Headlines
import ru.roundkubik.news.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteHeadlinesFromDbUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(headlines: Headlines) {
        return repository.deleteArticlesFromDb(headlines)
    }
}