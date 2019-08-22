package com.example.myapplication.feature

import com.google.gson.annotations.SerializedName


/**
 * Created by Vinod Kumar on 8/8/19.
 */
data class UserListModel(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("website") val website: String?
)
