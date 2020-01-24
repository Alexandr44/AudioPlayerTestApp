package com.alex44.audioplayertestapp.di.modules

import com.alex44.audioplayertestapp.App
import com.alex44.audioplayertestapp.common.interfaces.INetworkStatus
import com.alex44.audioplayertestapp.common.ui.NetworkStatus
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class NetworkModule {

    @Provides
    fun networkStatus(app : App) : INetworkStatus = NetworkStatus(app)

}