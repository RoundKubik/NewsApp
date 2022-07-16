package ru.roundkubik.news.presentation.news

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.roundkubik.news.core.entity.ErrorEntity
import ru.roundkubik.news.core.entity.HeadlinesError
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.usecase.GetHeadlinesUseCase
import ru.roundkubik.news.domain.usecase.GetSortedCategories
import ru.roundkubik.news.presentation.model.HeadlineUi
import ru.roundkubik.news.presentation.model.toHeadlineUi

class NewsViewModel constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val getSortedCategories: GetSortedCategories
) : ViewModel() {

    private val _headlines: MutableStateFlow<MutableMap<Category, HeadlineUi>> =
        MutableStateFlow(mutableMapOf())
    val headlines: StateFlow<Map<Category, HeadlineUi>> get() = _headlines

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _newsState: MutableStateFlow<NewsState> = MutableStateFlow(NewsState.Initial)
    val newsState get() = _newsState

    private val disposable = CompositeDisposable()

    init {
        _categories.value = getSortedCategories.invoke()
        _headlines.value = categories.value.associateWith { HeadlineUi.Progress(it) }.toMutableMap()
    }

    fun getHeadlines() {
        categories.value.map { category ->
            getHeadlinesUseCase.invoke(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it) {
                        is NewsResult.Success -> {
                            _headlines.value[category] = it.data.toHeadlineUi()
                        }
                        is NewsResult.Error -> {
                            HeadlineUi.Fail(it.error.toString(), category)
                        }
                    }
                }, {
                    _newsState.value = NewsState.Failure(HeadlinesError.Unknown)
                })
                .addTo(disposable)
        }
    }

    sealed class NewsState {
        object Initial : NewsState()
        data class Failure(val newError: ErrorEntity) : NewsState()
    }
}