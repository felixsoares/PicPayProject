package com.picpay.desafio.android.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Util {
    fun hasNetwork(context: Context): Boolean {
        var isConnected = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true

        return isConnected
    }
}