package ru.roundkubik.news.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import ru.roundkubik.news.data.remote.model.HeadlinesResponse

interface NewsApiService {

    @GET("/v2/top-headlines")
    fun getTopHeadlines() : Response<HeadlinesResponse>
}