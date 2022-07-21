package ru.roundkubik.news.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.roundkubik.news.core.entity.NewsResult
import ru.roundkubik.news.core.network_manager.NetworkManager
import ru.roundkubik.news.core.schedulers.SchedulerProvider
import ru.roundkubik.news.domain.model.Article
import ru.roundkubik.news.domain.model.Category
import ru.roundkubik.news.domain.model.Headlines
import ru.roundkubik.news.domain.usecase.CacheHeadlinesUseCase
import ru.roundkubik.news.domain.usecase.GetCachedHeadlinesUseCase
import ru.roundkubik.news.domain.usecase.GetHeadlinesUseCase
import ru.roundkubik.news.domain.usecase.GetSortedCategoriesUseCase
import ru.roundkubik.news.presentation.model.*
import java.util.*
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    getSortedCategoriesUseCase: GetSortedCategoriesUseCase,
    val networkManager: NetworkManager,
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val getCachedHeadlinesUseCase: GetCachedHeadlinesUseCase,
    private val cacheHeadlinesUseCase: CacheHeadlinesUseCase,
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

    /* private val _cachedHeadlines: MutableStateFlow<MutableList<Headlines>> = MutableStateFlow(
         mutableListOf()
     )
     val cachedHeadlines: StateFlow<List<Headlines>> get() = _cachedHeadlines
 */
    private val _newsState: MutableStateFlow<NewsState> = MutableStateFlow(NewsState.Initial)
    val newsState get() = _newsState

    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun loadHeadLines() {
        if (networkManager.isAvailable) {
            getHeadlines()
        } else {
            getCachedHeadlines()
        }
    }

    fun startListeningInternetConnection() {
        networkManager.startListening()
    }

    fun stopListeningInternetConnection() {
        networkManager.stopListening()
    }

    private fun getHeadlines() {
        setHeadlinesToProgress()
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

    private fun getCachedHeadlines() {
        setHeadlinesToProgress()
        headlines.value.keys.forEach { category ->
            getCachedHeadlinesUseCase.invoke(category)
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

    fun cacheHeadlines() {
        headlines.value.keys.forEach { category ->
            val it = headlines.value[category]
            Log.d(TAG, "cacheHeadlines: ${it}")
            if (it is BaseHeadlineUi) {
                cacheHeadlinesUseCase.invoke(
                    Headlines(
                        it.category,
                        it.articles.map { article ->
                            Article(
                                article.title,
                                article.url,
                                article.urlToImage,
                                article.publishedAt
                            )
                        }
                    )
                )
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ }, {
                        _newsState.value = NewsState.FailureThrowable
                    }).addTo(disposable)
            }
        }
    }

    private fun setHeadlinesToProgress() {
        _headlines.value = sortedCategories.value
            .associateWith { category -> ProgressHeadlineUi(category) }
            .toSortedMap(compareBy { it.name })
    }

    sealed class NewsState {
        object Initial : NewsState()
        object NoNetworkConnection : NewsState()
        object NetworkAvailable : NewsState()
        object FailureThrowable : NewsState()
    }

    companion object {
        private val TAG = NewsViewModel::class.simpleName
    }
}