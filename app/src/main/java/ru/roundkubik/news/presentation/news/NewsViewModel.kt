package ru.roundkubik.news.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.core.schedulers.SchedulerProvider
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.usecase.GetHeadlinesUseCase
import ru.roundkubik.news.domain.usecase.GetSortedCategoriesUseCase
import ru.roundkubik.news.presentation.model.FailHeadlineUi
import ru.roundkubik.news.presentation.model.HeadlineUi
import ru.roundkubik.news.presentation.model.ProgressHeadlineUi
import ru.roundkubik.news.presentation.model.toHeadlineUi
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    getSortedCategoriesUseCase: GetSortedCategoriesUseCase,
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val schedulerProvider: SchedulerProvider,
) : ViewModel() {

    private val sortedCategories: StateFlow<List<Category>> =
        MutableStateFlow(getSortedCategoriesUseCase.invoke())

    private val _headlines: MutableStateFlow<SortedMap<Category, HeadlineUi>> = MutableStateFlow(
        sortedCategories.value
            .associateWith { category -> ProgressHeadlineUi(category) }
            .toSortedMap(compareBy { it.name })
    )
    val headlines: StateFlow<Map<Category, HeadlineUi>> get() = _headlines

    private val _newsState: MutableStateFlow<NewsState> = MutableStateFlow(NewsState.Initial)
    val newsState get() = _newsState

    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun getHeadlines() {
        _headlines.value = sortedCategories.value
            .associateWith { category -> ProgressHeadlineUi(category) }
            .toSortedMap(compareBy { it.name })
        headlines.value.keys.forEach { category ->
            getHeadlinesUseCase.invoke(category)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    when (it) {
                        is NewsResult.Success -> {
                            val data = it.data.toHeadlineUi()
                            val mutableData =
                                _headlines.value.toSortedMap(compareBy { ca -> ca.name }).apply {
                                    this[data.category] = data
                                }
                            _headlines.value = mutableData
                        }
                        is NewsResult.Error -> {
                            val mutableData =
                                _headlines.value.toSortedMap(compareBy { ca -> ca.name }).apply {
                                    this[category] = FailHeadlineUi(it.error.message, category)
                                }
                            _headlines.value = mutableData
                        }
                    }
                }, {
                    Log.d(TAG, "getHeadlines: ${it.printStackTrace()}")
                    _newsState.value = NewsState.FailureThrowable
                })
                .addTo(disposable)
        }
    }

    sealed class NewsState {
        object Initial : NewsState()
        object FailureThrowable : NewsState()
    }

    companion object {
        private val TAG = NewsViewModel::class.simpleName
    }
}