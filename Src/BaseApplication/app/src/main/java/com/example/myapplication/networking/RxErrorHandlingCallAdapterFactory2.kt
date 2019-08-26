package com.example.myapplication.networking

import com.example.myapplication.networking.RetrofitException.Companion.asRetrofitException
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by Vinod Kumar on 7/8/19.
 */
class RxErrorHandlingCallAdapterFactory2 private constructor() : CallAdapter.Factory() {
    private val original = RxJava2CallAdapterFactory.create()

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<Any, Any>? {
        return RxCallAdapterWrapper(original.get(returnType, annotations, retrofit) ?: return null)
    }

    private class RxCallAdapterWrapper<R>(private val wrapped: CallAdapter<R, Any>) : CallAdapter<R, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<R>): Any {
            return when (val result = wrapped.adapt(call)) {
                is Single<*> -> result.onErrorResumeNext(Function { throwable ->
                    Single.error(
                        asRetrofitException(
                            throwable
                        )
                    )
                })
                is Observable<*> -> result.onErrorResumeNext(Function { throwable ->
                    Observable.error(
                        asRetrofitException(throwable)
                    )
                })
                is Completable -> result.onErrorResumeNext(Function { throwable ->
                    Completable.error(
                        asRetrofitException(
                            throwable
                        )
                    )
                })
                else -> result
            }

        }
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory2()
        }
    }
}
