package ru.roundkubik.news.core.schedulers

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler

    fun computation() : Scheduler

    fun ui() : Scheduler
}