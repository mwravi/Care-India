package com.mamta.sabal.service.response

import com.careindia.lifeskills.entity.*
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("mstUser")
    var users: List<MstUserEntity>? = null

    @SerializedName("mst_1State")
    var mst_1State: List<MstStateEntity>? = null

    @SerializedName("mst_2District")
    var mst_2District: List<MstDistrictEntity>? = null

    @SerializedName("Mst_3Zone")
    var Mst_3Zone: List<MstZoneEntity>? = null

    @SerializedName("Mst_4Panchayat_Ward")
    var Mst_4Panchayat_Ward: List<MstPanchayat_WardEntity>? = null

    @SerializedName("mst_5Locality")
    var mst_5Locality: List<MstLocalityEntity>? = null

    @SerializedName("mst_9Lookup")
    var mst_9Lookup: List<MstLookupEntity>? = null



}