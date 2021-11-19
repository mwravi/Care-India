package com.careindia.lifeskills.views.loginscreen


import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error_message")
    val errorMessage: String,
    @SerializedName("message")
    val message: Message,
    @SerializedName("success")
    val success: Boolean
) {
    data class Message(
        @SerializedName("apiToken")
        val apiToken: String,
        @SerializedName("countyCode")
        val countyCode: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("mobile")
        val mobile: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("profilePicture")
        val profilePicture: String,
        @SerializedName("socialProvider")
        val socialProvider: String,
        @SerializedName("userId")
        val userId: Int
    )
}