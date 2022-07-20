package ru.roundkubik.news.data.remote.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.roundkubik.news.data.remote.model.HeadlinesResponse

interface NewsApiService {

    @GET("/v2/top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ) : Single<Response<HeadlinesResponse>>
}