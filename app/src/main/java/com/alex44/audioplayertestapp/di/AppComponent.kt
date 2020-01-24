package com.alex44.audioplayertestapp.di

import com.alex44.audioplayertestapp.di.modules.AppModule
import com.alex44.audioplayertestapp.di.modules.CiceroneModule
import com.alex44.audioplayertestapp.di.modules.ImageModule
import com.alex44.audioplayertestapp.di.modules.RepoModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ImageModule::class, CiceroneModule::class, RepoModule::class])
interface AppComponent {

}