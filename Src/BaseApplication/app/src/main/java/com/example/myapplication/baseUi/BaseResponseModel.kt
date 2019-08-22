package com.example.myapplication.baseUi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Vinod Kumar on 8/8/19.
 */
class BaseResponseModel<T> {
    @SerializedName("status")
    @Expose
    var mServerStatus: Boolean = false
    @SerializedName("message")
    @Expose
    var mMessage: String? = null
    @SerializedName("data")
    @Expose
    var mData: T? = null
}
