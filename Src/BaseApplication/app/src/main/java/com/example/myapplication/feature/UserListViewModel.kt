package com.example.myapplication.feature

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.networking.Resource
import com.hadilq.liveevent.LiveEvent
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by Vinod Kumar on 8/8/19.
 */
class MainViewModel @Inject constructor(application: Application, repository: UserListRepository) :
    AndroidViewModel(application) {
    private var mRepository: UserListRepository = repository
    private var mContext: Application = application
    private val mLiveEventData = LiveEvent<Resource<ArrayList<UserListModel>>>()


    init {
        observeData()
    }


    private fun observeData() {
        mLiveEventData.addSource(mRepository.getUserListLiveData()) { listResource ->
            when (listResource.mStatus) {
                Resource.Status.LOADING -> mLiveEventData.value = Resource.loading()

                Resource.Status.SUCCESS -> mLiveEventData.value = listResource.mData?.let { Resource.success(it) }!!

                Resource.Status.ERROR -> mLiveEventData.value = listResource.mError?.let { Resource.errorData(it) }

                Resource.Status.VALIDATION -> TODO()
            }
        }


    }


    /**
     * Return live event to calling activity for getting User List.
     * @return
     */
    fun getUserList(): LiveEvent<Resource<ArrayList<UserListModel>>> {
        return mLiveEventData

    }

    /**
     * Hit API to get user list.
     */
    fun fetchUserList() {
        mRepository.getUserList()
    }

}
