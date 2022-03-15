package com.careindia.lifeskills.services.response

import com.careindia.lifeskills.entity.PsychometricEntity
import com.google.gson.annotations.SerializedName

class MasterResponse {

    @SerializedName("tblPsychometric")
    var tblPsychometric: List<PsychometricEntity>? = null

}