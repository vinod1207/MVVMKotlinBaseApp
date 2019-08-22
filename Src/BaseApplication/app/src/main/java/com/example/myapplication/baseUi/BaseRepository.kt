package com.example.myapplication.baseUi

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver

/**
 * Created by Vinod Kumar on 8/8/19.
 */
open class BaseRepository {
    private val mCompositeSubscription = CompositeDisposable()


    protected fun addSubscription(subscription: DisposableObserver<*>) {
        mCompositeSubscription.add(subscription)
    }

    protected fun addCompleteSubscription(subscription: DisposableCompletableObserver) {
        mCompositeSubscription.add(subscription)
    }

    protected fun removeSubscription(subscription: DisposableObserver<*>) {
        mCompositeSubscription.remove(subscription)
    }


    fun onCleared() {
        mCompositeSubscription.clear()
    }

}