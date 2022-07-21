package ru.roundkubik.news.presentation.news

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.roundkubik.news.core.entity.ErrorEntity
import ru.roundkubik.news.data.remote.model.HeadlinesError
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.core.resource_provider.ResourceProvider
import ru.roundkubik.news.core.schedulers.SchedulerProvider
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.usecase.GetHeadlinesUseCase
import ru.roundkubik.news.domain.usecase.GetSortedCategoriesUseCase
import ru.roundkubik.news.presentation.model.HeadlineUi
import ru.roundkubik.news.presentation.model.toHeadlineUi
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val getSortedCategoriesUseCase: GetSortedCategoriesUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _headlines: MutableStateFlow<Map<Category, HeadlineUi>> = MutableStateFlow(mapOf())
    val headlines: StateFlow<Map<Category, HeadlineUi>> get() = _headlines

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    private val categories: StateFlow<List<Category>> get() = _categories

    private val _newsState: MutableStateFlow<NewsState> = MutableStateFlow(NewsState.Initial)
    val newsState get() = _newsState

    private val disposable = CompositeDisposable()

    init {
        _categories.value = getSortedCategoriesUseCase.invoke()
        _headlines.value =
            categories.value.associateWith { category -> HeadlineUi.Progress(category) }
    }

    fun getHeadlines() {
        categories.value.map { category ->
            getHeadlinesUseCase.invoke(category)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    when (it) {
                        is NewsResult.Success -> {
                            val data = it.data.toHeadlineUi()
                            val mutableData = _headlines.value.toMutableMap().apply {
                                this[data.category] = data
                            }
                            _headlines.value = mutableData
                        }
                        is NewsResult.Error -> {
                            val mutableData = _headlines.value.toMutableMap().apply {
                                this[category] = HeadlineUi.Fail(it.error.message, category)
                            }
                            _headlines.value = mutableData
                        }
                    }
                }, {
                    _newsState.value = NewsState.Failure("some message ")
                })
                .addTo(disposable)
        }
    }

    sealed class NewsState {
        object Initial : NewsState()
        data class Failure(val message: String) : NewsState()
    }

    companion object {
        private val TAG = NewsViewModel::class.simpleName
    }
}