package com.careindia.lifeskills.model

import android.os.Parcel
import android.os.Parcelable

class BundleDataModel():Parcelable{
     var countryCode: String? = null
     var phoneNumber: String? = null
    var userId:String?=null
    var name:String?=null



    constructor(parcel: Parcel) : this() {
        countryCode = parcel.readString()
        phoneNumber = parcel.readString()
        userId = parcel.readString()
        name = parcel.readString()


            }
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(countryCode)
        dest?.writeString(phoneNumber)
        dest?.writeString(userId)
        dest?.writeString(name)


    }

    override fun describeContents(): Int {
        return 0
    }



    companion object CREATOR : Parcelable.Creator<BundleDataModel> {
        override fun createFromParcel(parcel: Parcel): BundleDataModel {
            return BundleDataModel(parcel)
        }

        override fun newArray(size: Int): Array<BundleDataModel?> {
            return arrayOfNulls(size)
        }
    }
}
