package com.example.myapplication.networking

import android.content.Context
import io.reactivex.observers.DisposableObserver

/**
 * Created by Vinod Kumar on 7/8/19.
 */
/*This class is to distinguish network error type and to handle them... */
open class BaseNetworkSubscriber<T>(private val mContext: Context) : DisposableObserver<T>() {

    override fun onComplete() {}

    override fun onError(e: Throwable) {
    }

    override fun onNext(t: T) {

    }
}
