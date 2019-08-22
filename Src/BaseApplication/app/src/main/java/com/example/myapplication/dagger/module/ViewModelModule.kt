package com.example.myapplication.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.baseUi.BaseViewModelFactory
import com.example.myapplication.dagger.annotations.ViewModelKey
import com.example.myapplication.feature.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Vinod Kumar on 7/8/19.
 */
@Module
open abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel
}
