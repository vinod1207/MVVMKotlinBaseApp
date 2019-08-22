package com.example.myapplication

import android.app.Application
import com.example.myapplication.dagger.component.ApplicationComponent
import com.example.myapplication.dagger.component.DaggerApplicationComponent
import com.example.myapplication.dagger.module.ApplicationModule

/**
 * Created by Vinod Kumar on 7/8/19.
 */
class BaseAppApplication : Application() {
    /* *
     * Method used to get DaggerAppComponent instance to get required injection
     *
     * @return AppComponent
     */
    var mAppComponent: ApplicationComponent? = null


    override fun onCreate() {
        super.onCreate()
        app = this
        System.setProperty("http.keepAlive", "false")
        setUpDagger()

    }

    /**
     * Method used to setUp dagger
     */

    private fun setUpDagger() {
        mAppComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }

    open fun appComponent(): ApplicationComponent? {
        return mAppComponent
    }

    companion object {
        var app: BaseAppApplication? = null

    }

}

