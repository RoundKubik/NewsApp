package ru.roundkubik.news.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
import ru.roundkubik.news.presentation.model.HeadlineUi
import ru.roundkubik.news.presentation.model.toHeadlineUi
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    // private val getSortedCategories: GetSortedCategories
) : ViewModel() {

    private val _headlines: MutableStateFlow<Map<Category, HeadlineUi>> = MutableStateFlow(mapOf())
    val headlines: StateFlow<Map<Category, HeadlineUi>> get() = _headlines

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _newsState: MutableStateFlow<NewsState> = MutableStateFlow(NewsState.Initial)
    val newsState get() = _newsState

    private val disposable = CompositeDisposable()

    init {
        _categories.value =
            listOf(Category.Business, Category.Science, Category.Health, Category.General)
        _headlines.value =
            categories.value.associateWith { category -> HeadlineUi.Progress(category) }
    }

    fun getHeadlines() {
        categories.value.map { category ->
            Log.d(TAG, "getHeadlines: catgegory: $category")
            getHeadlinesUseCase.invoke(category)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        when (it) {
                            is NewsResult.Success -> {
                                val mutableData = _headlines.value.toMutableMap()
                                mutableData[it.data.category] = it.data.toHeadlineUi()
                                _headlines.value = mutableData
                                Log.d(TAG, "getHeadlines: ${it.data.toHeadlineUi()}")
                            }
                            is NewsResult.Error -> {
                                Log.d(TAG, "getHeadlines: error ${it.error.toString()}")
                                HeadlineUi.Fail(it.error.toString(), category)
                            }
                        }
                    }, {
                        Log.d(TAG, "getHeadlines: throwable $it")
                        _newsState.value = NewsState.Failure(HeadlinesError.Unknown)
                    })
                    .addTo(disposable)
        }
    }

    sealed class NewsState {
        object Initial : NewsState()
        data class Failure(val newError: ErrorEntity) : NewsState()
    }

    companion object {
        private val TAG = NewsViewModel::class.simpleName
    }
}