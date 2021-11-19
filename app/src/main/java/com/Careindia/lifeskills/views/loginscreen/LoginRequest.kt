package com.careindia.lifeskills.views.loginscreen

import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("socialId")
    var socialId: String? = null
    @SerializedName("socialProvider")
    var socialProvider: String? = null



}