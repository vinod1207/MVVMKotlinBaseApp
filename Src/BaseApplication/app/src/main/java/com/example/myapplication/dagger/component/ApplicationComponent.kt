package com.example.myapplication.dagger.component

import com.example.myapplication.dagger.module.ApiModule
import com.example.myapplication.dagger.module.ApplicationModule
import com.example.myapplication.dagger.module.NetworkModule
import com.example.myapplication.dagger.module.ViewModelModule
import com.example.myapplication.dagger.scope.AppScope
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Vinod Kumar on 7/8/19.
 */
@Singleton
@AppScope
@Component(modules = [ApplicationModule::class, ViewModelModule::class, ApiModule::class, NetworkModule::class])
interface ApplicationComponent : Injector
