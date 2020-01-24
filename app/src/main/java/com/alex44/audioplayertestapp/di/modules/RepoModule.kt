package com.alex44.audioplayertestapp.di.modules

import dagger.Module

@Module(includes = [ApiModule::class, NetworkModule::class])
class RepoModule {

}