package com.example.myapplication.permission

import android.Manifest

/**
 * Created by Vinod Kumar on 9/8/19.
 */
class PermissionUtils {
    companion object {
        val REQUEST_CODE_SETTINGS_ACTIVITY = 126
        val REQUEST_CODE_PERMISSIONS = 127
        val SEND_SMS = Manifest.permission.SEND_SMS
        val CAMERA = Manifest.permission.CAMERA
        val CALL_PHONE = Manifest.permission.CALL_PHONE
        val INTERNET_ACCESS_FULL = Manifest.permission.INTERNET
        val AUDIO_SETTING = Manifest.permission.MODIFY_AUDIO_SETTINGS
        val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
        val BLUETOOTH = Manifest.permission.BLUETOOTH
        val BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN

        val WAKE_LOCK = Manifest.permission.WAKE_LOCK

        val GET_ACCOUNT = Manifest.permission.GET_ACCOUNTS

        val VIBRATE = Manifest.permission.VIBRATE

        val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

        val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

        val ACCESS_WIFI_STATE = Manifest.permission.ACCESS_WIFI_STATE

        val ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE

        val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

        val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

        val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE

        val GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS

        val READ_CALENDAR = Manifest.permission.READ_CALENDAR

        val WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR

        val LOCATION_MESSAGE = "Please grant location permission"
        val STORAGE_MESSAGE = "Please grant storage permission"
        val CAMERA_MESSAGE = "Please grant camera permission"

        fun getPermissionRationaleTag(permission: String): String {
            var tag = "PERMISSION"
            when (permission) {
                ACCESS_COARSE_LOCATION -> tag = "Location"
                ACCESS_FINE_LOCATION -> tag = ""
                CAMERA -> tag = "Camera"
                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE -> tag = "Storage"
            }
            return tag
        }


        fun getPermissionRationaleMessage(permission: String): String {
            var msg = "Please grant permission in Setting"
            when (permission) {
                ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION -> msg = LOCATION_MESSAGE
                CAMERA -> msg = CAMERA_MESSAGE
                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE -> msg = STORAGE_MESSAGE
            }
            return msg
        }


        fun isPermissionWithCustomMessage(permission: String): Boolean {
/*  switch (permission) {
            case CAMERA:
                result = true;
                break;

        }*/
            return false
        }


        fun getPermissionForCustomMessage(permission: String): String {
            var msg = "Please grant permission in Setting"
            when (permission) {
                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE -> msg = STORAGE_MESSAGE
            }
            return msg
        }
    }




}