package com.example.myapplication.dagger.annotations

import androidx.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.Documented
import kotlin.reflect.KClass

/**
 * Created by Vinod Kumar on 7/8/19.
 */

@Target(AnnotationTarget.FUNCTION)
@Documented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)