package ru.roundkubik.news.core.network_manager

import android.content.Context
import android.net.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class NetworkManagerImpl(@ApplicationContext context: Context) : NetworkManager {

    private val connectivityManager: ConnectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            isAvailable = checkAvailability()
        }

        override fun onLost(network: Network) {
            isAvailable = checkAvailability()
        }
    }

    private val _availabilityFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val availabilityFlow: StateFlow<Boolean> get() = _availabilityFlow

    override var isAvailable: Boolean = checkAvailability()
        set(value) {
            if (value == field) return
            field = value
            _availabilityFlow.value = value
        }

    override fun startListening() {
        isAvailable = checkAvailability()
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun stopListening() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun checkAvailability(): Boolean {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}