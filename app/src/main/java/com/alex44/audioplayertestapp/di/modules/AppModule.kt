package com.alex44.audioplayertestapp.di.modules

import com.alex44.audioplayertestapp.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app : App) {

    @Provides
    fun app() = app

}