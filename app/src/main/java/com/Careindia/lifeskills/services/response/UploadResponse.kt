package com.careindia.lifeskills.services.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UploadResponse {
    @SerializedName("response")
    @Expose
    var response:String?=null
}