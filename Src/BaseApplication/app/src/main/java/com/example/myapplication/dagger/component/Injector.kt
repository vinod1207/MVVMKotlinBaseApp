package com.example.myapplication.dagger.component

import com.example.myapplication.BaseAppApplication
import com.example.myapplication.feature.UserListActivity

/**
 * Created by Vinod Kumar on 7/8/19.
 */
open interface Injector {

    fun provideIn(application: BaseAppApplication)

    fun provideIn(mainActivity: UserListActivity)
}