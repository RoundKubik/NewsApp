package ru.roundkubik.news.domain.usecase

import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.domain.model.Headlines
import ru.roundkubik.news.domain.repository.NewsRepository

class GetHeadlinesUseCase(private val repository: NewsRepository){
    operator fun invoke(): NewsResult<Headlines> {
        return repository.getHeadlines()
    }
}