package com.example.myapplication.feature

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.baseUi.BaseRepository
import com.example.myapplication.networking.ApiService
import com.example.myapplication.networking.BaseNetworkSubscriber
import com.example.myapplication.networking.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by Vinod Kumar on 8/8/19.
 */
class UserListRepository @Inject constructor(apiService: ApiService, application: Application) : BaseRepository() {
    private var mApiService: ApiService = apiService
    private var mApplication: Application = application
    private var mMutableLiveData = MutableLiveData<Resource<ArrayList<UserListModel>>>()


    /**
     * Method to hit API to get user list.
     */
    fun getUserList() {

         mMutableLiveData.value = Resource.loading()

        addSubscription(
            mApiService.entity().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    BaseNetworkSubscriber<ArrayList<UserListModel>>(mApplication) {
                    override fun onNext(t: ArrayList<UserListModel>) {
                        super.onNext(t)
                        mMutableLiveData.value = Resource.success(t)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                         mMutableLiveData.value = Resource.errorData(e)
                        println("")
                    }
                })
        )


    }

    /**
     * Return mutable live data to view model for getting user list.
     * @return
     */
    fun getUserListLiveData(): MutableLiveData<Resource<ArrayList<UserListModel>>> {
        return mMutableLiveData
    }


}

