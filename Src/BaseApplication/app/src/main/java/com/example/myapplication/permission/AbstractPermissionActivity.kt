package com.example.myapplication.permission

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.baseUi.BaseActivity
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

/**
 * Created by Vinod Kumar on 9/8/19.
 */
abstract class AbstractPermissionActivity : BaseActivity() {

    private var mPermissionsList: ArrayList<String>? = ArrayList()
    private var mPermissionResultCallback: PermissionResult? = null

    fun requestPermission(permission: String, permissionResult: PermissionResult) {
        val arrayList = ArrayList<String>()
        arrayList.add(permission)
        requestEach(arrayList, permissionResult)
    }

    fun requestEach(permissions: ArrayList<String>?, permissionResult: PermissionResult) {
        if (permissions == null || permissions.size == 0)
            return

        mPermissionsList!!.clear()
        mPermissionsList = permissions
        mPermissionResultCallback = permissionResult

        val disGrantedPermissionsLists = ArrayList<String>()
        val rationalePermissionsList = ArrayList<String>()

        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED)
                disGrantedPermissionsLists.add(permissions[i])
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]))
                rationalePermissionsList.add(permissions[i])
        }

        if (disGrantedPermissionsLists.size > 0) {
            ActivityCompat.requestPermissions(
                this, disGrantedPermissionsLists.toTypedArray(),
                PermissionUtils.REQUEST_CODE_PERMISSIONS
            )
            return
        }

        sendPermissionResult(mPermissionsList, isGrant = true, isNeverAskAgain = false)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            PermissionUtils.REQUEST_CODE_PERMISSIONS -> {
                val deniedPermissions = ArrayList<String>()
                val neverAskAgainPermissions = ArrayList<String>()
                var mIsAllPermissionsGranted = true

                for (i in grantResults.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        mIsAllPermissionsGranted = false
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]))
                            deniedPermissions.add(permissions[i])
                        else
                            neverAskAgainPermissions.add(permissions[i])
                    }
                }

                if (!mIsAllPermissionsGranted) {
                    if (deniedPermissions.size > 0)
                        showPermissionRationale(deniedPermissions)
                    else if (neverAskAgainPermissions.size > 0) {
                        sendPermissionResult(mPermissionsList, isGrant = false, isNeverAskAgain = true)
                        showOpenSettingsSnackBar(neverAskAgainPermissions)
                    }

                } else
                    sendPermissionResult(mPermissionsList, isGrant = true, isNeverAskAgain = false)
            }
            else -> {
            }
        }

    }

    private fun showPermissionRationale(deniedPermissions: ArrayList<String>?) {
        if (deniedPermissions != null && deniedPermissions.size > 0) {

            showRationaleMessage(getDenialPermissionsMessage(deniedPermissions),
                DialogInterface.OnClickListener { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this@AbstractPermissionActivity,
                        deniedPermissions.toTypedArray(),
                        PermissionUtils.REQUEST_CODE_PERMISSIONS
                    )
                },
                DialogInterface.OnClickListener { dialog, which ->
                    sendPermissionResult(
                        mPermissionsList,
                        false,
                        false
                    )
                })
        }
    }

    private fun showOpenSettingsSnackBar(neverAskAgainPermissions: ArrayList<String>) {
        Snackbar.make(
            getLayoutRootView(),
            getDenialPermissionsMessage(neverAskAgainPermissions),
            Snackbar.LENGTH_INDEFINITE
        )
            .setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
            .setAction("open settings") { openSystemSettings() }
            .show()
    }

    /**
     * Method used to open System Settings
     */
    private fun openSystemSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", packageName, null)
        startActivityForResult(intent, PermissionUtils.REQUEST_CODE_SETTINGS_ACTIVITY)
    }

    abstract fun getLayoutRootView(): View

    private fun checkIfAllPermissionsGranted(permissions: ArrayList<String>): Boolean {
        var mIsAllPermissionsGranted = true
        for (i in permissions.indices)
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED)
                mIsAllPermissionsGranted = false
        return mIsAllPermissionsGranted
    }

    private fun showRationaleMessage(
        message: String,
        okClickListener: DialogInterface.OnClickListener,
        cancelClickListener: DialogInterface.OnClickListener
    ) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Permission Denied")
        builder.setPositiveButton("Ok", okClickListener)
        builder.setNegativeButton("Cancel", cancelClickListener)
        builder.setMessage(message)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

    private fun getDenialPermissionsMessage(permissions: ArrayList<String>): String {

        var mRationaleMessage = ""

        if (PermissionUtils.isPermissionWithCustomMessage(permissions[0])) {
            mRationaleMessage = PermissionUtils.getPermissionForCustomMessage(permissions[0])
        } else if (!isSinglePermission()) {
            val text = "Please grant access to "

            for (i in permissions.indices) {
                val msg = PermissionUtils.getPermissionRationaleTag(permissions[i])
                if (!TextUtils.isEmpty(msg)) {
                    if (TextUtils.isEmpty(mRationaleMessage))
                        mRationaleMessage = text + PermissionUtils.getPermissionRationaleTag(permissions[i])
                    else
                        mRationaleMessage =
                            mRationaleMessage + ", " + PermissionUtils.getPermissionRationaleTag(permissions[i])
                }

            }
        } else
            mRationaleMessage = PermissionUtils.getPermissionRationaleMessage(permissions[0])


        return mRationaleMessage
    }


    interface PermissionResult {
        fun onPermissionResult(permissionResult: PermissionModel)
    }

    inner class PermissionModel {
        var isPermissionGranted = false
        var isPermissionDenied = false
        var isNeverAskAgain = false
        internal var requestedPermissions: ArrayList<String>? = null
    }

    private fun sendPermissionResult(permissions: ArrayList<String>?, isGrant: Boolean, isNeverAskAgain: Boolean) {
        val permissionModel = PermissionModel()
        if (isGrant) {
            permissionModel.isPermissionGranted = true
            permissionModel.isPermissionDenied = false
            permissionModel.isNeverAskAgain = false
        } else {
            permissionModel.isPermissionGranted = false
            if (isNeverAskAgain) {
                permissionModel.isPermissionDenied = false
                permissionModel.isNeverAskAgain = true
            } else {
                permissionModel.isPermissionDenied = true
                permissionModel.isNeverAskAgain = false
            }
        }

        permissionModel.requestedPermissions = permissions
        mPermissionResultCallback!!.onPermissionResult(permissionModel)
    }

    private fun isSinglePermission(): Boolean {
        return mPermissionsList == null || mPermissionsList!!.size <= 1
    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PermissionUtils.REQUEST_CODE_SETTINGS_ACTIVITY -> if (checkIfAllPermissionsGranted(mPermissionsList!!))
                sendPermissionResult(mPermissionsList, true, false)
            else
                sendPermissionResult(mPermissionsList, false, false)
            else -> {
            }
        }
    }

    protected override fun onDestroy() {
        super.onDestroy()
        mPermissionResultCallback = null
    }

}