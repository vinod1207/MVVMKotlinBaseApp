package com.example.myapplication.dagger.module

import android.content.Context
import com.example.myapplication.BuildConfig
import com.example.myapplication.dagger.scope.AppScope
import com.example.myapplication.networking.RxErrorHandlingCallAdapterFactory2
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Vinod Kumar on 7/8/19.
 */
/*Module class that will provide instance of variable that are related to network...*/
@Module
class NetworkModule {

    @Provides
    @AppScope
    internal fun provideOkHttpClientInstance(context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        //   builder.addInterceptor(AuthenticationInterceptor(context))
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        builder.addInterceptor(interceptor)
        return builder.build()
    }


    @Provides
    @AppScope
    internal fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val builder = Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory2.create())
            .baseUrl(BuildConfig.BaseUrl)
        return builder.build()
    }

}