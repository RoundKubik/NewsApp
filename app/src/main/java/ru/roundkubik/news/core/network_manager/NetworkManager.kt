package ru.roundkubik.news.core.network_manager

import kotlinx.coroutines.flow.StateFlow

interface NetworkManager {

    val isAvailable: Boolean
    val availabilityFlow: StateFlow<Boolean>

    fun startListening()
    fun stopListening()
}