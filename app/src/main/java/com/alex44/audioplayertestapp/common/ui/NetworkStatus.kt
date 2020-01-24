package com.alex44.audioplayertestapp.common.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.alex44.audioplayertestapp.App
import com.alex44.audioplayertestapp.common.interfaces.INetworkStatus

class NetworkStatus(val app : App) : INetworkStatus {

    override fun getStatus(): INetworkStatus.Status {
        val cm  = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT > 22 ) {
            val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            if(networkCapabilities!=null
                && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    return INetworkStatus.Status.WIFI
                }
                else if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                    return INetworkStatus.Status.ETHERNET
                }
                else if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    return INetworkStatus.Status.MOBILE
                }
                return INetworkStatus.Status.MOBILE
            }
        }
        else {
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null) {
                when (networkInfo.type) {
                    ConnectivityManager.TYPE_WIFI -> return INetworkStatus.Status.WIFI
                    ConnectivityManager.TYPE_ETHERNET -> return INetworkStatus.Status.ETHERNET
                    ConnectivityManager.TYPE_MOBILE -> return INetworkStatus.Status.MOBILE
                }
                return INetworkStatus.Status.OTHER
            }
        }
        return INetworkStatus.Status.OFFLINE
    }

    override fun isOnline(): Boolean {
        return getStatus() != INetworkStatus.Status.OFFLINE
    }

    override fun isWifi(): Boolean {
        return getStatus() == INetworkStatus.Status.WIFI
    }

    override fun isEthernet(): Boolean {
        return getStatus() == INetworkStatus.Status.ETHERNET
    }

    override fun isMobile(): Boolean {
        return getStatus() == INetworkStatus.Status.MOBILE
    }

    override fun isOffline(): Boolean {
        return getStatus() == INetworkStatus.Status.OFFLINE
    }

}