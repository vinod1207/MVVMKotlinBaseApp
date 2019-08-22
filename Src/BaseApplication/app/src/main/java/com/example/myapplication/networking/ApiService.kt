package com.example.myapplication.networking

import com.example.myapplication.feature.UserListModel
import com.example.myapplication.baseUi.BaseResponseModel
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Vinod Kumar on 7/8/19.
 */
/*This is the service interface in which all the method define which will used for data...*/
interface ApiService {

    /*Get static entity values*/
    @GET("users")
    fun entity(): Observable<ArrayList<UserListModel>>

}