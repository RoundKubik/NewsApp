package ru.roundkubik.news.domain.repository

import ru.roundkubik.news.domain.model.Country

interface CountryRepository {

    fun getCountry() : Country

    fun storeCountry(country: Country)
}