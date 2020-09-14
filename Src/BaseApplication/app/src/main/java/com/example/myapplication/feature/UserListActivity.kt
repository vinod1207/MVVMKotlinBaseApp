package com.example.myapplication.feature

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BaseAppApplication
import com.example.myapplication.R
import com.example.myapplication.baseUi.BaseViewModelFactory
import com.example.myapplication.networking.Resource
import com.example.myapplication.permission.AbstractPermissionActivity
import com.example.myapplication.permission.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class UserListActivity : AbstractPermissionActivity(), AbstractPermissionActivity.PermissionResult {


    private var mUserListModel: java.util.ArrayList<UserListModel>? = java.util.ArrayList()
    private lateinit var recyclerView : RecyclerView


    override fun getLayoutRootView(): View {
        return root_view
    }

    @Inject
    lateinit var mViewModelFactory: BaseViewModelFactory

    private var mViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BaseAppApplication.app?.mAppComponent?.provideIn(this)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel::class.java)

        observeData()

        initView()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        mViewModel?.fetchUserList()

        request_permission.setOnClickListener { requestLocationPermission() }

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
    }


    private fun observeData() {
        mViewModel?.getUserList()?.observe(this,
            Observer { userList -> this.handleData(userList) })
    }

    private fun handleData(userList: Resource<ArrayList<UserListModel>>) {
        when (userList.mStatus) {
            Resource.Status.LOADING -> showShortToast("Loading")

            Resource.Status.SUCCESS -> {
                mUserListModel = userList.mData

                if (mUserListModel != null) {
                    val adapter = UserListAdapter(mUserListModel!!)
                    recyclerView.adapter = adapter
                }
            }

            Resource.Status.ERROR -> {
                showShortToast("Error")
            }
            Resource.Status.VALIDATION -> TODO()
            null -> TODO()
        }
    }


    private fun requestLocationPermission() {
        val permissionSet = ArrayList<String>()
        permissionSet.add(PermissionUtils.ACCESS_COARSE_LOCATION)
        permissionSet.add(PermissionUtils.ACCESS_FINE_LOCATION)
        permissionSet.add(PermissionUtils.CAMERA)

        requestEach(permissionSet, this)
    }

    override fun onPermissionResult(permissionResult: PermissionModel) {
        if (permissionResult.isPermissionGranted) {
            showPicDialog()
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()

        } else if (permissionResult.isPermissionDenied) {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

}
