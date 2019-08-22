package com.example.myapplication.networking

import com.example.myapplication.feature.UserListModel
import java.util.ArrayList

/**
 * Created by Vinod Kumar on 7/8/19.
 */
/*A generic class that contains data and status about loading this data.*/

class Resource<T>(
    val mStatus: Status?, val mData: T? = null,
    val mMessage: String?, val mError: Throwable? = null
) {

    enum class Status {
        SUCCESS, ERROR, LOADING, VALIDATION
    }

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> errorData(error: Throwable): Resource<T> {
            return Resource(Status.ERROR, null, null, error)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

        fun <T> validation(data: T): Resource<T> {
            return Resource(Status.VALIDATION, data, null, null)
        }

    }
}