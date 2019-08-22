package com.example.myapplication.dagger.module

import com.example.myapplication.dagger.scope.AppScope
import com.example.myapplication.networking.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by Vinod Kumar on 7/8/19.
 */
@Module
class ApiModule {

    @Provides
    @AppScope
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


}