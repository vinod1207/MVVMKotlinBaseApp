package com.example.myapplication.dagger.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Vinod Kumar on 7/8/19.
 */
@Module
class ApplicationModule(application: Application) {

    var mApplication : Application = application

    @Provides
    fun getApplication() : Application{
        return mApplication
    }

    @Provides
    internal fun getContext(): Context {
        return mApplication
    }

}