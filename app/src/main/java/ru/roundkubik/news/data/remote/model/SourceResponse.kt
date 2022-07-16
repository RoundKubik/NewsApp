package ru.roundkubik.news.data.remote.model

import com.google.gson.annotations.SerializedName


data class SourceResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String
)