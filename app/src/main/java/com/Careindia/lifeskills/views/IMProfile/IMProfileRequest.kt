package com.careindia.lifeskills.views.improfile

import com.google.gson.annotations.SerializedName

class IMProfileRequest {
    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("name")
    var name: String? = null
}